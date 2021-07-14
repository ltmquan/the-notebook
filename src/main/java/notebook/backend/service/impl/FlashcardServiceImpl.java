package notebook.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.messages.Messages;
import notebook.backend.model.Flashcard;
import notebook.backend.model.Note;
import notebook.backend.model.dto.FlashcardDTO;
import notebook.backend.repository.FlashcardRepository;
import notebook.backend.service.FlashcardService;
import notebook.backend.service.NoteService;
import notebook.backend.service.mapper.FlashcardMapper;
import notebook.backend.util.UserUtil;

@Service
public class FlashcardServiceImpl implements FlashcardService {

	@Autowired
	FlashcardRepository flashcardRepository;
	
	@Autowired
	NoteService noteService;
	
	@Autowired
	FlashcardMapper flashcardMapper;

	@Override
	public Flashcard findById(Long id) {
		Optional<Flashcard> flashcardOptional = flashcardRepository.findById(id);
		if (!flashcardOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST,
					ApiActions.RETRIEVE, EntityName.FLASHCARD);
		}
		
		Flashcard flashcard = flashcardOptional.get();
		if (!UserUtil.userIdIsConsistent(flashcard.getNote().getNotebook().getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST,
					ApiActions.RETRIEVE, EntityName.FLASHCARD);
		}
		return flashcard;
	}
	
	@Override
	public FlashcardDTO findDTOById(Long id) {
		return flashcardMapper.toDTO(this.findById(id));
	}

	@Override
	public List<FlashcardDTO> findByNoteId(Long id) {
		// check if consistent with login user through note
		Note note = noteService.findById(id);
		
		List<Flashcard> flashcardList = flashcardRepository.findByNoteId(note.getId());
		return flashcardMapper.toDTO(flashcardList);
	}

	@Override
	public void create(FlashcardDTO flashcardDTO) {
		if (flashcardDTO.getId() != null) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.CREATE, EntityName.FLASHCARD);
		}
		
		Note note = noteService.findById(flashcardDTO.getNoteId());
		
		Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
		flashcard.setNote(note);
		
		flashcardRepository.save(flashcard);
	}

	@Override
	public void update(FlashcardDTO flashcardDTO) {
		if (flashcardDTO.getId() == null || !flashcardRepository.existsById(flashcardDTO.getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.UPDATE, EntityName.FLASHCARD);
		}
		
		Note note = noteService.findById(flashcardDTO.getNoteId());
		
		Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
		flashcard.setNote(note);
		
		flashcardRepository.save(flashcard);
	}

	@Override
	public void deleteById(Long id) {
		Optional<Flashcard> flashcardOptional = flashcardRepository.findById(id);
		if (!flashcardOptional.isPresent()) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST_DATA,
					ApiActions.DELETE, EntityName.FLASHCARD);
		}
		
		Flashcard flashcard = flashcardOptional.get();
		if (!UserUtil.userIdIsConsistent(flashcard.getNote().getNotebook().getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST,
					ApiActions.DELETE, EntityName.FLASHCARD);
		}
		flashcardRepository.deleteById(id);
	}
	
}
