package com.phoenix.config.database;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Aspect
@Configuration
public class ReadOnlyAnnotationInterceptor implements Ordered {

    /**
     * 切入点
     */
    @Pointcut("@annotation(com.phoenix.config.database.ReadOnlyAnnotation)")
    private void pointcut() {}

    //Around 我们从程序进入就开始处理，直至结束
    @Around("pointcut()")
    public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            System.out.println("==================开始切换数据源为只读，从数据源==================");
            DataBaseContextHolder.setDataBaseType(DataBaseContextHolder.DataBaseType.SLAVE);
            Object proceed = joinPoint.proceed();
            return proceed;

        } finally {
            System.out.println("=================切换为主数据源=========================");
            DataBaseContextHolder.clearDataBaseType();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
