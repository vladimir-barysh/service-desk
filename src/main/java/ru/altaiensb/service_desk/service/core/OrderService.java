package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.repository.core.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repo;

    public List<Order> getAll() {
        return repo.findAll();
    }

    public Order getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));
    }
}
