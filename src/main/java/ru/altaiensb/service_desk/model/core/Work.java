package ru.altaiensb.service_desk.model.core;

import jakarta.persistence.*;

import lombok.*;

import ru.altaiensb.service_desk.model.reference.WorkType;
import ru.altaiensb.service_desk.model.reference.Podr;

@Entity
@Table(name = "it_service_type", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Work {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_work")
    private Integer idWork;

    @ManyToOne
    @JoinColumn(name = "id_work_parent")
    private Work workParent;

    @ManyToOne
    @JoinColumn(name = "id_catitem")
    private CatalogItem catitem;

    @ManyToOne
    @JoinColumn(name = "id_service")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "id_work_type", nullable = false)
    private WorkType workType;

    @Column(name = "remark")
    private String remark;

    @ManyToOne
    @JoinColumn(name = "id_podr", nullable = false)
    private Podr podr;
}
