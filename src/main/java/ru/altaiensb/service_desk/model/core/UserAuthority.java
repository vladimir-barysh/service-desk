package ru.altaiensb.service_desk.model.core;

import jakarta.persistence.*;

import lombok.*;

import ru.altaiensb.service_desk.model.reference.*;

@Entity
@Table(name = "it_user_authority", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_authority")
    private Integer idUserAuthority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_authority", nullable = false)
    private Authority authority;
}
