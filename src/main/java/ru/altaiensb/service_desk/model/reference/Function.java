package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;
import lombok.*;

import ru.altaiensb.service_desk.model.core.*;

@Entity
@Table(name = "it_function", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_function")
    private Integer idFunction;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service")
    private Service service;

    //добавить проверку >=1 <=9
    @Column(name = "acc_level")
    private Integer accLevel;
}
