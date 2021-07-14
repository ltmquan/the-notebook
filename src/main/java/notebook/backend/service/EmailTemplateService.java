package notebook.backend.service;

import notebook.backend.model.EmailTemplate;

public interface EmailTemplateService {

	EmailTemplate findById(Long id);
}
