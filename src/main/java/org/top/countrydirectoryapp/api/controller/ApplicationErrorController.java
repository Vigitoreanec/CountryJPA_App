package org.top.countrydirectoryapp.api.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.top.countrydirectoryapp.api.message.CommonApiMessage.ErrorMessage;

@RestController
public class ApplicationErrorController implements ErrorController {

    @RequestMapping("error")
    public ErrorMessage errorMessage(HttpServletRequest request){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (message == null || message.toString().isEmpty()) {
            message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        }
        return new ErrorMessage(status.toString(), message.toString());
    }
}
