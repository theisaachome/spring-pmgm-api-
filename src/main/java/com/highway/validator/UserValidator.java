package com.highway.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.highway.domain.User;

@Component 
public class UserValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("Validation Here......");
		User user= (User)target;
		if(user.getPassword().length()<6) {
			System.out.println("Password length.");
			errors.rejectValue("password" ,"length","Password must be at least 6 characters.");
		}
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			System.out.println("confirmpassword......");
			errors.rejectValue("confirmPassword" ,"Match","Passwords must match.");
		}
	}

}
