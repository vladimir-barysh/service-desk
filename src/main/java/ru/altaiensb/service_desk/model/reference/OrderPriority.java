package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_order_priority", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_priority")
    private Integer idOrderPriority;

    @Column(name = "name")
    private String name;

    @Column(name = "color", length = 8)
    private String color;
}
