package notebook.backend.api.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChangeCredentialsRequest {
	
	@NotBlank
	private String username;

	@NotBlank
	private String oldPassword;
	
	private String newPassword;
}
