package ru.altaiensb.service_desk.model;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_catalogitem", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catitem")
    private Integer idCatitem;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Serv service;

    @ManyToOne
    @JoinColumn(name = "id_catitem_parent")
    private CatalogItem catitemParent;

    @ManyToOne
    @JoinColumn(name = "id_exp_type", nullable = false)
    private ExpType expType;

    @Column(name = "exp_basis")
    private String expBasis;

    @Column(name = "exp_date")
    private LocalDate expDate;

    @Column(name = "nomer", length = 7)
    private String nomer;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "id_effect")
    private Effect effect;

    @ManyToOne
    @JoinColumn(name = "id_scale")
    private Scale scale;
}
