package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.UserGroup;
import ru.altaiensb.service_desk.repository.core.UserGroupRepository;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository repo;

    public List<UserGroup> getAll() {
        return repo.findAll();
    }

    public UserGroup getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("UserGroup not found with id=" + id));
    }
}
