package notebook.backend.repository.custom;

import java.util.List;

import notebook.backend.model.Note;

public interface NoteRepositoryCustom {
	
	List<Note> findByNotebookId(Long id);
}
