package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_work_type", schema = "sd_reference")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_work_type")
    private String idWorkType;
    
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
