package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.OrderMessageRepository;
import ru.altaiensb.service_desk.model.core.OrderMessage;

@Service
@RequiredArgsConstructor
public class OrderMessageService {

    private final OrderMessageRepository repo;

    public List<OrderMessage> getAll() {
        return repo.findAll();
    }

    public OrderMessage getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderMessage not found with id=" + id));
    }
}
