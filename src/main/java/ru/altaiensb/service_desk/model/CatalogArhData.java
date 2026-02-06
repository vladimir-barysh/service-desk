package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "it_catalog_arh_data", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogArhData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_data")
    private Integer idData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_arh", nullable = false)
    private CatalogArh catalogArh;

    @Column(name = "old_pk")
    private Integer oldPk;

    @Column(name = "nomer", length = 7)
    private String nomer;

    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "org", length = 256)
    private String org;

    @Column(name = "podr", length = 256)
    private String podr;

    @Column(name = "fio_fz", length = 256)
    private String fioFz;

    @Column(name = "fio_cu", length = 256)
    private String fioCu;

    @Column(name = "fio_sm", length = 256)
    private String fioSm;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "exp_basis", length = 256)
    private String expBasis;

    @Column(name = "exp_date")
    private LocalDate expDate;

    @Column(name = "scale", length = 50)
    private String scale;

    @Column(name = "effect", length = 50)
    private String effect;

    @Column(name = "info")
    private String info;
}
