package notebook.backend.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import notebook.backend.model.User;
import notebook.backend.model.dto.UserDTO;

@Mapper(componentModel="spring")
public interface UserMapper extends EntityMapper<User, UserDTO>{
	@Override
	User toEntity(UserDTO userDTO);
	
	@Override
	@Mappings({
		@Mapping(source="role.name", target="roleName")
	})
	UserDTO toDTO(User user);
	
	default User fromId(Long id) {
		if (id == null) {
			return null;
		}
		
		User user = new User();
		user.setId(id);
		
		return user;
	}
	
}
