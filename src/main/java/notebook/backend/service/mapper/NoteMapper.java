package notebook.backend.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import notebook.backend.model.Note;
import notebook.backend.model.dto.NoteDTO;

@Mapper(componentModel="spring")
public interface NoteMapper extends EntityMapper<Note, NoteDTO>{
	@Override
	Note toEntity(NoteDTO noteDTO);
	
	@Override
	@Mappings({
		@Mapping(source="type.id", target="typeId"),
		@Mapping(source="type.name", target="typeName"),
		@Mapping(source="notebook.id", target="notebookId"),
		@Mapping(source="notebook.name", target="notebookName"),
	})
	NoteDTO toDTO(Note note);
	
	default Note fromId(Long id) {
		if (id == null) {
			return null;
		}
		
		Note note = new Note();
		note.setId(id);
		
		return note;
	}

}
