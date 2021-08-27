package notebook.backend.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import notebook.backend.model.Flashcard;
import notebook.backend.model.dto.FlashcardDTO;

@Mapper(componentModel="spring")
public interface FlashcardMapper extends EntityMapper<Flashcard, FlashcardDTO>{
	@Override
	Flashcard toEntity(FlashcardDTO flashcardDTO);
	
	@Override
	FlashcardDTO toDTO(Flashcard flashcard);
	
	default Flashcard fromId(Long id) {
		if (id == null) {
			return null;
		}
		
		Flashcard flashcard = new Flashcard();
		flashcard.setId(id);
		
		return flashcard;
	}
	
}
