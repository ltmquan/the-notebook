package notebook.backend.repository.custom;

import java.util.List;

import notebook.backend.model.Flashcard;

public interface FlashcardRepositoryCustom {

	List<Flashcard> findByUserId(Long id);
}
