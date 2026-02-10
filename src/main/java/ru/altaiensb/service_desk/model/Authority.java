package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_authority", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_authority")
    private Integer idAuthority;

    @Column(name = "authority", nullable = false)
    private String authority;

    @Column(name = "description")
    private String description;

}