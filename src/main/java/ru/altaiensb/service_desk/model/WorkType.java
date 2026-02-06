package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_work_type", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_work_type")
    private Integer idWorkType;
    
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
