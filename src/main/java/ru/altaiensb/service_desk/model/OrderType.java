package ru.altaiensb.service_desk.model;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_order_type", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order_type")
    private Integer idOrderType;

    @Column(name = "name")
    private String name;

    @ColumnDefault("false")
    @Column(name = "available", nullable = false)
    private Boolean available;
}
