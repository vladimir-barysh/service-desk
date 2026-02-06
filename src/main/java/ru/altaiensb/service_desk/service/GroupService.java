package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Group;
import ru.altaiensb.service_desk.repository.GroupRepository;

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
