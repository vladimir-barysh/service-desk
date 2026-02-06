package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_menu", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Integer idMenu;

    @Column(name = "name", length = 256)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "acc_level")
    private Short accLevel;

    // Обязательная связь с функцией
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_function", nullable = false)
    private Function function;
}