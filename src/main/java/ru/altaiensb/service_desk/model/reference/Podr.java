package ru.altaiensb.service_desk.model.reference;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "it_podr", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Podr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_podr")
    private Integer idPodr;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_podr_parent")
    private Podr podrParent;  

    @Column(name = "id_1c")
    private String id1c;

    @ColumnDefault("false")
    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @Column(name = "por")
    private Integer por;
}
