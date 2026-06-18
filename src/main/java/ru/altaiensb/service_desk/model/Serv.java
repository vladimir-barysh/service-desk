package ru.altaiensb.service_desk.model;
import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_service", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Serv {
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

    @Column(name = "date_s", columnDefinition = "timestamptz")
    private LocalDate dateS;

    @Column(name = "date_f", columnDefinition = "timestamptz")
    private LocalDate dateF;

    @Column(name = "priznak_is")
    private Boolean priznakIs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_catitem", nullable = false)
    private CatalogItem catalogItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service_type", nullable = false)
    private ServiceType serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service_state")
    private ServiceState serviceState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_exp_type", nullable = false)
    private ExpType expType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_service_parent")
    private Serv serviceParent;

    @Builder.Default
    @ColumnDefault("false")
    @Column(name = "is_need_approval", nullable = false)
    private Boolean isNeedApproval = false;

    @Builder.Default
    @ColumnDefault("true")
    @Column(name = "is_service", nullable = false)
    private Boolean isService = true;

    @Builder.Default
    @Column(name = "business_critical", nullable = false, columnDefinition = "SMALLINT DEFAULT 3 CHECK (business_critical IN (1, 2, 3))")
    private Short businessCritical = 3;

    @Column(name = "basis_s")
    private String basisS;

}