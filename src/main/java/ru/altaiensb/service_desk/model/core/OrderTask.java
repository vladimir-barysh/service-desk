package ru.altaiensb.service_desk.model.core;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import ru.altaiensb.service_desk.model.reference.*;

@Entity
@Table(name = "it_order_task", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_task")
    private Integer idOrderTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_task_parent")
    private OrderTask orderTaskParent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_work")
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_executor")
    private User executor;

    @Column(name = "date_f_plan")
    private LocalDateTime dateFinishPlan;

    @Column(name = "date_f_fact")
    private LocalDateTime dateFinishFact;

    @Column(name = "description")
    private String description;

    @Column(name = "close_parent_check")
    private Boolean closeParentCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_task_state", nullable = false)
    private TaskState taskState;

    @CreationTimestamp
    @Column(name = "date_c", updatable = false)
    private LocalDateTime dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_creator", nullable = false)
    private User creator;

    @Column(name = "result_text")
    private String resultText;
}