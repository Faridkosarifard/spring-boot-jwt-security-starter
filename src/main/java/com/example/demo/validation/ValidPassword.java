package com.example.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.http.HttpStatus;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 4:24 PM
 */
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Documented
public @interface ValidPassword {
    HttpStatus message() default HttpStatus.NOT_ACCEPTABLE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
