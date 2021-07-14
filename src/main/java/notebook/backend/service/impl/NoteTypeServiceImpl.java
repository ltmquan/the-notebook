package notebook.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.messages.Messages;
import notebook.backend.model.NoteType;
import notebook.backend.model.User;
import notebook.backend.model.dto.NoteTypeDTO;
import notebook.backend.repository.NoteTypeRepository;
import notebook.backend.service.NoteTypeService;
import notebook.backend.service.UserService;
import notebook.backend.service.mapper.NoteTypeMapper;
import notebook.backend.util.UserUtil;

@Service
public class NoteTypeServiceImpl implements NoteTypeService {

	@Autowired
	NoteTypeRepository noteTypeRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	NoteTypeMapper noteTypeMapper;
	
	@Override
	public NoteType findById(Long id) {
		Optional<NoteType> noteTypeOptional = noteTypeRepository.findById(id);
		if (!noteTypeOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST, 
					ApiActions.RETRIEVE, EntityName.NOTE_TYPE);
		}
		
		NoteType noteType = noteTypeOptional.get();
		if (!UserUtil.userIdIsConsistent(noteType.getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST, 
					ApiActions.RETRIEVE, EntityName.NOTE_TYPE);
		}
		
		return noteType;
	}

	@Override
	public NoteTypeDTO findDTOById(Long id) {
		return noteTypeMapper.toDTO(this.findById(id));
	}
	
	@Override
	public List<NoteTypeDTO> findByCurrentUser() {
		Long currentId = UserUtil.getCurrentUserId();
		
		List<NoteType> noteTypeList = noteTypeRepository.findByUserId(currentId);
		return noteTypeMapper.toDTO(noteTypeList);
	}

	@Override
	public void create(NoteTypeDTO noteTypeDTO) {
		if (noteTypeDTO.getId() != null) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA, 
					ApiActions.CREATE, EntityName.NOTE_TYPE);
		}
		
		User user = userService.findById(UserUtil.getCurrentUserId());
		
		NoteType noteType = noteTypeMapper.toEntity(noteTypeDTO);
		noteType.setUser(user);

		noteTypeRepository.save(noteType);
	}

	@Override
	public void update(NoteTypeDTO noteTypeDTO) {
		if (noteTypeDTO.getId() == null || !noteTypeRepository.existsById(noteTypeDTO.getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA, 
					ApiActions.UPDATE, EntityName.NOTE_TYPE);
		}
		
		User user = userService.findById(UserUtil.getCurrentUserId());
		
		NoteType noteType = noteTypeMapper.toEntity(noteTypeDTO);
		noteType.setUser(user);

		noteTypeRepository.save(noteType);
	}

	@Override
	public void deleteById(Long id) {
		Optional<NoteType> noteTypeOptional = noteTypeRepository.findById(id);
		if (!noteTypeOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA, 
					ApiActions.DELETE, EntityName.NOTE_TYPE);
		}
		
		NoteType noteType = noteTypeOptional.get();
		if (!UserUtil.userIdIsConsistent(noteType.getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST, 
					ApiActions.DELETE, EntityName.NOTE_TYPE);
		}
		
		noteTypeRepository.deleteById(id);
	}

}
