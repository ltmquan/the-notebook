package notebook.backend.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EmailTemplateRepositoryImpl {
	@PersistenceContext
	EntityManager entityManager;
}
