package notebook.backend.service;

import java.util.List;

import notebook.backend.model.Notebook;
import notebook.backend.model.dto.NotebookDTO;

public interface NotebookService {
	Notebook findById(Long id);
	
	NotebookDTO findDTOById(Long id);
	
	List<NotebookDTO> findByCurrentUser();
	
	void create(NotebookDTO notebookDTO);
	
	void update(NotebookDTO notebookDTO);
	
	void deleteById(Long id);
	
}
