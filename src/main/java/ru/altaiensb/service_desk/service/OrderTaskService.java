package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.OrderTask;
import ru.altaiensb.service_desk.repository.OrderTaskRepository;

@Service
@RequiredArgsConstructor
public class OrderTaskService {

    private final OrderTaskRepository repo;

    public List<OrderTask> getAll() {
        return repo.findAll();
    }

    public OrderTask getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderTask not found with id=" + id));
    }
}
