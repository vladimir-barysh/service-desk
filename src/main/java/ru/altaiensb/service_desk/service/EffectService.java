package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Effect;
import ru.altaiensb.service_desk.repository.EffectRepository;

@Service
@RequiredArgsConstructor
public class EffectService {

    private final EffectRepository repo;

    public List<Effect> getAll() {
        return repo.findAll();
    }

    public Effect getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Effect not found with id=" + id));
    }
}
