package ru.altaiensb.service_desk.model.reference;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_task_state", schema = "sd_reference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task_state")
    private Integer idTaskState;

    @Column(name = "name", length = 256)
    private String name;
}
