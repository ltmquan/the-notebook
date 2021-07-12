package notebook.backend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import notebook.backend.security.service.UserDetailsImpl;

public class UserUtil {

	public static Long getCurrentUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		return userDetails.getId();
	}
	
	public static String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		return userDetails.getUsername();
	}
	
	public static String getCurrentPassword() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		return userDetails.getPassword();
	}
	
	public static boolean userIdIsConsistent(Long userId) {
		Long currentId = getCurrentUserId();
		
		return currentId == userId;
	}
	
	
}
