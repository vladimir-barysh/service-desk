package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.UserAuthorityRepository;
import ru.altaiensb.service_desk.model.core.UserAuthority;

@Service
@RequiredArgsConstructor
public class UserAuthorityService {

    private final UserAuthorityRepository repo;

    public List<UserAuthority> getAll() {
        return repo.findAll();
    }

    public UserAuthority getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("UserAuthority not found with id=" + id));
    }
}
