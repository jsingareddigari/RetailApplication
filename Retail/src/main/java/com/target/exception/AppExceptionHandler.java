package com.target.exception;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionHandler {

	private static final Logger LOGGER = Logger.getLogger(AppExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public @ResponseBody ErrorResponse handleNotFound(Exception ex) {
		LOGGER.info("ExceptionHandlerAdvice+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOGGER.error("<Exception>", ex);
		ErrorResponse advice = new ErrorResponse();
		if (ex instanceof AppException) {
			advice.setErrorMessage(/*"<Business Exception>" + */ex.getMessage());
		} else {
			advice.setErrorMessage(/*"<Runtime Exception>"+ */ex.toString());			// ex.printStackTrace();/*.substring(0,88)*/
		}
		return advice;
	}

}