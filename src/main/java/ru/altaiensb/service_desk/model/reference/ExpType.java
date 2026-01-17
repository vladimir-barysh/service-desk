package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_exp_type", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_exp_type")
    private Integer idExpType;

    @Column(name = "name")
    private String name;
}
