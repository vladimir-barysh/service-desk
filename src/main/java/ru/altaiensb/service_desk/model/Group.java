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
@Table(name = "it_group", schema = "sd_core")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private Integer idGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User responsibleUser;

    @Column(name = "name")
    private String name;

    @Column(name = "name_1c_doc")
    private String name1cDoc;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "groups", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
}
