package notebook.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import notebook.backend.model.Notebook;
import notebook.backend.repository.custom.NotebookRepositoryCustom;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long>, NotebookRepositoryCustom {

}
