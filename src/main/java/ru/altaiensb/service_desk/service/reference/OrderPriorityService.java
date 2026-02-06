package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.OrderPriority;
import ru.altaiensb.service_desk.repository.reference.OrderPriorityRepository;

@Service
@RequiredArgsConstructor
public class OrderPriorityService {

    private final OrderPriorityRepository repo;

    public List<OrderPriority> getAll() {
        return repo.findAll();
    }

    public OrderPriority getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderPriority not found with id=" + id));
    }
}
