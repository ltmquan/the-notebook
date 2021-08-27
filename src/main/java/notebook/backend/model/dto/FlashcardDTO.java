package notebook.backend.model.dto;

import javax.validation.constraints.NotBlank;

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
}
