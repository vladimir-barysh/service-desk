package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_service_state", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service_state")
    private Integer idServiceState;

    @Column(name = "name")
    private String name;
}