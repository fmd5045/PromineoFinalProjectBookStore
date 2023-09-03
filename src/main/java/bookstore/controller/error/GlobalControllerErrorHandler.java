package bookstore.controller.error;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerErrorHandler {

	private enum LogStatus {
		STACK_TRACE, MESSAGE_ONLY
	}

	// creating getters and setters
	@Data
	private class ExceptionMessage {
		private String message;
		private String statusReason;
		private int statusCode;
		private String timeStamp;
		private String uri;
	}
	
	@ExceptionHandler(IllegalStateException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ExceptionMessage handleIllegalStateException(IllegalStateException exception, WebRequest webRequest) {
		return buildExceptionMessage(exception, HttpStatus.BAD_REQUEST, webRequest, LogStatus.MESSAGE_ONLY);
		}
	
	
	@ExceptionHandler(UnsupportedOperationException.class)
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	public ExceptionMessage hadleUnsupportedOPerationException(UnsupportedOperationException exception,
			WebRequest webRequest) {
		return buildExceptionMessage(exception, HttpStatus.METHOD_NOT_ALLOWED, webRequest, LogStatus.MESSAGE_ONLY);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionMessage handleException(Exception exception, WebRequest webRequest) {
		return buildExceptionMessage(exception, HttpStatus.INTERNAL_SERVER_ERROR, webRequest, LogStatus.STACK_TRACE);

	}

	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionMessage handleNoSuchElementException(NoSuchElementException exception, WebRequest webRequest) {
		return buildExceptionMessage(exception, HttpStatus.NOT_FOUND, webRequest, LogStatus.MESSAGE_ONLY);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ExceptionMessage handleDuplicateKeyException(DuplicateKeyException exception, WebRequest webRequest) {
		return buildExceptionMessage(exception, HttpStatus.CONFLICT, webRequest, LogStatus.MESSAGE_ONLY);
	}

	private ExceptionMessage buildExceptionMessage(Exception exception, HttpStatus status, WebRequest webRequest,
			LogStatus logStatus) {

		String message = exception.toString();
		String statusReason = status.getReasonPhrase();
		int statusCode = status.value();
		String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
		String uri = null;

		if (webRequest instanceof ServletWebRequest swr) {
			uri = swr.getRequest().getRequestURI();
		}

		if (logStatus == LogStatus.MESSAGE_ONLY) {
			log.error("Exception: {})", exception.toString());
		} else {
			log.error("Exception: ", exception);
		}

		ExceptionMessage exceptionMessage = new ExceptionMessage();

		exceptionMessage.setMessage(message);
		exceptionMessage.setStatusCode(statusCode);
		exceptionMessage.setStatusReason(statusReason);
		exceptionMessage.setTimeStamp(timeStamp);
		exceptionMessage.setUri(uri);

		return exceptionMessage;
	}
}
