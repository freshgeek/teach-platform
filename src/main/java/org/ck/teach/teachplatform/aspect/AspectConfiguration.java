package org.ck.teach.teachplatform.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.util.SessionUtils;
import org.apache.naming.factory.BeanFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.ck.teach.teachplatform.annotation.TipLog;
import org.ck.teach.teachplatform.common.BaseController;
import org.ck.teach.teachplatform.entity.User;
import org.ck.teach.teachplatform.entity.UserTip;
import org.ck.teach.teachplatform.util.WebUtils;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/20 18:52
 * @description
 */
@Aspect
@Slf4j
@SpringBootConfiguration
public class AspectConfiguration {

    @Around("@annotation(org.ck.teach.teachplatform.annotation.TipLog)")
    public Object aroundTip(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        UserTip userTip = new UserTip();

        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext(pjp.getArgs());

        String targetName = pjp.getTarget().getClass().getName();
        String simpleName = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Object[] arguments = pjp.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String key = "";
        String[] paramNames = {};
        for(Method method:methods){
            if(method.getName().equals(methodName)){
                key = method.getAnnotation(TipLog.class).userId();
                System.out.println("key is "+key);
                paramNames = getParamNames(method);
            }
        }

        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        for(int i=0;i<arguments.length;i++){
            context.setVariable(paramNames[i],arguments[i]);
        }
        System.out.println(expression.getValue(context,String.class));
        userTip.setCreateTime(new Date());
        userTip.setReaded("0");
        userTip.setContent(pjp.getSignature().getName());
        User user = WebUtils.getLoginSessionUser();
        userTip.setResourceUserId(user.getId());
        log.debug("测试{}", user);
        return proceed;
    }



    public String[] getParamNames(Method method){
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        return  u.getParameterNames(method);
    }

    @Pointcut("@annotation(org.ck.teach.teachplatform.annotation.VisitLog)")
    public void visit() {
    }

    @Around("visit()")
    public Object aroundVisit(ProceedingJoinPoint pjp) throws Throwable {

        Object proceed = pjp.proceed();

        return proceed;
    }


}
