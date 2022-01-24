package com.highway.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.highway.domain.User;
import com.highway.services.MapValidationErrorService;
import com.highway.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user,BindingResult result){
		if(result.hasErrors()) mapValidationErrorService.mapValidationService(result);
		User newUser = userService.saveUser(user);
		return new ResponseEntity<>(newUser,HttpStatus.OK);
	}
}
