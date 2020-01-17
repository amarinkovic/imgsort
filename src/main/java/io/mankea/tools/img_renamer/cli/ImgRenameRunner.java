package io.mankea.tools.img_renamer.cli;

import io.mankea.tools.img_renamer.model.ImgFile;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class ImgRenameRunner implements Callable<Integer> {

    private static final String DEFAULT_TARGET_PATH = File.separator + "tmp" + File.separator + "out";

    private static final DecimalFormat df = new DecimalFormat("####");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static int counter = 0;

    static {
        df.setMinimumIntegerDigits(4);
    }

    @CommandLine.Option(names = {"-d", "--dry-run"}, description = "Dry run, does not process files, just report")
    boolean dryRun;

    @CommandLine.Option(names = {"-o", "--offset"}, defaultValue = "1", description = "Counter offset, index for the first element")
    int offset;

    @CommandLine.Option(names = {"-p", "--prefix"}, defaultValue = "photo", description = "File naming prefix")
    String prefix;

    @CommandLine.Parameters(paramLabel = "folders", description = "Source and target folder paths")
    String[] paths;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Display this help message")
    private boolean helpRequested = false;

    String getSourcePath() {
        return ofNullable(paths != null ? paths[0] : null).orElseThrow(() -> new IllegalArgumentException("No source path provided"));
    }

    String getTargetPath() {
        return paths != null && paths.length > 1 ? paths[1] : DEFAULT_TARGET_PATH;
    }

    @Override
    public Integer call() throws Exception {
        processFiles(this);
        return 0;
    }

    private void processFiles(ImgRenameRunner conf) throws IOException {

        Instant start = Instant.now();

        System.out.println(colored(" >> Source path: ", 245) + conf.getSourcePath());
        System.out.println(colored(" >> Destination: ", 245) + conf.getTargetPath() + "\n");

        if(dryRun) {
            System.out.println(" >> dry run\n");
        }

        File targetFolder = new File(conf.getTargetPath());
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        Path targetDir = targetFolder.toPath();

        System.out.println("Scanning files:\n");
        List<ImgFile> fs = Files.walk(new File(conf.getSourcePath()).toPath())
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().toLowerCase().endsWith("jpg"))
                .map(Path::toFile)
                .map(ImgFile::new)
                .sorted(comparing(ImgFile::getCreatedAt))
                .peek(this::logImg)
                .collect(toList());

        if (!dryRun) {
            System.out.println(format("\n  >> Starting rename of %s images", fs.size()));
            counter = conf.offset;
            fs.stream().forEach(i -> {
                try {
                    Files.copy(i.getFile().toPath(), targetDir.resolve(conf.prefix + "_" + df.format(counter++) + ".jpg"));
                } catch (Exception e) {
                    System.out.println(format("Error processing: {} - {}", i.getFile().getAbsolutePath(), e.getMessage()));
                }
            });
        }

        System.out.println("\nProcessing done in: " + Duration.between(start, Instant.now()).toMillis() + " ms");

    }

    private void logImg(ImgFile i) {
        System.out.println(
                sdf.format(i.getCreatedAt())
                        + " | diff: " + i.daysDiff() + (i.daysDiff() < 100 ? "\t" : "") + " | "
                        + i.getFile().getAbsolutePath()
        );
    }

    private String colored(String input, int color) {
        return CommandLine.Help.Ansi.AUTO.string("@|fg(" + color + ") " + input + "|@");
    }
}
