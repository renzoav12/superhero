package com.example.superhero.aop;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Value("${logging.active}")
    private String isEnable;

    /**
     * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
     *
     * @param joinPoint join point we want the logger for.
     * @return {@link Logger} associated to the given {@link JoinPoint}.
     */
    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice.
     * @return result.
     * @throws Throwable throws {@link IllegalArgumentException}.
     */
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        if(Boolean.valueOf(isEnable)) {
            Logger log = logger(joinPoint);
            long start = System.currentTimeMillis();
            if (log.isDebugEnabled()) {
                log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(),
                        Arrays.toString(joinPoint.getArgs()));
            }
            try {
                Object result = joinPoint.proceed();
                if (log.isDebugEnabled()) {
                    log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
                }
                long executionTime = System.currentTimeMillis() - start;
                log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
                return result;
            } catch (IllegalArgumentException e) {
                log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()),
                        joinPoint.getSignature().getName());
                throw e;
            }
        }
        return joinPoint.proceed();
    }

}
