package org.top.countrydirectoryapp.api.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.top.countrydirectoryapp.api.message.CommonApiMessage.*;

@RestController
@RequestMapping("api")
public class StatusController {

    private final HttpServletRequest request;

    public StatusController(HttpServletRequest request) {
        this.request = request;
    }

    @GetMapping
    public ServerStatus status(){
        return new ServerStatus("Server is running",
                "localhost:"+request.getLocalPort()+request.getRequestURI(),
                request.getScheme() + ":" + request.getMethod());
    }

    @GetMapping("ping")
    public StringMessage ping(){
        return new StringMessage("Pong");
    }
}
