package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import ru.altaiensb.service_desk.dto.OrderDTO;
import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.model.OrderBinding;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.repository.*;
import ru.altaiensb.service_desk.model.OrderState;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final ServiceRepository servRepo;
    private final OrderTypeRepository orderTypeRepo;
    private final OrderStateRepository orderStateRepo;
    private final UserRepository userRepository;
    private final OrderPriorityRepository orderPriorityRepo;
    private final OrderBindingRepository orderBindingRepository;

    private String saveFileToStorage(MultipartFile file) throws IOException {
        String uploadDir = "/api/orderbinding";
        Files.createDirectories(Paths.get(uploadDir));
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    public List<Order> getAll() {
        return orderRepo.findAll();
    }

    public Order getById(Integer id) {
        return orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));
    }

    public Order create(OrderDTO dto, List<MultipartFile> files, Integer userId) throws IOException {
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

        order = orderRepo.save(order);

        // Если файлы есть — сохраняем их
        if (files != null && !files.isEmpty()) {
            User currentUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id=" + userId));
            for (MultipartFile file : files) {
                // Сохраняем файл на диск / S3 / etc.
                String filePath = saveFileToStorage(file); // реализуй метод: возвращает путь (e.g.
                                                           // "/uploads/filename.ext")

                // Создаём запись в OrderBinding
                OrderBinding binding = new OrderBinding();
                binding.setPath(filePath);
                binding.setOrder(order);
                binding.setDateCreated(Instant.now());
                binding.setUser(currentUser);
                binding.setName(file.getOriginalFilename()); // имя файла

                orderBindingRepository.save(binding);
            }
        }

        return order;
    }

    public Order update(Integer id, OrderDTO dto) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));

        order.setName(dto.getName());
        order.setDescription(dto.getDescription());
        order.setDateFinishPlan(dto.getDateFinishPlan());
        order.setDatePostpone(dto.getDatePostpone());
        order.setService(servRepo.findById(dto.getIdService())
                .orElseThrow(() -> new RuntimeException(
                        "Service not found with id=" + dto.getIdService())));
        order.setOrderType(orderTypeRepo.findById(dto.getIdOrderType())
                .orElseThrow(() -> new RuntimeException(
                        "OrderType not found with id=" + dto.getIdOrderType())));
        order.setOrderState(orderStateRepo.findById(dto.getIdOrderState())
                .orElseThrow(() -> new RuntimeException(
                        "OrderState not found with id=" + dto.getIdOrderState())));
        order.setOrderPriority(orderPriorityRepo.findById(dto.getIdOrderPriority())
                .orElseThrow(
                        () -> new RuntimeException("OrderPriority not found with id="
                                + dto.getIdOrderPriority())));
        order.setComment(dto.getComment());
        order.setComment(dto.getComment());

        /*
         * / проверка на дату в прошлом
         * if (order.getDateFinishPlan() != null) {
         * if (order.getDateFinishPlan().isBefore(Instant.now())) {
         * throw new IllegalArgumentException("Дата в прошлом");
         * }
         * }
         */

        return orderRepo.save(order);
    }

    public void delete(Integer id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));
        orderRepo.delete(order);
    }

    public Order updateStatus(Integer id, Integer newStateId) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));

        OrderState newState = orderStateRepo.findById(newStateId)
                .orElseThrow(() -> new RuntimeException("OrderState not found with id=" + newStateId));

        order.setOrderState(newState);
        return orderRepo.save(order);
    }

}
