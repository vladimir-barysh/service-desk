package ru.altaiensb.service_desk.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class OrderCreateDTO {
    private Integer nomer;
    private String name;
    private String description;
    private Instant dateCreated;
    private Instant dateFinishPlan;
    private Instant dateFinishFact;
    private Integer idOrderParent;
    private Integer idOrderType;
    private Integer idCatItem;
    private Integer idService;
    private Integer idOrderPriority;
    private Integer idCreator;
    private Integer idInitiator;
    private Integer idDispatcher;
    private Integer idExecutor;
    private Integer idOrderSource;
    private String resultText;
}
