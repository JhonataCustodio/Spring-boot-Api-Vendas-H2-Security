package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.validation.constraint;


import java.util.List;

import com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.validation.NotEmptyList;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, List>{

    @Override
    public void initialize(NotEmptyList constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        return list != null && !list.isEmpty();
    }
    
}
