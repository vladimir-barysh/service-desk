package ru.altaiensb.service_desk.model.core;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.*;
import ru.altaiensb.service_desk.model.core.Service;

@Entity
@Table(name = "it_new", schema = "sd_core")
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
    private LocalDateTime dateS;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Service serviceEntity;
}
