package notebook.backend.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import notebook.backend.api.response.MessageResponse;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.constants.ResponseStatus;
import notebook.backend.model.dto.FlashcardDTO;
import notebook.backend.service.FlashcardService;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/flashcard")
public class FlashcardController {

	@Autowired
	FlashcardService flashcardService;
	
	@GetMapping("/{id}")
	public ResponseEntity<FlashcardDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(flashcardService.findDTOById(id));
	}
	
	@GetMapping("/note/{noteId}")
	public ResponseEntity<List<FlashcardDTO>> findByNoteId(@PathVariable Long noteId) {
		return ResponseEntity.ok(flashcardService.findByNoteId(noteId));
	}
	
	@PostMapping
	public ResponseEntity<MessageResponse> create(@RequestBody @Valid FlashcardDTO flashcardDTO) {
		flashcardService.create(flashcardDTO);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.CREATE, EntityName.FLASHCARD));
	}
	
	@PutMapping
	public ResponseEntity<MessageResponse> update(@RequestBody @Valid FlashcardDTO flashcardDTO) {
		flashcardService.update(flashcardDTO);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.UPDATE, EntityName.FLASHCARD));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteById(@PathVariable Long id) {
		flashcardService.deleteById(id);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.DELETE, EntityName.FLASHCARD));
	}
	
}
