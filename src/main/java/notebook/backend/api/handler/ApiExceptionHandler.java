package notebook.backend.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.api.response.MessageResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public MessageResponse handleBadRequestException(BadRequestException exception) {
		MessageResponse response = new MessageResponse(
				notebook.backend.constants.ResponseStatus.ERROR,
				exception.getAction(),
				exception.getEntityName(),
				exception.getMessage());
		
		return response;
	}
	
}

