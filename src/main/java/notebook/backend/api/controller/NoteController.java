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
import notebook.backend.model.dto.NoteDTO;
import notebook.backend.service.NoteService;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/note")
public class NoteController {

	@Autowired
	NoteService noteService;
	
	@GetMapping("/{id}")
	public ResponseEntity<NoteDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(noteService.findDTOById(id));
	}
	
	@GetMapping("/notebook/{notebookId}")
	public ResponseEntity<List<NoteDTO>> findByNotebookId(@PathVariable Long notebookId) {
		return ResponseEntity.ok(noteService.findByNotebookId(notebookId));
	}
	
	@PostMapping
	public ResponseEntity<MessageResponse> create(@RequestBody @Valid NoteDTO noteDTO) {
		noteService.create(noteDTO);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.CREATE, EntityName.NOTE));
	}
	
	@PutMapping
	public ResponseEntity<MessageResponse> update(@RequestBody @Valid NoteDTO noteDTO) {
		noteService.update(noteDTO);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.UPDATE, EntityName.NOTE));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteById(@PathVariable Long id) {
		noteService.deleteById(id);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.DELETE, EntityName.NOTE));
	}
	
}
