package notebook.backend.repository.custom;

import java.util.List;

import notebook.backend.model.Notebook;

public interface NotebookRepositoryCustom {
	
	List<Notebook> findByUserId(Long id);
	
}
