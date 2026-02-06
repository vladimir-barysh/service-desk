package ru.altaiensb.service_desk.model;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_new", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_new")
    private Integer idNew;

    @Column(name = "name")
    private String name;

    @Column(name = "date_s")
    private LocalDate dateS;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Serv serviceEntity;
}
