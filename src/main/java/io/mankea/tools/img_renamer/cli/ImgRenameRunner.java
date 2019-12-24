package io.mankea.tools.img_renamer.cli;

import io.mankea.tools.img_renamer.model.ImgFile;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.Callable;

import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class ImgRenameRunner implements Callable<Integer> {

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
        return paths[0];
    }

    String getTargetPath() {
        return paths[1];
    }

    @Override
    public Integer call() throws Exception {
        processFiles(this);
        return 0;
    }

    private void processFiles(ImgRenameRunner conf) throws IOException {

        System.out.println(" >> Source path: " + conf.getSourcePath());
        System.out.println(" >> Destination: " + conf.getTargetPath() + "\n\n");

        Path targetDir = new File(conf.getTargetPath()).toPath();

        List<ImgFile> fs = Files.walk(new File(conf.getSourcePath()).toPath())
                .filter(Files::isRegularFile)
                .filter(p -> p.getFileName().toString().toLowerCase().endsWith("jpg"))
                .map(Path::toFile)
                .map(ImgFile::new)
                .sorted(comparing(ImgFile::getCreatedAt))
                .peek(this::logImg)
                .collect(toList());

        boolean dryRun = true; // c.dryRun

        if (!dryRun) {
            System.out.println(format(" >> Staring rename of %s images", fs.size()));
            counter = conf.offset;
            fs.stream().forEach(i -> {
                try {
                    Files.copy(i.getFile().toPath(), targetDir.resolve(conf.prefix + "_" + df.format(counter++) + ".jpg"));
                } catch (Exception e) {
                    System.out.println(format("Error processing: {} - {}", i.getFile().getAbsolutePath(), e.getMessage()));
                }
            });
        }
    }

    private void logImg(ImgFile i) {
        System.out.println(
                sdf.format(i.getCreatedAt())
                        + " | diff: " + i.daysDiff() + (i.daysDiff() < 100 ? "\t" : "") + " | "
                        + i.getFile().getAbsolutePath()
        );
    }
}
