package notebook.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.messages.Messages;
import notebook.backend.model.Notebook;
import notebook.backend.model.User;
import notebook.backend.model.dto.NotebookDTO;
import notebook.backend.repository.NotebookRepository;
import notebook.backend.service.NotebookService;
import notebook.backend.service.UserService;
import notebook.backend.service.mapper.NotebookMapper;
import notebook.backend.util.UserUtil;

@Service
public class NotebookServiceImpl implements NotebookService {

	@Autowired
	NotebookRepository notebookRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NotebookMapper notebookMapper;

	@Override
	public Notebook findById(Long id) {
		Optional<Notebook> notebookOptional = notebookRepository.findById(id);
		if (!notebookOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST,
					ApiActions.RETRIEVE, EntityName.NOTEBOOK);
		}
		
		Notebook notebook = notebookOptional.get();
		
		if (!UserUtil.userIdIsConsistent(notebook.getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST, 
					ApiActions.RETRIEVE, EntityName.NOTEBOOK);
		}
		
		return notebook;
	}
	
	@Override
	public NotebookDTO findDTOById(Long id) {
		return notebookMapper.toDTO(this.findById(id));
	}

	@Override
	public List<NotebookDTO> findByCurrentUser() {
		Long currentId = UserUtil.getCurrentUserId();
		
		List<Notebook> notebookList = notebookRepository.findByUserId(currentId); 
		return notebookMapper.toDTO(notebookList);
	}

	@Override
	public void create(NotebookDTO notebookDTO) {
		if (notebookDTO.getId() != null) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.CREATE, EntityName.NOTEBOOK);
		}
		
		User user = userService.findById(UserUtil.getCurrentUserId());
		
		Notebook notebook = notebookMapper.toEntity(notebookDTO);
		notebook.setUser(user);
		
		notebookRepository.save(notebook);
	}

	@Override
	public void update(NotebookDTO notebookDTO) {
		if (notebookDTO.getId() == null || !notebookRepository.existsById(notebookDTO.getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.UPDATE, EntityName.NOTEBOOK);
		}
		
		User user = userService.findById(UserUtil.getCurrentUserId());
		
		Notebook notebook = notebookMapper.toEntity(notebookDTO);
		notebook.setUser(user);
		
		notebookRepository.save(notebook);
	}

	@Override
	public void deleteById(Long id) {
		Optional<Notebook> notebookOptional = notebookRepository.findById(id);
		if (!notebookOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.DELETE, EntityName.NOTEBOOK);
		}
		
		Notebook notebook = notebookOptional.get();
		
		if (!UserUtil.userIdIsConsistent(notebook.getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST, 
					ApiActions.DELETE, EntityName.NOTEBOOK);
		}
		
		notebookRepository.deleteById(id);
	}
}
