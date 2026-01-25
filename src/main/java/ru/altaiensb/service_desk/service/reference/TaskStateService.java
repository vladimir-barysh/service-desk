package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.reference.TaskStateRepository;
import ru.altaiensb.service_desk.model.reference.TaskState;

@Service
@RequiredArgsConstructor
public class TaskStateService {

    private final TaskStateRepository repo;

    public List<TaskState> getAll() {
        return repo.findAll();
    }

    public TaskState getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("TaskState not found with id=" + id));
    }
}
