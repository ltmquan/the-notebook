package notebook.backend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NoteTypeDTO {

	private Long id;
	
	@NotBlank
	private String name;
	
	@NotNull
	private Long userId;
	
	private String userName;
}
