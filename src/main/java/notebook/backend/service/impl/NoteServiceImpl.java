package notebook.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.messages.Messages;
import notebook.backend.model.Note;
import notebook.backend.model.NoteType;
import notebook.backend.model.Notebook;
import notebook.backend.model.dto.NoteDTO;
import notebook.backend.repository.NoteRepository;
import notebook.backend.service.NoteService;
import notebook.backend.service.NoteTypeService;
import notebook.backend.service.NotebookService;
import notebook.backend.service.mapper.NoteMapper;
import notebook.backend.util.UserUtil;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	NotebookService notebookService;
	
	@Autowired
	NoteTypeService noteTypeService;
	
	@Autowired
	NoteMapper noteMapper;

	@Override
	public Note findById(Long id) {
		Optional<Note> noteOptional = noteRepository.findById(id);
		if (!noteOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST,
					ApiActions.RETRIEVE, EntityName.NOTE);
		}
		
		Note note = noteOptional.get();
		
		if (!UserUtil.userIdIsConsistent(note.getNotebook().getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST, 
					ApiActions.RETRIEVE, EntityName.NOTE);
		}
		
		return note;
	}
	
	@Override
	public NoteDTO findDTOById(Long id) {
		return noteMapper.toDTO(this.findById(id));
	}

	@Override
	public List<NoteDTO> findByNotebookId(Long id) {
		// check if consistent with login user through notebook
		Notebook notebook = notebookService.findById(id);
		
		List<Note> noteList = noteRepository.findByNotebookId(notebook.getId());
		return noteMapper.toDTO(noteList);
	}

	@Override
	public void create(NoteDTO noteDTO) {
		if (noteDTO.getId() != null) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.CREATE, EntityName.NOTE);
		}
		
		Notebook notebook = notebookService.findById(noteDTO.getNotebookId());
		
		NoteType noteType = null;
		if (noteDTO.getTypeId() != null) {
			noteType = noteTypeService.findById(noteDTO.getTypeId());
		}
		
		Note note = noteMapper.toEntity(noteDTO);
		note.setNotebook(notebook);
		note.setType(noteType);
		
		noteRepository.save(note);
	}

	@Override
	public void update(NoteDTO noteDTO) {
		if (noteDTO.getId() == null || !noteRepository.existsById(noteDTO.getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.UPDATE, EntityName.NOTE);
		}
		
		Notebook notebook = notebookService.findById(noteDTO.getNotebookId());
		
		NoteType noteType = null;
		if (noteDTO.getTypeId() != null) {
			noteType = noteTypeService.findById(noteDTO.getTypeId());
		}
		
		Note note = noteMapper.toEntity(noteDTO);
		note.setNotebook(notebook);
		note.setType(noteType);
		
		noteRepository.save(note);
	}

	@Override
	public void deleteById(Long id) {
		Optional<Note> noteOptional = noteRepository.findById(id);
		if (!noteOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.DELETE, EntityName.NOTE);
		}
		
		Note note = noteOptional.get();
		if (!UserUtil.userIdIsConsistent(note.getNotebook().getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST, 
					ApiActions.DELETE, EntityName.NOTE);
		}
		
		noteRepository.deleteById(id);
	}
}
