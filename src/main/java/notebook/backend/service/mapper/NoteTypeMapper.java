package notebook.backend.service.mapper;

import org.mapstruct.Mapper;

import notebook.backend.model.NoteType;
import notebook.backend.model.dto.NoteTypeDTO;

@Mapper(componentModel="spring")
public interface NoteTypeMapper extends EntityMapper<NoteType, NoteTypeDTO>{
	@Override
	NoteType toEntity(NoteTypeDTO noteTypeDTO);
	
	@Override
	NoteTypeDTO toDTO(NoteType noteType);
	
	default NoteType fromId(Long id) {
		if (id == null) {
			return null;
		}
		
		NoteType noteType = new NoteType();
		noteType.setId(id);
		
		return noteType;
	}
}
