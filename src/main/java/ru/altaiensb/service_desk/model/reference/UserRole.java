package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_user_role", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_role")
    private Integer idUserRole;

    @Column(name = "name", length = 256)
    private String name;
}
