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
import notebook.backend.model.User;
import notebook.backend.model.dto.FlashcardDTO;
import notebook.backend.repository.FlashcardRepository;
import notebook.backend.service.FlashcardService;
import notebook.backend.service.UserService;
import notebook.backend.service.mapper.FlashcardMapper;
import notebook.backend.util.UserUtil;

@Service
public class FlashcardServiceImpl implements FlashcardService {

	@Autowired
	FlashcardRepository flashcardRepository;

	@Autowired
	UserService userService;

	@Autowired
	FlashcardMapper flashcardMapper;

	@Override
	public Flashcard findById(Long id) {
		Optional<Flashcard> flashcardOptional = flashcardRepository.findById(id);
		if (!flashcardOptional.isPresent()) {
			throw new BadRequestException(Messages.ERROR_REQUESTED_DATA_DOES_NOT_EXIST, ApiActions.RETRIEVE,
					EntityName.FLASHCARD);
		}

		Flashcard flashcard = flashcardOptional.get();

		if (!UserUtil.userIdIsConsistent(flashcard.getUser().getId())) {
			throw new BadRequestException(Messages.ERROR_INVALID_REQUEST, ApiActions.RETRIEVE, EntityName.NOTEBOOK);
		}

		return flashcard;
	}

	@Override
	public FlashcardDTO findDTOById(Long id) {
		return flashcardMapper.toDTO(this.findById(id));
	}

	@Override
	public List<FlashcardDTO> findByCurrentUser() {
		Long currentId = UserUtil.getCurrentUserId();

		List<Flashcard> notebookList = flashcardRepository.findByUserId(currentId);
		return flashcardMapper.toDTO(notebookList);
	}

	@Override
	public void create(FlashcardDTO flashcardDTO) {
		if (flashcardDTO.getId() != null) {
			throw new BadRequestException(Messages.ERROR_INVALID_REQUEST_DATA, ApiActions.CREATE, EntityName.FLASHCARD);
		}

		User user = userService.findById(UserUtil.getCurrentUserId());

		Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
		flashcard.setUser(user);

		flashcardRepository.save(flashcard);
	}

	@Override
	public void update(FlashcardDTO flashcardDTO) {
		if (flashcardDTO.getId() == null || !flashcardRepository.existsById(flashcardDTO.getId())) {
			throw new BadRequestException(Messages.ERROR_INVALID_REQUEST_DATA, ApiActions.UPDATE, EntityName.FLASHCARD);
		}

		User user = userService.findById(UserUtil.getCurrentUserId());

		Flashcard flashcard = flashcardMapper.toEntity(flashcardDTO);
		flashcard.setUser(user);

		flashcardRepository.save(flashcard);
	}

	@Override
	public void deleteById(Long id) {
		Optional<Flashcard> flashcardOptional = flashcardRepository.findById(id);
		if (!flashcardOptional.isPresent()) {
			throw new BadRequestException(Messages.ERROR_INVALID_REQUEST_DATA, ApiActions.DELETE, EntityName.FLASHCARD);
		}

		Flashcard flashcard = flashcardOptional.get();
		
		if (!UserUtil.userIdIsConsistent(flashcard.getUser().getId())) {
			throw new BadRequestException(
					Messages.ERROR_INVALID_REQUEST, 
					ApiActions.DELETE, EntityName.FLASHCARD);
		}
		
		flashcardRepository.deleteById(id);
	}

}
