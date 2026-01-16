package ru.altaiensb.service_desk.model.core;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;

import lombok.*;
import ru.altaiensb.service_desk.model.reference.*;

@Entity
@Table(name = "it_service", schema = "sd_core")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Integer idService;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "sname")
    private String sname;

    @Column(name = "description")
    private String description;

    @Column(name = "developer")
    private String developer;

    @Column(name = "date_s")
    private LocalDateTime dateS;

    @Column(name = "date_f")
    private LocalDateTime dateF;

    @Column(name = "priznak_is")
    private Boolean priznakIs;

    @ManyToOne
    @JoinColumn(name = "id_service_type", nullable = false)
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "id_exp_type", nullable = false)
    private ExpType expType;

    @ManyToOne
    @JoinColumn(name = "id_service_parent", nullable = false)
    private Service serviceParent;

    @ColumnDefault("false")
    @Column(name = "is_need_approval", nullable = false)
    private Boolean isNeedApproval;

    @ColumnDefault("true")
    @Column(name = "is_service", nullable = false)
    private Boolean isService;

    @Column(name = "business_critical", nullable = false, columnDefinition = "SMALLINT DEFAULT 3 CHECK (business_critical IN (1, 2, 3))")
    private Short businessCritical = 3;

    @Column(name = "basis_s")
    private String basisS;

}
