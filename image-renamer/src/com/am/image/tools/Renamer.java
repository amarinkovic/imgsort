package com.am.image.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributes;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

		Comparator<File> cmp = new Comparator<File>() {
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
		};

		List<File> files = new ArrayList<File>(Arrays.asList(folder.listFiles()));
		Collections.sort(files, cmp);

		for (File file : files) {
			String oldFileName = file.getName();
			if (oldFileName != null && oldFileName.toLowerCase(Locale.getDefault()).endsWith(".jpg")) {
				Path p = FileSystems.getDefault().getPath(file.getParent(), file.getName());
				Files.move(p, p.resolveSibling(baseName + df.format(++counter) + "-" + oldFileName + ".jpg"));
				System.out.println(oldFileName + "\t -> \t" + baseName + df.format(counter));
			}
		}

		System.out.println("[+] Done.");
	}
}