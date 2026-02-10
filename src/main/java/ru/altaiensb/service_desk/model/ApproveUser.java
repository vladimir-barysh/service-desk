package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "it_approve_users", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_approve_users")
    private Integer idApproveUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_approve", nullable = false)
    private Approve approve;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "user_role")
    private Short userRole;

    @ColumnDefault("0")
    @Column(name = "state", nullable = false)
    private Short state;

    @Column(name = "result_text")
    private String resultText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_approve_users_parent")
    private ApproveUser approveUserParent;

    @Column(name = "date_plan", columnDefinition = "timestamptz")
    private Instant datePlan;

    @Column(name = "date_fact", columnDefinition = "timestamptz")
    private Instant dateFact;

    @Column(name = "task_text")
    private String taskText;
}
