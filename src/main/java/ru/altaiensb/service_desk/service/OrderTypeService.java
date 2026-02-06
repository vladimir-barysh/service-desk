package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.OrderType;
import ru.altaiensb.service_desk.repository.OrderTypeRepository;

@Service
@RequiredArgsConstructor
public class OrderTypeService {

    private final OrderTypeRepository repo;

    public List<OrderType> getAll() {
        return repo.findAll();
    }

    public OrderType getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderType not found with id=" + id));
    }
}
