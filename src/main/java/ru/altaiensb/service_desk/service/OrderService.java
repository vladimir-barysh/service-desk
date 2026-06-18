package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.OrderDTO.OrderCreateRequestDTO;
import ru.altaiensb.service_desk.dto.OrderDTO.OrderResponseDTO;
import ru.altaiensb.service_desk.dto.OrderDTO.OrderUpdateDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.*;
import ru.altaiensb.service_desk.repository.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    @PersistenceContext
    private EntityManager entityManager;
    private final OrderRepository orderRepo;
    private final ServRepository servRepo;
    private final OrderTypeRepository orderTypeRepo;
    private final OrderStateRepository orderStateRepo;
    private final UserRepository userRepo;
    private final OrderPriorityRepository orderPriorityRepo;
    private final OrderTaskRepository orderTaskRepo;
    private final OrderSourceRepository orderSourceRepo;
    private final ApproveService approveService;

    private static final String CLOSED = "Закрыта";
    private static final String PENDING = "В ожидании";
    private static final String PENDING_CONFIRMATION = "На подтверждении";
    private static final String REJECTED = "Отклонена";

    // ---------------------------- Response ----------------------------
    private OrderResponseDTO toResponse(Order order) {
        return new OrderResponseDTO(
                order.getIdOrder(),
                order.getNomer(),
                order.getName(),
                order.getDescription(),
                order.getDateCreated(),
                order.getDateFinishPlan(),
                order.getDateFinishFact(),
                order.getDatePostpone(),
                order.getDateTechReturn(),
                order.getComment(),
                order.getResultText(),
                order.getOrderParent() != null ? order.getOrderParent().getIdOrder() : null,
                order.getInitiator() != null ? order.getInitiator().getIdItUser() : null,
                order.getInitiator() != null ? order.getInitiator().getFio1c() : null,
                order.getCreator() != null ? order.getCreator().getIdItUser() : null,
                order.getCreator() != null ? order.getCreator().getFio1c() : null,
                order.getDispatcher() != null ? order.getDispatcher().getIdItUser() : null,
                order.getDispatcher() != null ? order.getDispatcher().getFio1c() : null,
                order.getExecutor() != null ? order.getExecutor().getIdItUser() : null,
                order.getExecutor() != null ? order.getExecutor().getFio1c() : null,
                order.getOrderType() != null ? order.getOrderType().getIdOrderType() : null,
                order.getOrderType() != null ? order.getOrderType().getName() : null,
                order.getCatalogItem() != null ? order.getCatalogItem().getIdCatitem() : null,
                order.getCatalogItem() != null ? order.getCatalogItem().getName() : null,
                order.getService() != null ? order.getService().getIdService() : null,
                order.getService() != null ? order.getService().getFullname() : null,
                order.getOrderState() != null ? order.getOrderState().getIdOrderState() : null,
                order.getOrderState() != null ? order.getOrderState().getName() : null,
                order.getOrderPriority() != null ? order.getOrderPriority().getIdOrderPriority() : null,
                order.getOrderPriority() != null ? order.getOrderPriority().getName() : null,
                order.getOrderSource() != null ? order.getOrderSource().getIdOrderSource() : null,
                order.getOrderSource() != null ? order.getOrderSource().getName() : null);
    }

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAll() {
        return orderRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getById(Integer id) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getByInitiatorId(Integer id) {
        return orderRepo.findByInitiator_IdItUser(id).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ---------------------------- CREATE ----------------------------
    @Transactional
    public OrderResponseDTO create(OrderCreateRequestDTO dto) {

        // Загрузка обязательных связей из DTO
        User initiator = userRepo.findById(dto.idInitiator())
                .orElseThrow(() -> new ResourceNotFoundException("User (initiator)", dto.idInitiator()));
        OrderType orderType = orderTypeRepo.findById(dto.idOrderType())
                .orElseThrow(() -> new ResourceNotFoundException("OrderType", dto.idOrderType()));
        Serv service = servRepo.findById(dto.idService())
                .orElseThrow(() -> new ResourceNotFoundException("Service", dto.idService()));
        CatalogItem catalogItem = service.getCatalogItem();

        // TODO: взять создателя заявки из контекста
        User creator = userRepo.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("User (creator)", 1));

        // Значения по умолчанию
        OrderState defaultState = orderStateRepo.findByName("Новая")
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", "Новая"));
        OrderPriority defaultPriority = orderPriorityRepo.findByName("Низкий")
                .orElseThrow(() -> new ResourceNotFoundException("OrderPriority", "Низкий"));

        // Необязательные поля
        OrderSource orderSource = dto.idOrderSource() != null
                ? orderSourceRepo.findById(dto.idOrderSource())
                        .orElseThrow(() -> new ResourceNotFoundException("OrderSource", dto.idOrderSource()))
                : null;

        // Валидация в зависимости от типа
        String typeName = orderType.getName();
        switch (typeName) {
            case "ЗНТ" -> {
                if (dto.dateTechReturn() == null) {
                    throw new IllegalArgumentException("Для заявки типа ЗНТ обязательно указать дату возврата техники");
                }
            }
            case "ЗНО", "ЗНИ", "ЗНД" -> {
                // специфические проверки при необходимости
            }
            default -> throw new IllegalArgumentException("Неизвестный тип заявки: " + typeName);
        }

        // Построение сущности
        Order order = Order.builder()
                .name(dto.name())
                .description(dto.description())
                .dateCreated(Instant.now())
                .dateFinishPlan("ЗНД".equals(typeName) ? null : dto.dateFinishPlan())
                .datePostpone(null)
                .dateTechReturn("ЗНТ".equals(typeName) ? dto.dateTechReturn() : null)
                .comment(dto.comment())
                .creator(creator)
                .initiator(initiator)
                .orderType(orderType)
                .service(service)
                .catalogItem(catalogItem)
                .orderPriority(defaultPriority)
                .orderState(defaultState)
                .dispatcher(null)
                .executor(null)
                .orderSource(orderSource)
                .orderParent(null)
                .resultText("")
                .build();
        Order savedOrder = orderRepo.save(order);
        entityManager.refresh(savedOrder);

        // Автоматическое создание согласования для ЗНД и ЗНИ
        if ("ЗНД".equals(typeName) || "ЗНИ".equals(typeName)) {
            approveService.createAuto(savedOrder);
        }

        return toResponse(savedOrder);
    }

    // ---------------------------- UPDATE ----------------------------
    @Transactional
    public OrderResponseDTO update(Integer id, OrderUpdateDTO dto) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));

        dto.getName().ifPresent(order::setName);
        dto.getDescription().ifPresent(order::setDescription);
        dto.getDateFinishPlan().ifPresent(order::setDateFinishPlan);
        dto.getDateFinishFact().ifPresent(order::setDateFinishFact);
        dto.getDatePostpone().ifPresent(order::setDatePostpone);
        dto.getComment().ifPresent(order::setComment);
        dto.getResultText().ifPresent(order::setResultText);

        // Обработка ссылок с возможным null
        setReference(dto.getIdOrderParent(), orderRepo, order::setOrderParent);
        setReference(dto.getIdOrderType(), orderTypeRepo, order::setOrderType);
        dto.getIdService().ifPresent(idService -> {
            if (idService == null) {
                order.setService(null);
            } else {
                Serv service = servRepo.findById(idService)
                                .orElseThrow(() -> new RuntimeException("Service not found"));
                order.setService(service);

                CatalogItem catalogItem = service.getCatalogItem();

                order.setCatalogItem(catalogItem);
            }
        });
        setReference(dto.getIdOrderPriority(), orderPriorityRepo, order::setOrderPriority);
        setReference(dto.getIdOrderState(), orderStateRepo, order::setOrderState);
        setReference(dto.getIdCreator(), userRepo, order::setCreator);
        setReference(dto.getIdInitiator(), userRepo, order::setInitiator);
        setReference(dto.getIdDispatcher(), userRepo, order::setDispatcher);
        setReference(dto.getIdExecutor(), userRepo, order::setExecutor);
        setReference(dto.getIdOrderSource(), orderSourceRepo, order::setOrderSource);

        if (order.getOrderState().getName().equals(CLOSED) || order.getOrderState().getName().equals(REJECTED)) {
            order.setDateFinishFact(Instant.now());
        }
        // Валидация даты
        if (order.getDateFinishPlan() != null && order.getDateFinishPlan().isBefore(Instant.now())) {
            throw new IllegalArgumentException("Плановая дата не может быть в прошлом");
        }

        Order updated = orderRepo.save(order);
        return toResponse(updated);
    }

    private <T> void setReference(org.openapitools.jackson.nullable.JsonNullable<Integer> nullableId,
            org.springframework.data.jpa.repository.JpaRepository<T, Integer> repo,
            java.util.function.Consumer<T> setter) {
        nullableId.ifPresent(id -> {
            if (id == null) {
                setter.accept(null);
            } else {
                T entity = repo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException(repo.getClass().getSimpleName(), id));
                setter.accept(entity);
            }
        });
    }

    // ---------------------------- DELETE ----------------------------
    @Transactional
    public void delete(Integer id) {
        if (!orderRepo.existsById(id)) {
            throw new ResourceNotFoundException("Order", id);
        }
        orderRepo.deleteById(id);
    }

    // ---------------------------- STATUS UPDATE ----------------------------
    @Transactional
    public OrderResponseDTO updateStatus(Integer id, Integer newStateId) {
        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        OrderState newState = orderStateRepo.findById(newStateId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", newStateId));

        OrderState oldState = order.getOrderState();
        order.setOrderState(newState);
        
        // Перевод заявки в статус "В работе"
        if (newState.getName().equals("В работе") && (oldState == null || !oldState.getName().equals("В работе"))) {
            // Назначить диспетчера, если не задан
            if (order.getDispatcher() == null) {
                // TODO: взять из контекста
                User dispatcher = userRepo.findById(1) 
                        .orElseThrow(() -> new ResourceNotFoundException("Dispatcher default", 1));
                order.setDispatcher(dispatcher);
                order.setExecutor(dispatcher);
            }
            // Создать задачу
            // TODO: синхронизировать с согласованием. 
            OrderTask task = OrderTask.builder()
                    .order(order)
                    .executor(order.getDispatcher())
                    .dateFinishPlan(order.getDateFinishPlan())
                    .description(order.getDescription())
                    .taskState(orderStateRepo.findByName("Новая")
                            .orElseThrow(() -> new ResourceNotFoundException("TaskState", 1)))
                    .dateCreated(Instant.now())
                    // Создатель задачи - диспетчер
                    .creator(order.getDispatcher())
                    .build();
            orderTaskRepo.save(task);
        } 
        // Подтверждение заявки
        else if (newState.getName().equals(CLOSED)
                && (oldState != null && oldState.getName().equals(PENDING_CONFIRMATION))) {
            order.setDateFinishFact(Instant.now());
            String oldResult = order.getResultText();
            order.setResultText( oldResult + "\nЗаявка закрыта инициатором");
        }
        // Отклонение – заполнить дату факта
        if (newState.getName().equals("Отклонена") && order.getDateFinishFact() == null) {
            order.setDateFinishFact(Instant.now());
        }

        Order updated = orderRepo.save(order);
        return toResponse(updated);
    }
}
