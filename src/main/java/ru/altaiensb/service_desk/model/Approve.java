package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "it_approve", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Approve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_approve")
    private Integer idApprove;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_creator")
    private User userCreator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_state")
    private OrderState approveState;

    @Column(name = "name")
    private String name;

    @Builder.Default
    @ColumnDefault("false")
    @Column(name = "flag_approved", nullable = false)
    private Boolean flagApproved = false;

    @CreationTimestamp
    @Column(name = "date_c", columnDefinition = "timestamptz", nullable = false)
    private Instant dateCreated;

    @Column(name = "date_plan", columnDefinition = "timestamptz")
    private Instant datePlan;

    @Column(name = "date_fact", columnDefinition = "timestamptz")
    private Instant dateFact;

    @Column(name = "task_text")
    private String taskText;
}
