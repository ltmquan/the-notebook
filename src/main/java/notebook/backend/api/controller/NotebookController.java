package notebook.backend.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import notebook.backend.model.dto.NotebookDTO;
import notebook.backend.service.NotebookService;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/notebook")
public class NotebookController {
	
	@Autowired
	NotebookService notebookService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<NotebookDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(notebookService.findDTOById(id));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<NotebookDTO>> findByCurrentUser() {
		return ResponseEntity.ok(notebookService.findByCurrentUser());
	}
	
	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MessageResponse> create(@RequestBody @Valid NotebookDTO notebookDTO) {
		notebookService.create(notebookDTO);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.CREATE, EntityName.NOTEBOOK));
	}
	
	@PutMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MessageResponse> update(@RequestBody @Valid NotebookDTO notebookDTO) {
		notebookService.update(notebookDTO);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.UPDATE, EntityName.NOTEBOOK)); 
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<MessageResponse> deleteById(@PathVariable Long id) {
		notebookService.deleteById(id);
		return ResponseEntity.ok(
				new MessageResponse(ResponseStatus.SUCCESS, ApiActions.DELETE, EntityName.NOTEBOOK));
	}
}
