package notebook.backend.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import notebook.backend.repository.custom.RoleRepositoryCustom;

public class RoleRepositoryImpl implements RoleRepositoryCustom {
	@PersistenceContext
	EntityManager entityManager;
}
