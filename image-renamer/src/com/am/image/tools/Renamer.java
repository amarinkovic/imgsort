package com.am.image.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Renamer {

	private static final DecimalFormat df = new DecimalFormat("#####");
	static {
		df.setMinimumIntegerDigits(5);
	}

	public static void main(String[] args) throws IOException {

		final String folderPath = args[0];
		final String baseName = args[1];
		int counter = 0;

		File folder = new File(folderPath);

		if (folder == null || !folder.isDirectory()) {
			throw new IllegalArgumentException("Path provided is invalid!");
		}

		List<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		Collections.sort(files, new CreationDateFileComparator());

		for (File file : files) {
			if (file.getName().toLowerCase(Locale.getDefault()).endsWith(".jpg")) {
				Path p = FileSystems.getDefault().getPath(file.getParent(), file.getName());
				Files.move(p, p.resolveSibling(baseName + df.format(++counter) + ".jpg"));
			}
		}

		System.out.println("[+] Done renaming " + counter + " files.");
	}
}