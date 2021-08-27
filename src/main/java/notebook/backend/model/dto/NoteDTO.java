package notebook.backend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class NoteDTO {

	private Long id;
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String content;
	
	private Long typeId;
	
	private String typeName;
	
	@NotNull
	private Long notebookId;
	
	private String notebookName;
}
