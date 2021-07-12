package notebook.backend.service;

import java.util.List;

import notebook.backend.model.NoteType;
import notebook.backend.model.dto.NoteTypeDTO;

public interface NoteTypeService {

	NoteType findById(Long id);
	
	NoteTypeDTO findDTOById(Long id);
	
	List<NoteTypeDTO> findByCurrentUser();
	
	void create(NoteTypeDTO noteTypeDTO);
	
	void update(NoteTypeDTO noteTypeDTO);
	
	void deleteById(Long id);
}
