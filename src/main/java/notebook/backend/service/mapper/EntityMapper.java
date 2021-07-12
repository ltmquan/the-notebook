package notebook.backend.service.mapper;

import java.util.List;

public interface EntityMapper<Entity, DTO> {
	
	Entity toEntity(DTO dto);
	
	DTO toDTO(Entity entity);
	
	List<Entity> toEntity(List<DTO> dto);
	
	List<DTO> toDTO(List<Entity> entity);
	
}
