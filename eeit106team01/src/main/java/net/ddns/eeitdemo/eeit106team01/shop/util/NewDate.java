package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewDate {

	public Date newCurrentTime() {
		return new Date(System.currentTimeMillis());
	}

	public Date newDate(String dateFormat, String dateString) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.parse(dateString);
	}

}
