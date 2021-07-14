package notebook.backend.api.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResetPasswordRequest {

	@NotBlank
	private String otp;
	
	@NotBlank
	private String newPassword;
	
}
