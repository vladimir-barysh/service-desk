package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import ru.altaiensb.service_desk.dto.OrderCreateDTO;
import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.repository.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final ServiceRepository servRepo;
    private final OrderTypeRepository orderTypeRepo;
    private final OrderStateRepository orderStateRepo;
    private final UserRepository userRepository;
    private final OrderPriorityRepository orderPriorityRepo;

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public Order getById(Integer id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));
    }

    public Order create(OrderCreateDTO dto) {
        Order order = new Order();
        // генерация номера
        Integer maxNomer = orderRepo.findMaxNomer();
        int nextNomer = (maxNomer != null) ? maxNomer + 1 : 1;
        order.setNomer(nextNomer);

        order.setName(dto.getName());
        order.setDescription(dto.getDescription());
        order.setDateCreated(Instant.now());                    // дата создания устанавливается автоматически
        order.setDateFinishPlan(dto.getDateFinishPlan());
        order.setDatePostpone(dto.getDatePostpone());
        order.setComment(dto.getComment());

        // временный юзер
        order.setCreator(
                userRepository.findById(1)
                        .orElseThrow(() -> new RuntimeException("User not found with id=1")));
        // временный юзер
        order.setInitiator(
                userRepository.findById(1)
                        .orElseThrow(() -> new RuntimeException("User not found with id=1")));

        order.setService(
                servRepo.findById(dto.getIdService())
                        .orElseThrow(() -> new RuntimeException("Service not found")));

        order.setOrderType(
                orderTypeRepo.findById(dto.getIdOrderType())
                        .orElseThrow(() -> new RuntimeException("OrderType not found")));

        order.setOrderState(
                orderStateRepo.findByName("Новая")
                        .orElseThrow(() -> new RuntimeException("State NEW not found")));
        
        order.setOrderPriority(
                orderPriorityRepo.findByName("Низкий")
                        .orElseThrow(() -> new RuntimeException("OrderPriority not found"))
        );

        // проверка на дату в прошлом
        if (order.getDateFinishPlan() != null) {
            if (order.getDateFinishPlan().isBefore(Instant.now())) {
                throw new IllegalArgumentException("Дата в прошлом");
            }
        }

        return orderRepo.save(order);
    }
}
