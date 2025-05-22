package ru.kuksov.compapi.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Component
public class CustomExceptionResolver extends AbstractHandlerExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
        final ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        if (e instanceof NotFoundException) {
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
            modelAndView.addObject("message", "Пользователь не найден");
            return modelAndView;
        }
        modelAndView.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        modelAndView.addObject("message", "При выполнении запроса произошла ошибка");
        return modelAndView;
    }

}