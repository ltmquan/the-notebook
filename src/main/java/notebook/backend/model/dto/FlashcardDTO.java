package notebook.backend.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class FlashcardDTO {

	private Long id;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String question;
	
	@NotBlank
	private String content;
	
	@NotNull
	private Long noteId;
	
	private String noteName;
}
