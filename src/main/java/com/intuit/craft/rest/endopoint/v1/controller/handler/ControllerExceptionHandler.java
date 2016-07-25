package com.intuit.craft.rest.endopoint.v1.controller.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.intuit.craft.exception.ConflictException;
import com.intuit.craft.exception.ItemNotFoundException;
import com.intuit.craft.exception.OverflowException;
import com.intuit.craft.rest.endopoint.v1.util.HttpUtils;

/**
 * The Class ControllerExceptionHandler.
 *
 * @author k0konat
 */
@ControllerAdvice
public class ControllerExceptionHandler {

/** The Constant logger. */
static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
	 
	/**
	 * Handle item not found request.
	 *
	 * @param req the req
	 * @param ex the ex
	 * @return the error info
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ItemNotFoundException.class)
	@ResponseBody
	ErrorInfo handleItemNotFoundRequest(HttpServletRequest req, Exception ex) {
	    logger.info("book_not_found", ex);
	    return new ErrorInfo(HttpUtils.getUrlFromContextPath(req), HttpStatus.NOT_FOUND, "book_not_found", ex);
	}
	
	
	/**
	 * Handle missed Arguments from the request.
	 *
	 * @param req the req
	 * @param ex the ex
	 * @return the error info
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	ErrorInfo handleIllegalArgumentsRequest(HttpServletRequest req, Exception ex) {
	    logger.info("missing_arguments", ex);
	    return new ErrorInfo(HttpUtils.getUrlFromContextPath(req), HttpStatus.BAD_REQUEST, "missing_arguments", ex);
	}
	
	/**
	 * Handle item not found request.
	 *
	 * @param req the req
	 * @param ex the ex
	 * @return the error info
	 */
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(ConflictException.class)
	@ResponseBody
	ErrorInfo handleConflictFoundRequest(HttpServletRequest req, Exception ex) {
	    logger.info("book_conflict", ex);
	    return new ErrorInfo(HttpUtils.getUrlFromContextPath(req), HttpStatus.CONFLICT, "book_conflict", ex);
	}
	
	/**
	 * Handle insuffiecient storage request.
	 *
	 * @param req the req
	 * @param ex the ex
	 * @return the error info
	 */
	@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
	@ExceptionHandler(OverflowException.class)
	@ResponseBody
	ErrorInfo handleInsuffiecientStorageRequest(HttpServletRequest req, Exception ex) {
	    logger.info("item_storage_full", ex);
	    return new ErrorInfo(HttpUtils.getUrlFromContextPath(req), HttpStatus.INSUFFICIENT_STORAGE, "Bookshelf_is_full", ex);
	}
	/**
	 * Handle exception request.
	 *
	 * @param req the req
	 * @param ex the ex
	 * @return the error info
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidFormatException.class)
	@ResponseBody
	ErrorInfo handleInvalidFormatnRequest(HttpServletRequest req, Exception ex) {
        logger.info("invalid_format", ex);
        return new ErrorInfo(HttpUtils.getUrlFromContextPath(req),
        		HttpStatus.BAD_REQUEST, "invalid_format", ex);
	}
	
	
	/**
	 * Handle exception request.
	 *
	 * @param req the req
	 * @param ex the ex
	 * @return the error info
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	@ResponseBody
	ErrorInfo handleExceptionRequest(HttpServletRequest req, Exception ex) {
        logger.info("unknown_exception", ex);
        return new ErrorInfo(HttpUtils.getUrlFromContextPath(req),
        		HttpStatus.INTERNAL_SERVER_ERROR, "unknown_exception", ex);
	}
	

	
	
	
	

}
