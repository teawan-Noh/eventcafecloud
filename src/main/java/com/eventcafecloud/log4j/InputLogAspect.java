package com.eventcafecloud.log4j;

import com.eventcafecloud.user.domain.User;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Log4j2
@Component
@Aspect
public class InputLogAspect {
    private static final Logger logger = LogManager.getLogger(InputLogAspect.class);

    // api 가 동작하기 이전에 AOP 동작
    @Before("execution(* com.eventcafecloud.exception.Error.*(..))")
    public void before(JoinPoint jp) {
        // 해당 api 이름 가져오기 위해서 (메소드 이름)
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        Object[] params = jp.getArgs();
        String email = ((User) params[1]).getUserEmail();
        logger.error("---- input ----");
        logger.error("유저:" + email);
        logger.error("메소드: " + method.getName() + "()");
        logger.error("---- end input ----\n");
    }
}