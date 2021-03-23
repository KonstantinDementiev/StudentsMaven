package org.geekhub.studentsregistry.web.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorWebController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            return switch (statusCode) {
                case 404 -> "errors/error-404";
                case 403 -> "errors/error-403";
                case 500 -> "errors/error-500";
                default -> "errors/error";
            };
        }
        return "errors/error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
