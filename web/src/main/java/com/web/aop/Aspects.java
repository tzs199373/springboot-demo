package com.web.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Aspects {

    @Pointcut("execution(public * com.web.aop.Target.*(..))")
    public void pointCut(){};

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName() +"的前置通知，参数："+ joinPoint.getArgs()[0]);
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName() +"的后置通知，参数："+ joinPoint.getArgs()[0]);
    }

    @AfterReturning(value = "pointCut()" , returning = "result" )
    //注意JoinPoint参数一定要放在第一个
    public void logReturn(JoinPoint joinPoint,Object result){
        System.out.println(joinPoint.getSignature().getName() +"的后置返回通知，返回值："+result);
    }

    @AfterThrowing(value = "pointCut()" , throwing = "exception")
    public void logException(Exception exception){
        System.out.println("后置异常通知:"+exception);
    }

    @Around(value = "pointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println(joinPoint.getSignature().getName()+"的joinpoint.proceed之前的环绕通知,捕捉到参数："+(String) joinPoint.getArgs()[0]);

        // 执行被拦截方法（目标方法），此处可修改入参，并接收方法执行后的返回值
        Object rvt = joinPoint.proceed(new String[] { (String) joinPoint.getArgs()[0]+",环绕通知proceed时的修改"});

        System.out.println(joinPoint.getSignature().getName()+"的joinpoint.proceed之后的环绕通知");

        //可对目标方法返回值进行修改，返回后通知方法参数obj为本方法的返回值(若本方法无返回值,obj为null)
        return rvt + "，环绕return时的修改";
    }
}

/**
 * 结果
 targetMethod的joinpoint.proceed之前的环绕通知,捕捉到参数：hello
 targetMethod的前置通知，参数：hello,环绕通知proceed时的修改
 目标方法：hello,环绕通知proceed时的修改
 targetMethod的joinpoint.proceed之后的环绕通知
 targetMethod的后置通知
 targetMethod的后置返回通知，返回值：hello,环绕通知proceed时的修改，环绕return时的修改
 */