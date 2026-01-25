package ru.altaiensb.service_desk.model.core;

import jakarta.persistence.*;
import lombok.*;

import ru.altaiensb.service_desk.model.reference.*;
@Entity
@Table(name = "it_catitem_user_role", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogItemUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catitem_user_role")
    private Integer idCatitemUserRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_catitem")
    private CatalogItem catalogItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_podr")
    private Podr podr;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_role", nullable = false)
    private UserRole userRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service")
    private Serv service;
}
