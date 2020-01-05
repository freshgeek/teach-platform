package org.ck.teach.teachplatform.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/20 18:52
 * @description
 */
@Aspect
@SpringBootConfiguration
public class AspectConfiguration {


    @Pointcut("@annotation(org.ck.teach.teachplatform.annotation.TipLog)")
    public void tip(){}

    @Around("tip()")
    public Object aroundTip(ProceedingJoinPoint pjp) throws Throwable {


        Object proceed = pjp.proceed();

        return proceed;
    }



    @Pointcut("@annotation(org.ck.teach.teachplatform.annotation.VisitLog)")
    public void visit(){}

    @Around("visit()")
    public Object aroundVisit(ProceedingJoinPoint pjp) throws Throwable {

        Object proceed = pjp.proceed();

        return proceed;
    }




}
