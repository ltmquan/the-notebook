package notebook.backend.repository.custom;

import java.util.List;

import notebook.backend.model.NoteType;

public interface NoteTypeRepositoryCustom {

	List<NoteType> findByUserId(Long id);
	
}
