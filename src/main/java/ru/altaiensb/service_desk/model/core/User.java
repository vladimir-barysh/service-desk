package ru.altaiensb.service_desk.model.core;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import lombok.*;
import ru.altaiensb.service_desk.model.reference.Podr;

@Entity
@Table(name="it_user", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_it_user")
    private Integer idItUser;

    @Column(name = "login_ad", length = 50)
    private String loginAd;

    @Column(name = "email_ad")
    private String emailAd;

    @Column(name = "tel_ad", length = 50)
    private String telAd;

    @Column(name = "fio_1c")
    private String fio1c;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_podr")
    private Podr podr;

    @Column(name = "dolzh_1c")
    private String dolzh1c;

    @Column(name = "tab_num_1c", length = 50)
    private String tabNum1c;

    @Column(name = "d_c")
    private LocalDate dateCreate;

    @Column(name = "d_m")
    private LocalDate dateModern;

    @Column(name = "is_user")
    private Boolean isUser;

    @Column(name = "d_prin")
    private LocalDate datePrin;

    @Column(name = "d_uvol")
    private LocalDate dateUvol;

    @Column(name = "agreement_type", length = 50)
    private String agreementType;

    @Column(name = "fiz_lico", length = 50)
    private String fizLico;

    @Column(name = "state_1c")
    private String state1c;

    @Column(name = "cti_user", length = 50)
    private String ctiUser;

    @Column(name = "cti_password", length = 50)
    private String ctiPassword;

    @Column(name = "crm_user", length = 50)
    private String crmUser;

    @Column(name = "crm_password", length = 50)
    private String crmPassword;

    @Column(name = "inter_ad")
    private Boolean interAd;

    @Column(name = "grade")
    private Integer grade;

    public String getUsername() { return loginAd; }
    public String getPassword() { return ctiPassword; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    } 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
