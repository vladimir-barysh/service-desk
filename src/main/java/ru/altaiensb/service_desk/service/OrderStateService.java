package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.repository.OrderStateRepository;

@Service
@RequiredArgsConstructor
public class OrderStateService {

    private final OrderStateRepository repo;

    public List<OrderState> getAll() {
        return repo.findAll();
    }

    public OrderState getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderState not found with id=" + id));
    }
}
