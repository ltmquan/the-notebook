package notebook.backend.api.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7331897551145015147L;
	
	private String message;
	
	private String action;
	
	private String entityName;

	public BadRequestException(String message, String action, String entityName) {
		super();
		this.message = message;
		this.action = action;
		this.entityName = entityName;
	}
}
