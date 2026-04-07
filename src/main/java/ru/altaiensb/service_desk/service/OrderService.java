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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.openapitools.jackson.nullable.JsonNullable;

import ru.altaiensb.service_desk.dto.OrderDTO;
import ru.altaiensb.service_desk.dto.OrderUpdateDTO;
import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.model.OrderBinding;
import ru.altaiensb.service_desk.model.OrderPriority;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.repository.*;
import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.model.OrderTask;
import ru.altaiensb.service_desk.model.OrderType;
import ru.altaiensb.service_desk.model.Serv;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final ServiceRepository servRepo;
    private final OrderTypeRepository orderTypeRepo;
    private final OrderStateRepository orderStateRepo;
    private final UserRepository userRepo;
    private final OrderPriorityRepository orderPriorityRepo;
    private final OrderBindingRepository orderBindingRepo;
    private final OrderTaskRepository orderTaskRepo;
    private final TaskStateRepository taskStateRepo;

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

        Integer maxNomer = orderRepo.findMaxNomer();
        int nextNomer = (maxNomer != null) ? maxNomer + 1 : 1;
        order.setNomer(nextNomer);

        order.setName(dto.getName());
        order.setDescription(dto.getDescription());
        order.setDateCreated(Instant.now());
        order.setDateFinishPlan(dto.getDateFinishPlan());
        order.setDatePostpone(dto.getDatePostpone());
        order.setComment(dto.getComment());

        // TODO: Поменять на настоящего пользователя
        order.setCreator(
                userRepo.findById(1)
                        .orElseThrow(() -> new RuntimeException("User not found with id=1")));
        // TODO: Поменять на настоящего пользователя
        order.setInitiator(
                userRepo.findById(1)
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
            User currentUser = userRepo.findById(userId)
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

                orderBindingRepo.save(binding);
            }
        }

        return order;
    }

    public Order update(Integer id, OrderUpdateDTO dto) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id=" + id));

        dto.getName().ifPresent(order::setName);
        dto.getDescription().ifPresent(order::setDescription);
        dto.getDateFinishPlan().ifPresent(order::setDateFinishPlan);
        dto.getDateFinishFact().ifPresent(order::setDateFinishFact);
        dto.getDatePostpone().ifPresent(order::setDatePostpone);
        
        dto.getIdOrderParent().ifPresent(idOrderParent -> {
            if (idOrderParent == null) {
                order.setOrderParent(null);
            } else {
                Order parent = orderRepo.findById(idOrderParent)
                        .orElseThrow(() -> new RuntimeException("OrderParent not found"));
                order.setOrderParent(parent);
            }
        });

        dto.getIdOrderType().ifPresent(idType -> {
            if (idType == null) {
                order.setOrderType(null);
            } else {
                OrderType type = orderTypeRepo.findById(idType)
                        .orElseThrow(() -> new RuntimeException("OrderType not found"));
                order.setOrderType(type);
            }
        });

        dto.getIdService().ifPresent(idService -> {
            if (idService == null) {
                order.setService(null);
            } else {
                Serv service = servRepo.findById(idService)
                        .orElseThrow(() -> new RuntimeException("Service not found"));
                order.setService(service);
            }
        });

        dto.getIdOrderPriority().ifPresent(idPriority -> {
            if (idPriority == null) {
                order.setOrderPriority(null);
            } else {
                OrderPriority priority = orderPriorityRepo.findById(idPriority)
                        .orElseThrow(() -> new RuntimeException("OrderPriority not found"));
                order.setOrderPriority(priority);
            }
        });

        dto.getIdOrderState().ifPresent(idState -> {
            if (idState == null) {
                order.setOrderState(null);
            } else {
                OrderState state = orderStateRepo.findById(idState)
                        .orElseThrow(() -> new RuntimeException("OrderState not found"));
                order.setOrderState(state);
            }
        });

        

        dto.getComment().ifPresent(order::setComment);
        
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

        OrderTask newTask = new OrderTask();

        order.setOrderState(newState);

        if (newState.getName().equals("В работе")) {
            // TODO: Поменять на настоящего пользователя
            order.setDispatcher(userRepo.findById(3)
                    .orElseThrow(() -> new RuntimeException("User not found with id=3")));
            
            newTask.setOrder(order);
            // TODO: add setWork()

            // TODO: Поменять на настоящего пользователя
            newTask.setExecutor(userRepo.findById(3)
                    .orElseThrow(() -> new RuntimeException("User not found with id=3")));

            newTask.setDateFinishPlan(order.getDateFinishPlan());
            newTask.setDescription(order.getDescription());
            // TODO: closeParentCheck()
            newTask.setTaskState(taskStateRepo.findById(1)
                    .orElseThrow(() -> new RuntimeException("Task state no found with id=1")));
            newTask.setDateCreated(Instant.now());
            // TODO: Поменять на настоящего пользователя
            newTask.setCreator(order.getCreator());

            orderTaskRepo.save(newTask);
        }
        if (newState.getName().equals("Отклонена")) {
            order.setDateFinishFact(Instant.now());
        }
        return orderRepo.save(order);
    }

}
