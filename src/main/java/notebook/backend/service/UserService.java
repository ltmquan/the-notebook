package notebook.backend.service;

import notebook.backend.model.User;
import notebook.backend.model.dto.UserDTO;

public interface UserService {
	User findById(Long id);
	
	UserDTO findDTOById(Long id);
	
	User findByUsername(String username);
	
	void signin(String username);
	
	void logout();
	
	void create(User user);
	
	UserDTO update(UserDTO userDTO);
	
	void changeCredentials(String username, String password);
	
	void delete();
}
