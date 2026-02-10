package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "it_catalog_arh", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogArh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_arh")
    private Integer idArh;

    @CreationTimestamp
    @Column(name = "d_c", nullable = false, updatable = false, columnDefinition = "timestamptz")
    private Instant dateCreated;

    @Column(name = "u_c", nullable = false)
    private Integer userCreatorId;

    @Column(name = "remark", length = 250)
    private String remark;
}
