package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.TaskState;
import ru.altaiensb.service_desk.repository.TaskStateRepository;

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
