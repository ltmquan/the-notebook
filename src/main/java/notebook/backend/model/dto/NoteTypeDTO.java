package notebook.backend.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NoteTypeDTO {

	private Long id;
	
	@NotBlank
	private String name;
}
