package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.OrderSource;
import ru.altaiensb.service_desk.repository.reference.OrderSourceRepository;

@Service
@RequiredArgsConstructor
public class OrderSourceService {

    private final OrderSourceRepository repo;

    public List<OrderSource> getAll() {
        return repo.findAll();
    }

    public OrderSource getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderSource not found with id=" + id));
    }
}
