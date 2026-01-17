package ru.altaiensb.service_desk.model.core;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

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

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_creator")
    private User userCreator;

    @ColumnDefault("false")
    @Column(name = "flag_approved", nullable = false)
    private Boolean flagApproved;

    @CreationTimestamp
    @Column(name = "date_c", updatable = false)
    private LocalDate dateCreated;

    @Column(name = "date_plan")
    private LocalDate datePlan;

    @ColumnDefault("0")
    @Column(name = "state", nullable = false)
    private Short state;

    @Column(name = "date_fact")
    private LocalDate dateFact;

    @Column(name = "task_text")
    private String taskText;
}
