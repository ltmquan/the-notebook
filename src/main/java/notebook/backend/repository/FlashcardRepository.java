package notebook.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import notebook.backend.model.Flashcard;
import notebook.backend.repository.custom.FlashcardRepositoryCustom;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long>, FlashcardRepositoryCustom {

}
