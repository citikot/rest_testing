package it.discovery.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class TitleValidator implements ConstraintValidator<TitleConstraint, String> {
    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(title)) {
            return false;
        }
        char first = title.charAt(0);
        return Character.isUpperCase(first);
    }
}
