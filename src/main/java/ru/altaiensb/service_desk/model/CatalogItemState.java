package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_catitem_state", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatitemState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_catitem_state")
    private Integer idCatitemState;

    @Column(name = "name")
    private String name;
}