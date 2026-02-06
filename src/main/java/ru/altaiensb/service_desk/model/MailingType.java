package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_mailing_type", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mailing_type")
    private Integer idMailingType;

    @Column(name = "name")
    private String name;
}
