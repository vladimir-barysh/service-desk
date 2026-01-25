package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.reference.OrderTypeRepository;
import ru.altaiensb.service_desk.model.reference.OrderType;

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
