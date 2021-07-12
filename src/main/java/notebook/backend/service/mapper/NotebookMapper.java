package notebook.backend.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import notebook.backend.model.Notebook;
import notebook.backend.model.dto.NotebookDTO;

@Mapper(componentModel="spring")
public interface NotebookMapper extends EntityMapper<Notebook, NotebookDTO>{
	@Override
	Notebook toEntity(NotebookDTO notebookDTO);
	
	@Override
	@Mappings({
		@Mapping(source="user.id", target="userId"),
		@Mapping(source="user.name", target="userName")
	})
	NotebookDTO toDTO(Notebook notebook);
	
	default Notebook fromId(Long id) {
		if (id == null) {
			return null;
		}
		
		Notebook notebook = new Notebook();
		notebook.setId(id);
		
		return notebook;
	}
	
}
