package com.ruralhealth.healthcare.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//this annotation tells spring that this is the class you have to use for thrown exception by the mvc
@ControllerAdvice
public class GlobalExceptionHandler {
    //this tells which  exceptioon this method handles
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException exception, Model model) {

        model.addAttribute("errorMessage", exception.getMessage());

        return "error";
    }
}