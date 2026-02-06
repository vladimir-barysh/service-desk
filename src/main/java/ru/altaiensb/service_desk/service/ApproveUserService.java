package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.ApproveUser;
import ru.altaiensb.service_desk.repository.ApproveUserRepository;

@Service
@RequiredArgsConstructor
public class ApproveUserService {

    private final ApproveUserRepository repo;

    public List<ApproveUser> getAll() {
        return repo.findAll();
    }

    public ApproveUser getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("ApproveUser not found with id=" + id));
    }
}
