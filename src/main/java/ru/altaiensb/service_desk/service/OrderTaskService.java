package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.model.OrderTask;
import ru.altaiensb.service_desk.dto.OrderTaskDTO.TaskCreateRequestDTO;
import ru.altaiensb.service_desk.dto.OrderTaskDTO.TaskResponseDTO;
import ru.altaiensb.service_desk.dto.OrderTaskDTO.TaskUpdateDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.model.Work;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.repository.OrderRepository;
import ru.altaiensb.service_desk.repository.OrderStateRepository;
import ru.altaiensb.service_desk.repository.OrderTaskRepository;
import ru.altaiensb.service_desk.repository.WorkRepository;
import ru.altaiensb.service_desk.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OrderTaskService {

    private final OrderTaskRepository taskRepo;
    private final OrderRepository orderRepo;
    private final WorkRepository workRepo;
    private final UserRepository userRepo;
    private final OrderStateRepository orderStateRepo;

    private TaskResponseDTO toResponse(OrderTask task) {
        return new TaskResponseDTO(
            task.getIdOrderTask(),
            task.getDateFinishPlan(),
            task.getDateFinishFact(),
            task.getDescription(),
            task.getCloseParentCheck(),
            task.getDateCreated(),
            task.getResultText(),
            task.getOrder() != null ? task.getOrder().getIdOrder() : null,
            task.getOrder() != null ? task.getOrder().getNomer() : null,
            task.getOrder() != null ? task.getOrder().getName() : null,
            task.getOrder() != null ? task.getOrder().getOrderType().getIdOrderType() : null,
            task.getOrder() != null ? task.getOrder().getOrderType().getName() : null,
            task.getOrder() != null ? task.getOrder().getService().getIdService() : null,
            task.getOrder() != null ? task.getOrder().getService().getFullname() : null,
            task.getOrder() != null ? task.getOrder().getCatalogItem().getIdCatitem() : null,
            task.getOrder() != null ? task.getOrder().getCatalogItem().getName() : null,
            task.getOrderTaskParent() != null ? task.getOrderTaskParent().getIdOrderTask() : null,
            task.getWork() != null ? task.getWork().getIdWork() : null,
            task.getExecutor() != null ? task.getExecutor().getIdItUser() : null,
            task.getExecutor() != null ? task.getExecutor().getFio1c() : null,
            task.getTaskState() != null ? task.getTaskState().getIdOrderState() : null,
            task.getTaskState() != null ? task.getTaskState().getName() : null,
            task.getCreator() != null ? task.getCreator().getIdItUser() : null,
            task.getCreator() != null ? task.getCreator().getFio1c() : null
        );
    }

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAll() {
        return taskRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getById(Integer id) {
        OrderTask task = taskRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));
        return toResponse(task);
    }
    
    // ---------------------------- CREATE ----------------------------
    @Transactional
    public TaskResponseDTO create(TaskCreateRequestDTO dto) {
        Order order = orderRepo.findById(dto.idOrder())
                .orElseThrow(() -> new ResourceNotFoundException("Order", dto.idOrder()));
        User creator = userRepo.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("Creator", 1));
        OrderState defaultState = orderStateRepo.findByName("Новая")
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", "Новая"));
        OrderTask parentTask = new OrderTask();
        if (dto.idOrderTaskParent() != null){
            parentTask = taskRepo.findById(dto.idOrderTaskParent())
                .orElseThrow(() -> new ResourceNotFoundException("TaskParent", dto.idOrderTaskParent()));
        }
        // TODO: Разобраться с работой. Убрать проверку на работу. Сейчас с фронта не идет никакой idWork
        Work work = new Work();
        if (dto.idWork() != null){
            work = workRepo.findById(dto.idWork())
                .orElseThrow(() -> new ResourceNotFoundException("Work", dto.idWork()));
        }
        User executor = userRepo.findById(dto.idExecutor())
                .orElseThrow(() -> new ResourceNotFoundException("Executor", dto.idExecutor()));
        
        OrderTask task = OrderTask.builder()
                .order(order)
                .orderTaskParent(dto.idOrderTaskParent() != null ? parentTask : null)
                .work(dto.idWork() != null ? work : null)
                .executor(executor)
                .dateFinishPlan(dto.dateFinishPlan())
                .dateFinishFact(null)
                .description(dto.description())
                .closeParentCheck(dto.closeParentCheck())
                .taskState(defaultState)
                .dateCreated(Instant.now())
                .creator(creator)
                .resultText("")
                .build();

        OrderTask saved = taskRepo.save(task);

        return toResponse(saved);
    }
    // ---------------------------- UPDATE ----------------------------
    @Transactional
    public TaskResponseDTO update(Integer id, TaskUpdateDTO dto) {
        OrderTask task = taskRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task", id));

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
                OrderState state = orderStateRepo.findById(1)
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

        // Валидация даты
        if (task.getDateFinishPlan() != null && task.getDateFinishPlan().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Плановая дата не может быть в прошлом");
        }

        OrderTask updated = taskRepo.save(task);
        return toResponse(updated);
    }

}
