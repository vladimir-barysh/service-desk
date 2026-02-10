package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_task_state", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task_state")
    private Integer idTaskState;

    @Column(name = "name")
    private String name;
}
