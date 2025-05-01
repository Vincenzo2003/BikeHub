package com.vincenzo.bikehub.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static ExceptionResponse mapCustomException(ResponseStatusException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                request.getRequestURI(), exception.getReason(), null, Instant.now()
        );
    }

    private static List<ValidationError> mapValidationErrors(MethodArgumentNotValidException exception) {
        List<FieldError> validationErrors = exception.getBindingResult().getFieldErrors();
        List<ValidationError> errors = new ArrayList<>(validationErrors.size());
        for (FieldError error : validationErrors) {
            String message = error.getDefaultMessage();
            if (message == null) {
                message = "Validation error";
            }
            errors.add(new ValidationError(
                    error.getField(),
                    List.of(message)
            ));
        }
        return errors;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<ValidationError> validationDetails = mapValidationErrors(exception);

        HashMap<String, Object> details = new HashMap<>();
        details.put("validationErrors", validationDetails);

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                request.getRequestURI(),
                exception.getBody().getDetail(),
                details,
                Instant.now()
        );
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(BicycleNotAvailableException.class)
    public ResponseEntity<ExceptionResponse> handleBicycleNotAvailableException(BicycleNotAvailableException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRentalNotFoundException(RentalNotFoundException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(RentalSavingException.class)
    public ResponseEntity<ExceptionResponse> handleRentalSavingException(RentalSavingException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(EquipmentSavingException.class)
    public ResponseEntity<ExceptionResponse> handleEquipmentSavingException(EquipmentSavingException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(BicycleSavingException.class)
    public ResponseEntity<ExceptionResponse> handleBicycleSavingException(BicycleSavingException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(BicycleChassisIdAlreadyRegisteredException.class)
    public ResponseEntity<ExceptionResponse> handleBicycleChassisIdAlreadyRegisteredException(BicycleChassisIdAlreadyRegisteredException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(ParkingLotNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleParkingLotNotFoundException(ParkingLotNotFoundException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(BicycleNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBicycleNotFoundException(BicycleNotFoundException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAccountNotFoundException(AccountNotFoundException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAccountAlreadyExistsException(AccountAlreadyExistsException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @ExceptionHandler(AccountCreationException.class)
    public ResponseEntity<ExceptionResponse> handleAccountCreationException(AccountCreationException exception, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = mapCustomException(exception, request);
        return new ResponseEntity<>(exceptionResponse, exception.getStatusCode());
    }

    @Getter
    @AllArgsConstructor
    private final static class ValidationError {
        private String fieldName;
        private List<String> errors;
    }

    @Getter
    @AllArgsConstructor
    public static final class ExceptionResponse {

        private String apiPath;

        private String message;

        private HashMap<String, Object> details;

        private Instant timestamp;

    }

}

