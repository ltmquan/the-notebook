package notebook.backend.service;

import java.util.List;

import notebook.backend.model.Note;
import notebook.backend.model.dto.NoteDTO;

public interface NoteService {
	Note findById(Long id);
	
	NoteDTO findDTOById(Long id);
	
	List<NoteDTO> findByNotebookId(Long id);
	
	void create(NoteDTO noteDTO);
	
	void update(NoteDTO noteDTO);
	
	void deleteById(Long id);

}
