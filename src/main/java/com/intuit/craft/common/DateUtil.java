package com.intuit.craft.common;

import java.time.LocalDateTime;

/**
 * The Class DateUtil.
 *
 * @author k0konat
 */

/**
 * This class is used for Date related functions.
 * 
 * @author k0konat.
 */

public class DateUtil {
	 
 	/**
 	 * Adds the days.
 	 *
 	 * @param dateTimeNow the date time now
 	 * @param days the days
 	 * @return the local date time
 	 */
 	public static LocalDateTime addDays(LocalDateTime dateTimeNow, int days) {          
	        return dateTimeNow.plusDays(days);
	 }

}
