package notebook.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import notebook.backend.model.EmailTemplate;
import notebook.backend.repository.custom.EmailTemplateRepositoryCustom;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long>, EmailTemplateRepositoryCustom {
	

}
