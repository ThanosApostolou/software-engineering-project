package web.prices.observatory.Exceptions;


import web.prices.observatory.Exceptions.NotAuthorized;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class NotAuthorizedAdvice {

    @ResponseBody
    @ExceptionHandler(NotAuthorized.class)
    @ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    String NotAuthorizedHandler(NotAuthorized ex) {return  ex.getMessage();}

}
