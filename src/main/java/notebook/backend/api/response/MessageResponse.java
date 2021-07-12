package notebook.backend.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

	private String status;
	
	private String action;
	
	private String entityName;
	
	private String message;

	public MessageResponse(String status, String action, String entityName) {
		this.status = status;
		this.action = action;
		this.entityName = entityName;
	}

	public MessageResponse(String message) {
		this.message = message;
	}
}
