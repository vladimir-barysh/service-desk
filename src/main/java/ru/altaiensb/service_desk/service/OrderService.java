package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import ru.altaiensb.service_desk.dto.OrderDTO;
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

    public Order create(OrderDTO dto) {
        Order order = new Order();
        // генерация номера
        Integer maxNomer = orderRepo.findMaxNomer();
        int nextNomer = (maxNomer != null) ? maxNomer + 1 : 1;
        order.setNomer(nextNomer);

        order.setName(dto.getName());
        order.setDescription(dto.getDescription());
        order.setDateCreated(Instant.now()); // дата создания устанавливается автоматически
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
                        .orElseThrow(() -> new RuntimeException("OrderPriority not found")));

        // проверка на дату в прошлом
        if (order.getDateFinishPlan() != null) {
            if (order.getDateFinishPlan().isBefore(Instant.now())) {
                throw new IllegalArgumentException("Дата в прошлом");
            }
        }

        return orderRepo.save(order);
    }

    public Order update(Integer id, OrderDTO dto) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));

        order.setName(dto.getName());
        order.setDescription(dto.getDescription());
        order.setDateFinishPlan(dto.getDateFinishPlan());
        order.setDatePostpone(dto.getDatePostpone());
        order.setService(servRepo.findById(dto.getIdService())
                .orElseThrow(() -> new RuntimeException("Service not found with id=" + dto.getIdService())));
        order.setOrderType(orderTypeRepo.findById(dto.getIdOrderType())
                .orElseThrow(() -> new RuntimeException("OrderType not found with id=" + dto.getIdOrderType())));
        order.setOrderState(orderStateRepo.findById(dto.getIdOrderState())
                .orElseThrow(() -> new RuntimeException("OrderState not found with id=" + dto.getIdOrderState())));
        order.setOrderPriority(orderPriorityRepo.findById(dto.getIdOrderPriority())
                .orElseThrow(
                        () -> new RuntimeException("OrderPriority not found with id=" + dto.getIdOrderPriority())));
        order.setComment(dto.getComment());
        order.setComment(dto.getComment());

        /*/ проверка на дату в прошлом
        if (order.getDateFinishPlan() != null) {
            if (order.getDateFinishPlan().isBefore(Instant.now())) {
                throw new IllegalArgumentException("Дата в прошлом");
            }
        }*/

        return orderRepo.save(order);
    }

    public void delete(Integer id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));
        orderRepo.delete(order);
    }
}
