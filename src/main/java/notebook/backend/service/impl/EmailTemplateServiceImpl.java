package notebook.backend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.messages.Messages;
import notebook.backend.model.EmailTemplate;
import notebook.backend.repository.EmailTemplateRepository;
import notebook.backend.service.EmailTemplateService;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {
	
	@Autowired
	EmailTemplateRepository emailTemplateRepository;

	@Override
	public EmailTemplate findById(Long id) {
		Optional<EmailTemplate> emailTemplateOptional = emailTemplateRepository.findById(id);
		if (!emailTemplateOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST, 
					ApiActions.RETRIEVE, EntityName.EMAIL_TEMPLATE);
		}
		return emailTemplateOptional.get();
	}

}
