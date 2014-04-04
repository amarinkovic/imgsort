package com.am.image.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.util.Comparator;

/**
 * Compares two files by their creation date.
 * 
 * @author amarinkovic
 *
 */
public class CreationDateFileComparator implements Comparator<File> {

	@Override
	public int compare(File f1, File f2) {
		try {
			Path p1 = FileSystems.getDefault().getPath(f1.getParent(), f1.getName());
			Path p2 = FileSystems.getDefault().getPath(f2.getParent(), f2.getName());

			DosFileAttributes attrs1 = Files.readAttributes(p1, DosFileAttributes.class);
			DosFileAttributes attrs2 = Files.readAttributes(p2, DosFileAttributes.class);

			return attrs1.creationTime().compareTo(attrs2.creationTime());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
}