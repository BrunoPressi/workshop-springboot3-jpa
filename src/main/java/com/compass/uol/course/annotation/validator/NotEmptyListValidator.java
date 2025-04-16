package com.compass.uol.course.annotation.validator;

import java.util.Set;

import com.compass.uol.course.annotation.NotEmptyList;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyListValidator implements ConstraintValidator<NotEmptyList, Set> {

	@Override
	public boolean isValid(Set set, ConstraintValidatorContext context) {
		return set != null && !set.isEmpty();
	}

	@Override
	public void initialize(NotEmptyList constraintAnnotation) {
		// TODO Auto-generated method stub
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

}
