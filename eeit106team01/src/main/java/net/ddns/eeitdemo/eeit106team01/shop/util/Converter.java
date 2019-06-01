package net.ddns.eeitdemo.eeit106team01.shop.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import sun.misc.BASE64Decoder;

public class Converter {

	public static List<?> convertObjectToList(Object obj) {
		List<?> list = new ArrayList<>();
		if (obj.getClass().isArray()) {
			list = Arrays.asList((Object[]) obj);
		} else if (obj instanceof Collection) {
			list = new ArrayList<>((Collection<?>) obj);
		}
		return list;
	}

	public static SerialBlob decodeToImage(String imageString) throws SerialException, SQLException, IOException {
	    BASE64Decoder decoder = new BASE64Decoder();
	    byte[] imageByte = decoder.decodeBuffer(imageString);    
	    return new SerialBlob(imageByte);    
	}

}
