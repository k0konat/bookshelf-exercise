package com.intuit.craft.rest.endopoint.v1.controller.handler;

import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Kishore Konate
 */
public class ErrorInfo {
    public final String url;
    public final String ex;
    public final HttpStatus httpStatus;
    public final String date;
    public final String error;

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
