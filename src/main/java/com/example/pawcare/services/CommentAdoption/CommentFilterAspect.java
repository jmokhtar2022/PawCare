package com.example.pawcare.services.CommentAdoption;

import com.example.pawcare.entities.Comment;
import com.example.pawcare.entities.CommentAdoption;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommentFilterAspect {
    private CommentFilter commentFilter;

    public CommentFilterAspect(CommentFilter commentFilter) {
        this.commentFilter = commentFilter;
    }

    @Around("@annotation(FilterComments)")
    public Object filterComments(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof CommentAdoption) {
                CommentAdoption comment = (CommentAdoption) arg;
                comment.setText(commentFilter.filter(comment.getText()));
            }
        }
        return joinPoint.proceed(args);
    }
}
