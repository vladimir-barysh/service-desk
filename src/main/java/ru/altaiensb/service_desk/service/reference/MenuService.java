package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.reference.MenuRepository;
import ru.altaiensb.service_desk.model.reference.Menu;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository repo;

    public List<Menu> getAll() {
        return repo.findAll();
    }

    public Menu getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu not found with id=" + id));
    }
}
