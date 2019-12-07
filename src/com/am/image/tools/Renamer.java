package com.am.image.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.am.image.tools.model.ImgFile;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class Renamer {

	private static final DecimalFormat df = new DecimalFormat("####");
	private static final SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static int counter = 0;

	static {
		df.setMinimumIntegerDigits(4);
	}

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();

		String input = args[0];
		String target = args[1];

		if (args.length < 2 || input == null || input.trim().isEmpty() || target == null || target.trim().isEmpty()) {
			throw new IllegalArgumentException("Source and target folder paths must be valid!");
		}

		String namePrefix = (args.length < 3 || args[2] == null || args[2].isEmpty()) ? "photo" : args[2];
		Path targetDir = new File(target).toPath();
		
		List<ImgFile> fs = Files.list(new File(input).toPath())
			.filter(p -> p.getFileName().toString().toLowerCase().endsWith("jpg"))
			.map(Path::toFile)
			.map(ImgFile::new)
			.sorted(comparing(ImgFile::getCreatedAt))
				.collect(toList());

			fs.stream().forEach(i -> logImg(i));

			boolean dryRun = true;

			if(!dryRun) {
				fs.stream().forEach(i -> {
					try {
						Files.copy(i.getFile().toPath(), targetDir.resolve(namePrefix + "-" + df.format(++counter) + ".jpg"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}

		System.out.println("\n[+] Done in " + (System.currentTimeMillis() - start) + " ms");
	}

	private static void logImg(ImgFile i) {
		System.out.println(
				"[ plain: " +
				(i.getCreatedAtPlain() != null ? sdf.format(Date.from(i.getCreatedAtPlain())) : "\t\t\t\t\t") +
				" | exif: " +
				(i.getCreatedAtExif() != null ? sdf.format(i.getCreatedAtExif()) : "\t\t\t\t\t") +
				" | diff: " +
				i.daysDiff() + (i.daysDiff() < 100 ? "\t" : "") + " ] " +
				i.getName());
	}
}