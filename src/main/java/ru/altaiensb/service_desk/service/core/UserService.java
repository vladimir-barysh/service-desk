package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.UserRepository;
import ru.altaiensb.service_desk.model.core.User;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id=" + id));
    }
}
