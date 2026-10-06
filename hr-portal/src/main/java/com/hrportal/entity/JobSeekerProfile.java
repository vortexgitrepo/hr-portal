package com.hrportal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_seeker_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSeekerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String headline;

    @Column(length = 2000)
    private String summary;

    private Integer experienceYears;

    private String currentCompany;

    private String currentLocation;

    private String preferredLocation;

    private Double expectedSalary;

    private Integer noticePeriod;

    private String profileImageUrl;
}