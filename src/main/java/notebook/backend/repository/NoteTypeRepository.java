package notebook.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import notebook.backend.model.NoteType;
import notebook.backend.repository.custom.NoteTypeRepositoryCustom;

@Repository
public interface NoteTypeRepository extends JpaRepository<NoteType, Long>, NoteTypeRepositoryCustom {

}
