package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.UserRepository;
import ru.altaiensb.service_desk.model.core.User;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

    private final UserRepository repo;

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id=" + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
