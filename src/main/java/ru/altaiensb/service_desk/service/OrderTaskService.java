package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.dto.TaskUpdateDTO;
import ru.altaiensb.service_desk.model.OrderPriority;
import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.model.OrderTask;
import ru.altaiensb.service_desk.model.OrderType;
import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.model.Work;
import ru.altaiensb.service_desk.model.Serv;
import ru.altaiensb.service_desk.model.TaskState;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.repository.OrderRepository;
import ru.altaiensb.service_desk.repository.OrderTaskRepository;
import ru.altaiensb.service_desk.repository.WorkRepository;
import ru.altaiensb.service_desk.repository.UserRepository;
import ru.altaiensb.service_desk.repository.TaskStateRepository;

@Service
@RequiredArgsConstructor
public class OrderTaskService {

    private final OrderTaskRepository taskRepo;
    private final OrderRepository orderRepo;
    private final WorkRepository workRepo;
    private final UserRepository userRepo;
    private final TaskStateRepository taskStateRepo;

    public List<OrderTask> getAll() {
        return taskRepo.findAll();
    }

    public OrderTask getById(Integer id) {
        return taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderTask not found with id=" + id));
    }
    
    public OrderTask update(Integer id, TaskUpdateDTO dto) {
        OrderTask task = taskRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id=" + id));

        dto.getIdOrder().ifPresent(idOrder -> {
            if (idOrder == null) {
                task.setOrder(null);
            } else {
                Order order = orderRepo.findById(idOrder)
                        .orElseThrow(() -> new RuntimeException("Order not found"));
                task.setOrder(order);
            }
        });
        dto.getIdOrderTaskParent().ifPresent(idOrderTaskParent -> {
            if (idOrderTaskParent == null) {
                task.setOrderTaskParent(null);
            } else {
                OrderTask parent = taskRepo.findById(idOrderTaskParent)
                        .orElseThrow(() -> new RuntimeException("OrderTaskParent not found"));
                task.setOrderTaskParent(parent);
            }
        });
        dto.getIdWork().ifPresent(idWork -> {
            if (idWork == null) {
                task.setWork(null);
            } else {
                Work work = workRepo.findById(idWork)
                        .orElseThrow(() -> new RuntimeException("Work not found"));
                task.setWork(work);
            }
        });
        dto.getIdExecutor().ifPresent(idExecutor -> {
            if (idExecutor == null) {
                task.setExecutor(null);
            } else {
                User executor = userRepo.findById(idExecutor)
                        .orElseThrow(() -> new RuntimeException("User not found with id="));
                task.setExecutor(executor);
            }
        });
        dto.getDateFinishPlan().ifPresent(task::setDateFinishPlan);
        dto.getDateFinishFact().ifPresent(task::setDateFinishFact);
        dto.getDescription().ifPresent(task::setDescription);
        
        dto.getCloseParentCheck().ifPresent(task::setCloseParentCheck);

        dto.getIdTaskState().ifPresent(idTaskState -> {
            if (idTaskState == null) {
                task.setTaskState(null);
            } else {
                TaskState state = taskStateRepo.findById(1)
                        .orElseThrow(() -> new RuntimeException("State not found with id="));
                task.setTaskState(state);
            }
        });

        dto.getIdCreator().ifPresent(idCreator -> {
            if (idCreator == null) {
                task.setCreator(null);
            } else {
                User creator = userRepo.findById(idCreator)
                        .orElseThrow(() -> new RuntimeException("User not found with id="));
                task.setCreator(creator);
            }
        });
        dto.getResultText().ifPresent(task::setResultText);

        return taskRepo.save(task);
    }

}
