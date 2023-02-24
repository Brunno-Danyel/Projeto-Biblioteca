package application.Exception;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ApiException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErros handleValidationException(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErros(bindingResult);
    }
}
