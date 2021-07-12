package notebook.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import notebook.backend.api.response.MessageResponse;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.constants.ResponseStatus;
import notebook.backend.model.dto.NoteTypeDTO;
import notebook.backend.service.NoteTypeService;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/note-type")
public class NoteTypeController {

	@Autowired
	NoteTypeService noteTypeService;
	
	@GetMapping("/{id}")
	public ResponseEntity<NoteTypeDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(noteTypeService.findDTOById(id));
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<NoteTypeDTO>> findByUserId() {
		return ResponseEntity.ok(noteTypeService.findByCurrentUser());
	}
	
	@PostMapping
	public ResponseEntity<MessageResponse> create(NoteTypeDTO noteTypeDTO) {
		noteTypeService.create(noteTypeDTO);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.CREATE, EntityName.NOTE_TYPE));
	}
	
	@PutMapping
	public ResponseEntity<MessageResponse> update(NoteTypeDTO noteTypeDTO) {
		noteTypeService.update(noteTypeDTO);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.UPDATE, EntityName.NOTE_TYPE));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> delete(@PathVariable Long id) {
		noteTypeService.deleteById(id);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.DELETE, EntityName.NOTE_TYPE));
	}
}
