package com.sptj.anno;

import com.sptj.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented //元注解
@Constraint(validatedBy = {StateValidation.class})
@Target({ElementType.FIELD})//元注解
@Retention(RetentionPolicy.RUNTIME)//元注解
public @interface State {
    //提供校验失败提示信息
    String message() default "state参数信息只能是已发布或草稿";

    //指定分组
    Class<?>[] groups() default {};

    //负载,获取到state注解的附加信息
    Class<? extends Payload>[] payload() default {};

}
