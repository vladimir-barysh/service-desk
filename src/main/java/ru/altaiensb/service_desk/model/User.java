package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="it_user", schema = "sd_core")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_it_user")
    private Integer idItUser;

    @Column(name = "login_ad")
    private String loginAd;

    @Column(name = "email_ad")
    private String emailAd;

    @Column(name = "tel_ad")
    private String telAd;

    @Column(name = "fio_1c")
    private String fio1c;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_podr")
    private Podr podr;

    @Column(name = "dolzh_1c")
    private String dolzh1c;

    @Column(name = "tab_num_1c")
    private String tabNum1c;

    @CreationTimestamp
    @Column(name = "d_c", nullable = false, updatable = false)
    private LocalDate dateCreate;

    @UpdateTimestamp
    @Column(name = "d_m", nullable = false)
    private LocalDate dateModern;

    @Builder.Default
    @ColumnDefault("true")
    @Column(name = "is_user", nullable = false)
    private Boolean isUser = true;

    @Column(name = "d_prin")
    private LocalDate datePrin;

    @Column(name = "d_uvol")
    private LocalDate dateUvol;

    @Column(name = "agreement_type")
    private String agreementType;

    @Column(name = "fiz_lico")
    private String fizLico;

    @Column(name = "state_1c")
    private String state1c;

    @Column(name = "cti_user")
    private String ctiUser;

    @Column(name = "cti_password")
    private String ctiPassword;

    @Column(name = "crm_user")
    private String crmUser;

    @Column(name = "crm_password")
    private String crmPassword;

    @Column(name = "inter_ad")
    private Boolean interAd;

    @Column(name = "grade")
    private Integer grade;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "it_user_group",
        schema = "sd_core",
        joinColumns = @JoinColumn(name = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "id_group")
    )
    @Builder.Default
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "it_user_authority",
        schema = "sd_core",
        joinColumns = @JoinColumn(name = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "id_authority")
    )
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();
}
