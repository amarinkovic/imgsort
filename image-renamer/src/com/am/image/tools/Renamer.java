package com.am.image.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.function.Function;

import com.drew.imaging.ImageProcessingException;

public class Renamer {

	private static final DecimalFormat df = new DecimalFormat("####");
	private static int counter = 0;

	static {
		df.setMinimumIntegerDigits(5);
	}

	public static void main(String[] args) throws IOException, ImageProcessingException {
		long start = System.currentTimeMillis();

		String input = args[0];
		String target = args[1];

		if (args.length < 2 || input == null || input.trim().isEmpty() || target == null || target.trim().isEmpty()) {
			throw new IllegalArgumentException("Source and target folder paths must be valid!");
		}

		String namePrefix = (args.length < 3 || args[2] == null || args[2].isEmpty()) ? "photo" : args[2];
		Path targetDir = new File(target).toPath();
		
		DecimalFormat df = new DecimalFormat("####");
		df.setMinimumIntegerDigits(4);

		Function<Path, Date> p2d = p -> Photos.getDateTaken(p.toFile());

		Files.list(new File(input).toPath())
			.filter(p -> ("" + p.getFileName()).toLowerCase().endsWith("jpg"))
				.sorted((p1, p2) -> p2d.apply(p1).compareTo(p2d.apply(p2)))
					.forEach(p -> {
						System.out.println(Dates.toString(p.toFile().lastModified()) + "\t" + p.getFileName());
					    try {
							 Files.copy(p, targetDir.resolve(namePrefix + "-" + df.format(++counter) + ".jpg"));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
		
		System.out.println("\n[+] Done in " + Dates.diff(System.currentTimeMillis(), start));
	}
}