package ru.altaiensb.service_desk.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "it_authority", schema = "sd_core")
public class Authority{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_authority")
    private Integer idAuthority;

    @Column(name = "authority", nullable = false)
    private String authority;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
}