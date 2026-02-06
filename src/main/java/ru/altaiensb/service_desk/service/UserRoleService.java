package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.UserRole;
import ru.altaiensb.service_desk.repository.UserRoleRepository;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository repo;

    public List<UserRole> getAll() {
        return repo.findAll();
    }

    public UserRole getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRole not found with id=" + id));
    }
}
