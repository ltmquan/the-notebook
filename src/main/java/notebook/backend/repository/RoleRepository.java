package notebook.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import notebook.backend.model.Role;
import notebook.backend.repository.custom.RoleRepositoryCustom;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {

}
