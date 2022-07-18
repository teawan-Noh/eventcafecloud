package com.eventcafecloud.log4j;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Log4j2
@Component
@Aspect
public class OutputLogAspect {
    private static final Logger logger = LogManager.getLogger(OutputLogAspect.class);

    // api 가 성공적으로 동작한 후에 AOP 동작
    @AfterReturning(pointcut = "execution(* com.eventcafecloud.exception.Error.*(..))", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        // 해당 api 이름 가져오기 위해서 (메소드 이름)
        MethodSignature methodSignature = (MethodSignature) jp.getSignature();
        Method method = methodSignature.getMethod();
        logger.error("---- output ----");
        logger.error("메소드: " + method.getName() + "()");
        logger.error("파라미터: " + result);
        logger.error("---- end output ----\n");
    }
}
