package notebook.backend.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

	private String accessToken;
	
	private final String type = "Bearer ";
	
	private Long id;
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String role;
	
}
