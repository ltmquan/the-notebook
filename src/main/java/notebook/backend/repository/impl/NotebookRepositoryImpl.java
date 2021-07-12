package notebook.backend.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import notebook.backend.model.Notebook;
import notebook.backend.repository.custom.NotebookRepositoryCustom;

public class NotebookRepositoryImpl implements NotebookRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Notebook> findByUserId(Long id) {
		
		String sql = "from Notebook e where e.user is not null and e.user.id = :id";
		Query query = entityManager.createQuery(sql, Notebook.class);
		query.setParameter("id", id);
		
		return query.getResultList();
	}

}
