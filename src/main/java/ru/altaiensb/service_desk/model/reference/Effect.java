package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_effect", schema = "sd_reference")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Effect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_effect")
    private Integer idEffect;

    @Column(name = "name")
    private String name;
}
