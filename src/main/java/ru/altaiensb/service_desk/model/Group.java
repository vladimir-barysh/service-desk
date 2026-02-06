package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_group", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private Integer idGroup;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "name")
    private String name;

    @Column(name = "name_1c_doc")
    private String name1cDoc;

    @Column(name = "description")
    private String description;
}
