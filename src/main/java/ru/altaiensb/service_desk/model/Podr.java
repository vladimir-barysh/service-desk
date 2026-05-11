package ru.altaiensb.service_desk.model;

import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "it_podr", schema = "sd_core")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Podr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_podr")
    private Integer idPodr;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_podr_parent")
    private Podr podrParent;  

    @Column(name = "id_1c")
    private String id1c;

    @Builder.Default
    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "por")
    private Integer por;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "it_podr_fact_location",
        schema = "sd_core",
        joinColumns = @JoinColumn(name = "id_podr"),
        inverseJoinColumns = @JoinColumn(name = "id_fact_location")
    )
    private Set<FactLocation> factLocations = new HashSet<>();
}
