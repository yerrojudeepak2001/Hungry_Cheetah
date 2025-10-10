package com.foodapp.common.exception;package com.foodapp.common.exception;



import io.github.resilience4j.circuitbreaker.CallNotPermittedException;import com.foodapp.common.dto.ErrorResponse;

import org.springframework.http.HttpStatus;import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

import org.springframework.http.ResponseEntity;import io.github.resilience4j.ratelimiter.RequestNotPermitted;

import org.springframework.validation.FieldError;import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;import java.util.Map;

import java.util.Map;import java.util.HashMap;



@RestControllerAdvice@RestControllerAdvice

public class GlobalExceptionHandler {public class GlobalExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)    @ExceptionHandler(ResourceNotFoundException.class)

    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {

        Map<String, Object> error = new HashMap<>();        ErrorResponse error = new ErrorResponse(

        error.put("status", HttpStatus.NOT_FOUND.value());                HttpStatus.NOT_FOUND.value(),

        error.put("error", "Resource Not Found");                "Resource Not Found",

        error.put("message", ex.getMessage());                ex.getMessage(),

        error.put("timestamp", System.currentTimeMillis());                System.currentTimeMillis()

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex) {

    }        Map<String, Object> error = new HashMap<>();

        error.put("status", HttpStatus.NOT_FOUND.value());

    @ExceptionHandler(CallNotPermittedException.class)        error.put("error", "Resource Not Found");

    public ResponseEntity<Map<String, Object>> handleCircuitBreakerError(CallNotPermittedException ex) {        error.put("message", ex.getMessage());

        Map<String, Object> error = new HashMap<>();        error.put("timestamp", System.currentTimeMillis()

        error.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());        );

        error.put("error", "Service Temporarily Unavailable");        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        error.put("message", "Service is currently unavailable. Please try again later.");    }

        error.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);    @ExceptionHandler(CallNotPermittedException.class)

    }    public ResponseEntity<ErrorResponse> handleCircuitBreakerError(CallNotPermittedException ex) {

        ErrorResponse error = new ErrorResponse(

    @ExceptionHandler(DuplicateResourceException.class)                HttpStatus.SERVICE_UNAVAILABLE.value(),

    public ResponseEntity<Map<String, Object>> handleDuplicateResource(DuplicateResourceException ex) {                "Service Temporarily Unavailable",

        Map<String, Object> error = new HashMap<>();                "Service is currently unavailable. Please try again later.",

        error.put("status", HttpStatus.CONFLICT.value());                System.currentTimeMillis()

        error.put("error", "Duplicate Resource");        );

        error.put("message", ex.getMessage());        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);

        error.put("timestamp", System.currentTimeMillis());    }

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

    }    @ExceptionHandler(RequestNotPermitted.class)

    public ResponseEntity<ErrorResponse> handleRateLimitError(RequestNotPermitted ex) {

    @ExceptionHandler(MethodArgumentNotValidException.class)        ErrorResponse error = new ErrorResponse(

    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {                HttpStatus.TOO_MANY_REQUESTS.value(),

        Map<String, Object> error = new HashMap<>();                "Too Many Requests",

        Map<String, String> fieldErrors = new HashMap<>();                "Request limit exceeded. Please try again later.",

                        System.currentTimeMillis()

        ex.getBindingResult().getAllErrors().forEach((errorObj) -> {        );

            String fieldName = ((FieldError) errorObj).getField();        return new ResponseEntity<>(error, HttpStatus.TOO_MANY_REQUESTS);

            String errorMessage = errorObj.getDefaultMessage();    }

            fieldErrors.put(fieldName, errorMessage);

        });    @ExceptionHandler(ResourceAccessException.class)

            public ResponseEntity<ErrorResponse> handleResourceAccessError(ResourceAccessException ex) {

        error.put("status", HttpStatus.BAD_REQUEST.value());        ErrorResponse error = new ErrorResponse(

        error.put("error", "Validation Failed");                HttpStatus.BAD_GATEWAY.value(),

        error.put("message", "Input validation failed");                "Service Communication Error",

        error.put("fieldErrors", fieldErrors);                "Unable to communicate with dependent service.",

        error.put("timestamp", System.currentTimeMillis());                System.currentTimeMillis()

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);        );

    }        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);

    }

    @ExceptionHandler(Exception.class)

    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {    @ExceptionHandler(PaymentProcessingException.class)

        Map<String, Object> error = new HashMap<>();    public ResponseEntity<ErrorResponse> handlePaymentError(PaymentProcessingException ex) {

        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());        ErrorResponse error = new ErrorResponse(

        error.put("error", "Internal Server Error");                HttpStatus.PAYMENT_REQUIRED.value(),

        error.put("message", "An unexpected error occurred");                "Payment Processing Error",

        error.put("timestamp", System.currentTimeMillis());                ex.getMessage(),

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);                System.currentTimeMillis()

    }        );

}        return new ResponseEntity<>(error, HttpStatus.PAYMENT_REQUIRED);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(ValidationException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurityError(SecurityException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Security Error",
                ex.getMessage(),
                System.currentTimeMillis()
        );
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Map<String, Object>> handleResourceAccessError(ResourceAccessException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.BAD_GATEWAY.value());
        error.put("error", "Service Communication Error");
        error.put("message", "Unable to communicate with dependent service.");
        error.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, Object>> handleSecurityError(SecurityException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.FORBIDDEN.value());
        error.put("error", "Security Error");
        error.put("message", ex.getMessage());
        error.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericError(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                System.currentTimeMillis()
        );
    public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Internal Server Error");
        error.put("message", "An unexpected error occurred. Please try again later.");
        error.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}