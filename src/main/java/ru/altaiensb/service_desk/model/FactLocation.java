package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "it_fact_location", schema = "sd_core")
@Getter
@Setter
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

    @Builder.Default
    @ManyToMany(mappedBy = "factLocations", fetch = FetchType.LAZY)
    private Set<Podr> podrs = new HashSet<>();
}