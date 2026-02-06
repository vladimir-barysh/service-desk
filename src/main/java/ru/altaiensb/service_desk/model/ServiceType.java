package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_service_type", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service_type")
    private Integer idServiceType;

    @Column(name = "name")
    private String name;

    @Column(name = "fullname")
    private String fullname;
}
