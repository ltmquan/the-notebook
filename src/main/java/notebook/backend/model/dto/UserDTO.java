package notebook.backend.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserDTO {

	private Long id;
	
	@NotBlank
	private String name;
	
	private String username;
	
	@Email
	@NotBlank
	private String email;
	
	private String roleName;
	
}
