package notebook.backend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.constants.UserRoles;
import notebook.backend.messages.Messages;
import notebook.backend.model.User;
import notebook.backend.model.dto.UserDTO;
import notebook.backend.repository.UserRepository;
import notebook.backend.service.EmailService;
import notebook.backend.service.OtpService;
import notebook.backend.service.RoleService;
import notebook.backend.service.UserService;
import notebook.backend.service.mapper.UserMapper;
import notebook.backend.util.UserUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleService roleService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	OtpService otpService;

	@Autowired
	UserMapper userMapper;

	@Override
	public User findById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		if (!userOptional.isPresent()) {
			throw new BadRequestException(Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST, ApiActions.RETRIEVE,
					EntityName.USER);
		}
		return userOptional.get();
	}

	@Override
	public UserDTO findDTOById(Long id) {
		return userMapper.toDTO(this.findById(id));
	}

	@Override
	public User findByUsername(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (!userOptional.isPresent()) {
			throw new BadRequestException(Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST, ApiActions.RETRIEVE,
					EntityName.USER);
		}
		return userOptional.get();
	}
	
	@Override
	public User findByEmail(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (!userOptional.isPresent()) {
			throw new BadRequestException(Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST, ApiActions.RETRIEVE,
					EntityName.USER);
		}
		return userOptional.get();
	}

	@Override
	public void signin(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		if (!userOptional.isPresent()) {
			throw new BadRequestException(Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST, ApiActions.SIGNIN,
					EntityName.USER);
		}

		User user = userOptional.get();
		user.setLoggedIn(true);

		userRepository.save(user);
	}

	@Override
	public void logout() {
		Long currentId = UserUtil.getCurrentUserId();

		User user = userRepository.findById(currentId).get();

		user.setLoggedIn(false);

		userRepository.save(user);
	}
	
	@Override
	public void sendOtpEmail(String email) {
		User user = this.findByEmail(email);
		
		String otp = otpService.generateOtp(user.getUsername());
		
		emailService.sentOtpMail(email, otp);
	}
	
	@Override
	public void resetPasswordWithOtp(String otp, String newPassword) {
		String username = otpService.findByOtp(otp);
		
		User user = this.findByUsername(username);
		user.setPassword(newPassword);
		
		userRepository.save(user);
	}

	@Override
	public void create(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new BadRequestException(Messages.ERROR_USERNAME_ALREADY_EXISTS, ApiActions.CREATE, EntityName.USER);
		}
		
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new BadRequestException(Messages.ERROR_EMAIL_ALREADY_EXISTS, ApiActions.CREATE, EntityName.USER);
		}

		user.setRole(roleService.findById(UserRoles.ROLE_USER_ID));
		userRepository.save(user);
	}

	@Override
	public UserDTO update(UserDTO userDTO) {
		if (userDTO.getId() == null || !userRepository.existsById(userDTO.getId())) {
			throw new BadRequestException(Messages.ERROR_INVALID_REQUEST_DATA, ApiActions.UPDATE, EntityName.USER);
		}

		if (!UserUtil.userIdIsConsistent(userDTO.getId())) {
			throw new BadRequestException(Messages.ERROR_INVALID_REQUEST, ApiActions.UPDATE, EntityName.USER);
		}

		User user = userMapper.toEntity(userDTO);
		user.setUsername(UserUtil.getCurrentUsername());
		user.setPassword(UserUtil.getCurrentPassword());
		user.setRole(roleService.findById(UserRoles.ROLE_USER_ID));
		user.setLoggedIn(true);

		return userMapper.toDTO(userRepository.save(user));
	}

	@Override
	public void changeCredentials(String username, String password) {
		Long currentId = UserUtil.getCurrentUserId();

		User user = userRepository.findById(currentId).get();

		user.setPassword(password);
		user.setUsername(username);
		userRepository.save(user);
	}

	@Override
	public void delete() {
		Long currentId = UserUtil.getCurrentUserId();

		userRepository.deleteById(currentId);
	}

}
