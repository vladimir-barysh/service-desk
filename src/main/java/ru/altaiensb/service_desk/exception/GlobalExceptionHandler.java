package ru.altaiensb.service_desk.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

/**
 * Обработчик для ситуации, когда запрашиваемый ресурс не найден
 * Возвращает HTTP 404 Not Found
 */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


/**
 * Обработчик для ошибок валидации входных данных (например, @Valid не пройдена)
 * Возвращает HTTP 400 Bad Request с деталями о некорректных полях
 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                message,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }


/**
 * Обработчик для ошибок формата тела запроса (например, неверный JSON или неправильный формат даты)
 * Возвращает HTTP 400 Bad Request с указанием причины
 */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request
    ) {
        log.error("Ошибка формата данных: ", ex);
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Неверный формат данных. Проверьте соответствие типов полей",
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }


/**
 * Обработчик для ситуации, когда параметр запроса имеет неверный тип
 * (например, http://.../api/approve/abc, где вместо abc должен быть число id)
 * Возвращает HTTP 400 Bad Request
 */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String message = String.format("Параметр '%s' должен иметь тип %s",
                ex.getName(),
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "неизвестный тип");
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                message,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }


/**
 * Обработчик для ситуации, когда отсутствует обязательный параметр запроса
 * (например, http://.../api/approve/?name=)
 * Возвращает HTTP 400 Bad Request.
 */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Отсутствует обязательный параметр: " + ex.getParameterName(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }


/**
 * Обработчик для неподдерживаемого HTTP метода (например, POST вместо GET)
 * Возвращает HTTP 405 Method Not Allowed с указанием разрешённых методов
 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {
        String supportedMethods = String.join(", ", ex.getSupportedMethods() != null ? ex.getSupportedMethods() : new String[0]);
        String message = String.format("Метод %s не поддерживается для этого эндпоинта. Разрешённые методы: %s",
                ex.getMethod(), supportedMethods);
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Method Not Allowed",
                message,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }



    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(
            NoResourceFoundException ex,
            HttpServletRequest request
    ) {
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                "Ресурс не найден: " + request.getRequestURI(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }



/**
 * Обработчик для 
 */    

    @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.of(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
        }


/**
 * Обработчик для всех необработанных исключений
 * Возвращает HTTP 500 Internal Server Error. Скрывает детали стека от клиента, выводя их в консоль
 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Тип необработанного исключения: {}", ex.getClass().getName(), ex);
        ex.printStackTrace();
        ErrorResponse error = ErrorResponse.of(
                500,
                "Internal Server Error",
                "Произошла непредвиденная ошибка на сервере",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}