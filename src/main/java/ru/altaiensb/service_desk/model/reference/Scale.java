package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_scale", schema = "sd_reference")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_scale")
    private Integer scale;

    @Column(name = "name")
    private String name;
}
