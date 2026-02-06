package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_fact_location", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fact_location")
    private Integer idFactLocation;

    @Column(name = "name")
    private String name;
}