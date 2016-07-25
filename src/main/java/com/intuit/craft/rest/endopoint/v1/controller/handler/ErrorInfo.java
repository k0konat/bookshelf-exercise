package com.intuit.craft.rest.endopoint.v1.controller.handler;

import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * The Class ErrorInfo.
 *
 * @author k0konat
 */
public class ErrorInfo {
    
    /** The url. */
    public final String url;
    
    /** The ex. */
    public final String ex;
    
    /** The http status. */
    public final HttpStatus httpStatus;
    
    /** The date. */
    public final String date;
    
    /** The error. */
    public final String error;

    /**
     * Instantiates a new error info.
     *
     * @param url the url
     * @param status the status
     * @param error the error
     * @param ex the ex
     */
    public ErrorInfo(String url, HttpStatus status, String error, Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
        this.httpStatus = status;
        this.error = Strings.isNullOrEmpty(error) ? status.getReasonPhrase() : error;
        this.date = DateTimeFormatter.ISO_ZONED_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
    }
}
