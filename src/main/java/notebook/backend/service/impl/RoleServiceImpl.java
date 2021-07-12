package notebook.backend.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.messages.Messages;
import notebook.backend.model.Role;
import notebook.backend.repository.RoleRepository;
import notebook.backend.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Role findById(Long id) {
		Optional<Role> roleOptional = roleRepository.findById(id);
		if (roleOptional.isEmpty()) {
			throw new BadRequestException(
					Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST,
					ApiActions.RETRIEVE,
					EntityName.ROLE);
		}
		return roleOptional.get();
	}

}
