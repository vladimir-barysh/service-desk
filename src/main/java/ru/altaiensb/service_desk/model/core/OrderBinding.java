package ru.altaiensb.service_desk.model.core;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "it_order_binding", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderBinding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_binding")
    private Integer idOrderBinding;

    @Column(name = "path", length = 256)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    private Order order;

    @Column(name = "d_c")
    private LocalDate dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "name", length = 256)
    private String name;
}
