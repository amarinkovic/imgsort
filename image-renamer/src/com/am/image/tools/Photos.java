package com.am.image.tools;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;

/**
 * Various convenience methods for working with photos
 * 
 * @author amarinkovic
 *
 */
public class Photos {

	public static Date getDateTaken(File file) {
		Date taken = null;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			ExifIFD0Directory dir = metadata.getDirectory(ExifIFD0Directory.class);
			taken = dir.getDate(ExifIFD0Directory.TAG_DATETIME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taken;
	}
	
	public static Date getDateModified(File file) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(file.lastModified());
		return cal.getTime();
	}
	
	public static String dateDiff(File file) {
		return Dates.diff(getDateTaken(file), getDateModified(file));
	}

	public static Map<String, String> getMetadata(File file) {
		Map<String, String> data = new HashMap<String, String>();
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			metadata.getDirectories().forEach(d->d.getTags().forEach(t->data.put(t.getTagName(), d.getDescription(t.getTagType()))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
