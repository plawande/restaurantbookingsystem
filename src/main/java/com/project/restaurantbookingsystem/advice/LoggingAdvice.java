package com.project.restaurantbookingsystem.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAdvice {

    /*@Pointcut(value = "execution(* com.project.restaurantbookingsystem.controller.BookingController.createReservation(..) )")
    public void myPointcut() {

    }*/

    //@Around("myPointcut()")
    @Around("@annotation(com.project.restaurantbookingsystem.annotation.TrackDetails)")
    public Object appLogger(ProceedingJoinPoint pjp) throws Throwable {
        String method = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] args = pjp.getArgs();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        log.info("method invoked {}:{}(), arguments: {}", className, method, mapper.writeValueAsString(args));
        Object object = pjp.proceed();
        log.info("method invoked {}:{}(), response: {}", className, method, mapper.writeValueAsString(object));
        return object;
    }
}
