package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_podr_fact_location", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PodrFactLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_podr_fact_location")
    private Integer idPodrFactLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_podr", nullable = false)
    private Podr podr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fact_location", nullable = false)
    private FactLocation factLocation;
}
