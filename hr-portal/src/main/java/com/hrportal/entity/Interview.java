package com.hrportal.entity;

import jakarta.persistence.*;
import com.hrportal.enums.InterviewStatus;
import com.hrportal.enums.InterviewType;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    private LocalDateTime interviewDate;

    @Enumerated(EnumType.STRING)
    private InterviewType interviewType;

    private String meetingLink;

    @Enumerated(EnumType.STRING)
    private InterviewStatus status;

    @Column(length = 2000)
    private String feedback;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();

        if (status == null) {
            status = InterviewStatus.SCHEDULED;
        }
    }
}