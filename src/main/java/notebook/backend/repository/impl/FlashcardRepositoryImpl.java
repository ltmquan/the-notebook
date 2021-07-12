package notebook.backend.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import notebook.backend.model.Flashcard;
import notebook.backend.repository.custom.FlashcardRepositoryCustom;

public class FlashcardRepositoryImpl implements FlashcardRepositoryCustom{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Flashcard> findByNoteId(Long id) {

		String sql = "from Flashcard e where e.note is not null and e.note.id = :id";
		Query query = entityManager.createQuery(sql, Flashcard.class);
		query.setParameter("id", id);
		
		return query.getResultList();
	}

}
