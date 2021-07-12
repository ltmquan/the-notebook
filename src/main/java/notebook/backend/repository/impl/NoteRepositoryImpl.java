package notebook.backend.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import notebook.backend.model.Note;
import notebook.backend.repository.custom.NoteRepositoryCustom;

public class NoteRepositoryImpl implements NoteRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Note> findByNotebookId(Long id) {
		
		String sql = "from Note e where e.notebook is not null and e.notebook.id = :id";
		Query query = entityManager.createQuery(sql, Note.class);
		query.setParameter("id", id);
		
		return query.getResultList();
	}

}
