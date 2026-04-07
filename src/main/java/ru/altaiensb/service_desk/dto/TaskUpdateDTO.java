package ru.altaiensb.service_desk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import org.openapitools.jackson.nullable.JsonNullable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TaskUpdateDTO {

    private JsonNullable<Integer> idOrder = JsonNullable.undefined();
    private JsonNullable<Integer> idOrderTaskParent = JsonNullable.undefined();
    private JsonNullable<Integer> idWork = JsonNullable.undefined();
    private JsonNullable<Integer> idExecutor = JsonNullable.undefined();
    private JsonNullable<Instant> dateFinishPlan = JsonNullable.undefined();
    private JsonNullable<Instant> dateFinishFact = JsonNullable.undefined();
    private JsonNullable<String> description = JsonNullable.undefined();
    private JsonNullable<Boolean> closeParentCheck = JsonNullable.undefined();
    private JsonNullable<Integer> idTaskState = JsonNullable.undefined();
    private JsonNullable<Integer> idCreator = JsonNullable.undefined();
    private JsonNullable<String> resultText = JsonNullable.undefined();

}
