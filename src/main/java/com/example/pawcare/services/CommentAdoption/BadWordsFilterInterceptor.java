package com.example.pawcare.services.CommentAdoption;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BadWordsFilterInterceptor implements HandlerInterceptor {
    private static final List<String> BAD_WORDS = Arrays.asList("badword1", "badword2", "badword3");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getMethod().isAnnotationPresent(BadWordsFilter.class)) {
                String requestBody = new BufferedReader(new InputStreamReader(request.getInputStream()))
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator()));
                for (String badWord : BAD_WORDS) {
                    if (requestBody.contains(badWord)) {
                        response.setStatus(HttpStatus.BAD_REQUEST.value());
                        response.getWriter().write("Bad word detected: " + badWord);
                        return false;
                    }
                }
            }
        }
        return true;
    }

}

