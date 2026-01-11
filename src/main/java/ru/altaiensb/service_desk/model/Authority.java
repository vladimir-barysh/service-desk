package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_authority", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_authority")
    private Integer id;

    @Column(name = "authority", nullable = false, length = 256, unique = true)
    private String authority;

    @Column(name = "description")
    private String description;

}