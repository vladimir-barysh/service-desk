package ru.altaiensb.service_desk.model.core;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

import ru.altaiensb.service_desk.model.reference.*;

@Entity
@Table(name = "it_order", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Integer idOrder;

    @Column(name = "nomer")
    private Integer nomer;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "date_c", updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "date_f_plan")
    private LocalDateTime dateFinishPlan;

    @Column(name = "date_f_fact")
    private LocalDateTime dateFinishFact;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_parent")
    private Order orderParent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_type")
    private OrderType orderType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_catitem")
    private CatalogItem catalogItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service")
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_state", nullable = false)
    private OrderState orderState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_priority")
    private OrderPriority orderPriority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_creator", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_initiator", nullable = false)
    private User initiator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_dispatcher")
    private User dispatcher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_executor")
    private User executor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_source")
    private OrderSource orderSource;

    @Column(name = "result_text")
    private String resultText;
}
