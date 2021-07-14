package notebook.backend.api.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import notebook.backend.api.request.ChangeCredentialsRequest;
import notebook.backend.api.request.LoginRequest;
import notebook.backend.api.request.ResetPasswordRequest;
import notebook.backend.api.request.SignupRequest;
import notebook.backend.api.response.JwtResponse;
import notebook.backend.api.response.MessageResponse;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.constants.ResponseStatus;
import notebook.backend.model.User;
import notebook.backend.model.dto.UserDTO;
import notebook.backend.security.jwt.JwtUtil;
import notebook.backend.security.service.UserDetailsImpl;
import notebook.backend.service.UserService;
import notebook.backend.util.UserUtil;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
public class UserController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@PostMapping("/user/signin")
	public ResponseEntity<JwtResponse> authenticate(@RequestBody @Valid LoginRequest loginRequest) {
		
		userService.signin(loginRequest.getUsername());
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = null;
		try {
			jwt = jwtUtil.generateJwtToken(authentication);
		} catch (ParseException e) { }
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String role = userDetails.getAuthority().getAuthority();
		
		return ResponseEntity.ok(new JwtResponse(jwt,
												userDetails.getId(),
												userDetails.getName(),
												userDetails.getUsername(),
												userDetails.getEmail(),
												role));
		
	}
	
	@GetMapping("/api/user/logout")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MessageResponse> logout() {
		userService.logout();
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.LOGOUT, EntityName.USER));
	}
	
	@PostMapping("/user/signup")
	public ResponseEntity<MessageResponse> create(@RequestBody @Valid SignupRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEmail(request.getEmail());
		user.setLoggedIn(false);
		
		userService.create(user);
		
		return ResponseEntity.ok().body(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.CREATE, EntityName.USER));
	}
	
	@PostMapping("/user/forgot-password/email")
	public ResponseEntity<MessageResponse> sendOtpEmail(@RequestBody String email) {
		userService.sendOtpEmail(email);
		
		return ResponseEntity.ok().body(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.SEND_EMAIL, EntityName.USER));
	}
	
	@PostMapping("/user/forgot-password/otp")
	public ResponseEntity<MessageResponse> resetPasswordWithOtp(@RequestBody ResetPasswordRequest request) {
		userService.resetPasswordWithOtp(request.getOtp(), passwordEncoder.encode(request.getNewPassword()));
		
		return ResponseEntity.ok().body(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.UPDATE, EntityName.USER));
	}
	
	@PutMapping("/api/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserDTO> update(@RequestBody @Valid UserDTO userDTO) {
		UserDTO dto = userService.update(userDTO);
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/api/user/credentials")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MessageResponse> changeCredentials(@RequestBody @Valid ChangeCredentialsRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(UserUtil.getCurrentUsername(), request.getOldPassword()));
		
		String passwordEncoded = passwordEncoder.encode(request.getNewPassword());
		userService.changeCredentials(request.getUsername(), passwordEncoded);
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), passwordEncoded));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.UPDATE, EntityName.USER));
	}
	
	@DeleteMapping("/api/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MessageResponse> delete() {
		userService.delete();
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.DELETE, EntityName.USER));
	}
}

