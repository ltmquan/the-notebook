package notebook.backend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NotebookDTO {

	private Long id;
	
	@NotBlank
	private String name;
	
	private String description;
	
	@NotNull
	private Long userId;
	
	private String userName;
	
}
