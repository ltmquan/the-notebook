package notebook.backend.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class NotebookDTO {

	private Long id;
	
	@NotBlank
	private String name;
	
	private String description;
	
}
