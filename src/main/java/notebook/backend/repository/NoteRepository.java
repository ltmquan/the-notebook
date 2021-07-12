package notebook.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import notebook.backend.model.Note;
import notebook.backend.repository.custom.NoteRepositoryCustom;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long>, NoteRepositoryCustom {

}
