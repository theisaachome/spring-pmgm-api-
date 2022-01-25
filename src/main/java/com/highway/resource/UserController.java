package com.highway.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.highway.domain.User;
import com.highway.payload.JWTLoginSuccessResponse;
import com.highway.payload.LoginRequest;
import com.highway.security.JwtTokenProvider;
import com.highway.security.SecurityConstants;
import com.highway.services.MapValidationErrorService;
import com.highway.services.UserService;
import com.highway.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private MapValidationErrorService mapValidationErrorService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(
			@Valid @RequestBody LoginRequest loginRequest,
			BindingResult result
			){
		if(result.hasErrors()) mapValidationErrorService.mapValidationService(result);
		Authentication authentication=authenticationManager
				.authenticate(
						new UsernamePasswordAuthenticationToken(
								loginRequest.getUsername(),
								loginRequest.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=SecurityConstants.TOKEN_PREFIX + tokenProvider.generateToken(authentication);
		
		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user,BindingResult result){
		userValidator.validate(user, result);
		
		if(result.hasErrors()) mapValidationErrorService.mapValidationService(result);
		User newUser = userService.saveUser(user);
		return new ResponseEntity<>(newUser,HttpStatus.OK);
	}
}
