package notebook.backend.service;

import java.util.List;

import notebook.backend.model.Flashcard;
import notebook.backend.model.dto.FlashcardDTO;

public interface FlashcardService {
	Flashcard findById(Long id);
	
	FlashcardDTO findDTOById(Long id);
	
	List<FlashcardDTO> findByCurrentUser();
	
	void create(FlashcardDTO flashcardDTO);
	
	void update(FlashcardDTO flashcardDTO);
	
	void deleteById(Long id);
	
}
