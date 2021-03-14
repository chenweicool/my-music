package com.mymusic.aop;
import java.lang.annotation.*;

/**
 * 自定义一个切面类注解,用来记录用户的访问记录
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Operation {
   String value() default "";  // 操作的方法名
   boolean intoDb() default false;//标识该条操作日志是否需要持久化存储。
}
