package com.mymusic.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统日志，切面的处理类
 */
@Aspect
@Component
@Slf4j
public class SysLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(SysLogAspect.class);

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    /**
     * 横切点
     */
    @Pointcut("execution(public * com.mymusic.controller..*.*(..))")
    public void webLog() {
    }
    /**
     * 接收请求，并记录数据
     * @param joinPoint
     */
 //   @Before(value = "webLog()&&@annotation(controllerWebLog)")
    @Before( "webLog()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 记录请求内容，threadInfo存储所有内容
        Map<String, Object> threadInfo = new HashMap<>();
        logger.info("URL : " + request.getRequestURL());
        threadInfo.put("url", request.getRequestURL());
        logger.info("URI : " + request.getRequestURI());
        threadInfo.put("uri", request.getRequestURI());
        logger.info("HTTP_METHOD : " + request.getMethod());
        threadInfo.put("httpMethod", request.getMethod());
        logger.info("REMOTE_ADDR : " + request.getRemoteAddr());
        threadInfo.put("ip", request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
        threadInfo.put("classMethod",
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        threadInfo.put("args", Arrays.toString(joinPoint.getArgs()));
        logger.info("USER_AGENT"+request.getHeader("User-Agent"));
        threadInfo.put("userAgent", request.getHeader("User-Agent"));
//        logger.info("执行方法：" + controllerWebLog.value());
//        threadInfo.put("methodName", controllerWebLog.value());
        threadLocal.set(threadInfo);
    }
    /**
     * 执行成功后处理
     * @param ret
     * @throws Throwable
     */
   // @AfterReturning(value = "webLog()&& @annotation(controllerWebLog)", returning = "ret")
    @AfterReturning(value = "webLog()", returning = "ret")
    public void doAfterReturning( Object ret) throws Throwable {
//        Map<String, Object> threadInfo = threadLocal.get();
//        threadInfo.put("result", ret);
//        if (controllerWebLog.intoDb()) {
//            //插入数据库操作
//            //insertResult(threadInfo);
//        }
//         //理完请求，返回内容
//        logger.info("RESPONSE : " + ret);
    }
    /**
     * 获取执行时间
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around(value = "webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object ob = proceedingJoinPoint.proceed();
        Map<String, Object> threadInfo = threadLocal.get();
        Long takeTime = System.currentTimeMillis() - startTime;
         threadInfo.put("takeTime", takeTime);
        logger.info("耗时：" + takeTime);
      //  threadLocal.set(threadInfo);
        return ob;
    }
    /**
     * 异常处理
     * @param throwable
     */
    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();

        ServletRequestAttributes sra = (ServletRequestAttributes) ra;

        HttpServletRequest request = sra.getRequest();
        // 异常信息
        logger.error("{}接口调用异常，异常信息{}", request.getRequestURI(), throwable);
    }

}
