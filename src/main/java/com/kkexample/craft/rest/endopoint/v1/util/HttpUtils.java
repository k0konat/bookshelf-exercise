package com.kkexample.craft.rest.endopoint.v1.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
	
// TODO: Auto-generated Javadoc
/**
 * The Class HttpUtils.
 *
 * @author k0konat.
 */
public class HttpUtils {
    
    /** The Constant logger. */
    static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * Instantiates a new http utils.
     */
    private HttpUtils() {
    }

    /**
     * Gets the full relative url from context path with query string.
     *
     * @param request the request
     * @return a String containing full url
     */
    public static String getUrlFromContextPath(HttpServletRequest request) {
        String pathInfo = request.getPathInfo() == null ? "" : request.getPathInfo();
        String requestURL = request.getContextPath() + request.getServletPath() +  pathInfo;
        String queryString = request.getQueryString();

        if (Strings.isNullOrEmpty(queryString)) {
            return requestURL;
        } else {
            return requestURL + '?' + queryString;
        }
    }

}
