package com.intuit.craft.common;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	 public static LocalDateTime addDays(LocalDateTime dateTimeNow, int days) {          
	        return dateTimeNow.plusDays(days);
	 }

}
