package com.ruralhealth.publichealth.exception;
import com.ruralhealth.publichealth.dto.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ResourceNotFoundException.class)
 ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException e){return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));}
 @ExceptionHandler(BusinessRuleException.class)
 ResponseEntity<ErrorResponse> conflict(BusinessRuleException e){return ResponseEntity.status(409).body(new ErrorResponse(e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 ResponseEntity<ErrorResponse> invalid(MethodArgumentNotValidException e){
   Map<String,String> fields=new LinkedHashMap<>();
   e.getBindingResult().getFieldErrors().forEach(x->fields.putIfAbsent(x.getField(),x.getDefaultMessage()));
   return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed",fields));
 }
 @ExceptionHandler({ConstraintViolationException.class,HttpMessageNotReadableException.class,IllegalArgumentException.class})
 ResponseEntity<ErrorResponse> badRequest(Exception e){return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed"));}
 @ExceptionHandler(DataIntegrityViolationException.class)
 ResponseEntity<ErrorResponse> dataConflict(DataIntegrityViolationException e){return ResponseEntity.status(409).body(new ErrorResponse("Conflicting data"));}
 @ExceptionHandler(Exception.class)
 ResponseEntity<ErrorResponse> unexpected(Exception e){return ResponseEntity.status(500).body(new ErrorResponse("Unexpected server error"));}
}
