package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.GroupRepository;
import ru.altaiensb.service_desk.model.core.Group;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository repo;

    public List<Group> getAll() {
        return repo.findAll();
    }

    public Group getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found with id=" + id));
    }
}
