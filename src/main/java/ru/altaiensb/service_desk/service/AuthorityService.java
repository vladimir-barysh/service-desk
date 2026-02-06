package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Authority;
import ru.altaiensb.service_desk.repository.AuthorityRepository;

@Service
@RequiredArgsConstructor
public class AuthorityService {

    private final AuthorityRepository repo;

    public List<Authority> getAll() {
        return repo.findAll();
    }

    public Authority getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Authority not found with id=" + id));
    }
}
