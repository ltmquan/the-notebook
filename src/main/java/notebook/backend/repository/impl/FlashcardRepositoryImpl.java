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
	public List<Flashcard> findByUserId(Long id) {

		String sql = "from Flashcard e where e.user is not null and e.user.id = :id";
		Query query = entityManager.createQuery(sql, Flashcard.class);
		query.setParameter("id", id);
		
		return query.getResultList();
	}

}
