package au.andrew.palindrome.demo.api.advice;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = WordConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface WordValidator {
    String field() default "{word}";
    String message() default "word is empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
