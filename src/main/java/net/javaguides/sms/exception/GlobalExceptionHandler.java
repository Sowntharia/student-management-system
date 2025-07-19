package net.javaguides.sms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerStudentNotFoundException(StudentNotFoundException ex, Model model) {
		model.addAttribute("message", ex.getMessage());
    	return "error/404";
    	
    }
	
    
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerGenericException(Exception ex, Model model) {
    	model.addAttribute("message", "An unexcepted error occured: " + ex.getMessage());
		return "error/500";
    	
    }
}
