package au.andrew.palindrome.demo.api.advice;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class WordConstraintValidator implements ConstraintValidator<WordValidator, String> {

    private String field;

    @Override
    public void initialize(WordValidator constraintAnnotation) {
        field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isEmpty(s)) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(field + " is empty").addConstraintViolation();
                return false;
            } else if(s.trim().contains(" ")){
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(field + " field is not a single word").addConstraintViolation();
                return false;
            } else
                return true;
        } catch (Exception e) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid " + field + " format").addConstraintViolation();
        }
        return false;
    }
}
