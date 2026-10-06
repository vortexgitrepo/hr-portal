# Plan: JWT Login Implementation + Test Controller

## Context
Spring Boot 4.0.8 / Java 17 / MySQL + JPA / Spring Security project.
JJWT 0.12.6 dependencies and `jwt.secret` already exist, but **no JWT code is implemented**:
- No `JwtService`, no JWT filter, no token in login response
- `SecurityConfig` has no auth mechanism (protected endpoints unreachable)
- Wrong password → 500, unknown email → 404 (should both be 401)
- Sessions still enabled (should be STATELESS for JWT)
- No test controller

Token expiration: **7 days** (`jwt.expiration=604800000` ms).

## New files

### 1. `src/main/java/com/hrportal/service/JwtService.java`
- Read `jwt.secret` and `jwt.expiration` from properties (`@Value`)
- `generateToken(User user)` — subject = email, claims: `userId`, `name`; signed with HMAC-SHA key (`Keys.hmacShaKeyFor`), JJWT 0.12 API (`Jwts.builder().claims()...compact()`)
- `validateToken(String token)` / `extractEmail(String token)` using `Jwts.parser().verifyWith(key)...parseSignedClaims(token)`
- Handle `ExpiredJwtException` / `JwtException` → invalid

### 2. `src/main/java/com/hrportal/security/JwtAuthenticationFilter.java`
- Extends `OncePerRequestFilter`
- Read `Authorization: Bearer <token>` header → validate → load `UserDetails` (email as username, `User.builder()`... or simple in-memory authorities) → set `UsernamePasswordAuthenticationToken` in `SecurityContextHolder`
- On invalid/missing token: continue chain without auth (entry point returns 401 JSON)

### 3. `src/main/java/com/hrportal/controller/TestController.java`
- `@RequestMapping("/api/test")`
- `GET /api/test/hello` — **protected**; returns message with authenticated user's email (from `Authentication` / SecurityContext) → verifies JWT works
- Optional: `GET /api/test/public` — `permitAll` for comparison

### 4. `src/main/java/com/hrportal/exception/InvalidCredentialsException.java`
- `RuntimeException` subclass for 401

## Modified files

### 5. `src/main/resources/application.properties`
- Add `jwt.expiration=604800000` (7 days)

### 6. `dto/LoginResponse.java`
- Add `token` (String) and `tokenType` ("Bearer") fields + getters; keep constructor with token

### 7. `controller/UserController.java`
- `login`: inject `JwtService`, generate token, return `LoginResponse(userId, name, "Login successfully", token, "Bearer")`

### 8. `config/SecurityConfig.java`
- Inject `JwtAuthenticationFilter`, add `addFilterBefore(..., UsernamePasswordAuthenticationFilter.class)`
- `sessionManagement(STATELESS)`
- `exceptionHandling`: `authenticationEntryPoint` → 401 JSON `{message: "..."}`
- permitAll: `/api/users/register`, `/api/users/login`, `/api/test/public` (if added); everything else authenticated

### 9. `service/UserService.java`
- `login`: unknown email OR wrong password → `InvalidCredentialsException("Invalid email or password")` (no user-existence leak; remove `RuntimeException`)

### 10. `exception/GlobalExceptionHandler.java`
- Add handler: `InvalidCredentialsException` → 401
- Add handler: `MethodArgumentNotValidException` → 400 (validation errors)

## Verification
1. `mvn -q compile` — builds clean
2. If MySQL available: `mvn spring-boot:run`
   - `POST /api/users/register` → 201
   - `POST /api/users/login` → 200 with token
   - `GET /api/test/hello` without token → 401
   - `GET /api/test/hello` with `Authorization: Bearer <token>` → 200 + email
   - Wrong password → 401 (not 500)

---

## Status (verification complete)

### Implemented (all compile + `mvn test` passes)
- `JwtService`, `JwtAuthenticationFilter`, `TestController`, `InvalidCredentialsException` created
- `LoginResponse` (token+tokenType), `UserController` (generates token), `SecurityConfig` (filter, STATELESS, 401 entry point), `UserService` (401 creds), `GlobalExceptionHandler` (401 + 400), `jwt.expiration=604800000` — done

### Live test results (app running on :8080)
| Test | Result |
|---|---|
| GET `/api/test/hello` no token | 401 JSON entry-point message |
| GET `/api/test/hello` garbage token | 401 JSON |
| GET `/api/test/hello` **valid signed token** | **200** `{"authenticatedAs":"jwt-test@example.com",...}` |
| GET `/api/test/hello` expired token | 401 JSON |
| POST `/api/users/login` bad creds (valid JSON) | 401 `{"message":"Invalid email or password"}` |
| GET `/api/users/login` (wrong method) | 405's `Allow: POST` header but **status masked as 401**  |

### BUG FOUND: `/error` dispatch blocked
Error dispatches (404/405/400 from MVC) are re-processed by the security chain,
hit `anyRequest().authenticated()`, and the entry point rewrites them as 401.
Evidence: `Allow: POST` header + status 401 on GET `/api/users/login`.

**Fix:** add `"/error"` to the `permitAll` matchers in `SecurityConfig`.

### Remaining verification (needs approval — writes to DB)
- Real end-to-end: POST `/api/users/register` -> POST `/api/users/login` -> use returned
  token on GET `/api/test/hello` -> 200 (filter/parser side already proven with crafted token)
- Wrong-password on an EXISTING user -> 401
