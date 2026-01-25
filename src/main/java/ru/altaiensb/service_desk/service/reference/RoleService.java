package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.reference.RoleRepository;
import ru.altaiensb.service_desk.model.reference.Role;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repo;

    public List<Role> getAll() {
        return repo.findAll();
    }

    public Role getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with id=" + id));
    }
}
