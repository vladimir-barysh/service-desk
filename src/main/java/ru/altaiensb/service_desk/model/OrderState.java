package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_order_state", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_state")
    private Integer idOrderState;

    @Column(name = "name")
    private String name;
}
