package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_role", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Integer idRole;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_function", nullable = false)
    private Function function;
}
