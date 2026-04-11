package ru.altaiensb.service_desk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import org.openapitools.jackson.nullable.JsonNullable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class OrderUpdateDTO {

    private JsonNullable<String> name = JsonNullable.undefined();
    private JsonNullable<String> description = JsonNullable.undefined();
    private JsonNullable<Instant> dateFinishPlan = JsonNullable.undefined();
    private JsonNullable<Instant> dateFinishFact = JsonNullable.undefined();
    private JsonNullable<Integer> idOrderParent = JsonNullable.undefined();
    private JsonNullable<Integer> idOrderType = JsonNullable.undefined();
    private JsonNullable<Integer> idCatItem = JsonNullable.undefined();
    private JsonNullable<Integer> idService = JsonNullable.undefined();
    private JsonNullable<Integer> idOrderPriority = JsonNullable.undefined();
    private JsonNullable<Integer> idOrderState = JsonNullable.undefined();
    private JsonNullable<Integer> idCreator= JsonNullable.undefined();
    private JsonNullable<Integer> idInitiator = JsonNullable.undefined();
    private JsonNullable<Integer> idDispatcher = JsonNullable.undefined();
    private JsonNullable<Integer> idExecutor = JsonNullable.undefined();
    private JsonNullable<Integer> idOrderSource = JsonNullable.undefined();
    private JsonNullable<String> resultText = JsonNullable.undefined();
    private JsonNullable<Instant> datePostpone = JsonNullable.undefined();
    private JsonNullable<String> comment = JsonNullable.undefined();

}
