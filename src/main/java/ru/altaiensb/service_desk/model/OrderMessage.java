package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "it_order_message", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_message")
    private Integer idOrderMessage;

    @CreationTimestamp
    @Column(name = "d_c", columnDefinition = "timestamptz")
    private Instant dateCreated;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order_task")
    private OrderTask orderTask;
}
