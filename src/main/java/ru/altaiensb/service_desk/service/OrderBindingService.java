package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.OrderBinding;
import ru.altaiensb.service_desk.repository.OrderBindingRepository;

@Service
@RequiredArgsConstructor
public class OrderBindingService {

    private final OrderBindingRepository repo;

    public List<OrderBinding> getAll() {
        return repo.findAll();
    }

    public OrderBinding getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderBinding not found with id=" + id));
    }
}
