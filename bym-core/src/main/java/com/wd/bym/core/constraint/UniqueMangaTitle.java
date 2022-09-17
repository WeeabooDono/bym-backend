package com.wd.bym.core.constraint;


import com.wd.bym.core.constraint.validator.UniqueMangaTitleValidator;
import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueMangaTitleValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueMangaTitle {

    String message() default "The title should be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
