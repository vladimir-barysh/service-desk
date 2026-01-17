package ru.altaiensb.service_desk.model.core;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_service_catitem", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCatItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service_catitem")
    private Integer idServiceCatitem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service", nullable = false)
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_catitem", nullable = false)
    private CatalogItem catalogItem;
}
