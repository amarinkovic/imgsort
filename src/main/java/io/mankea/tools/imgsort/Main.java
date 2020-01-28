package io.mankea.tools.imgsort;

import io.mankea.tools.imgsort.picocli.CliArgsErrorHandler;
import io.mankea.tools.imgsort.picocli.ImgSortRunner;
import picocli.CommandLine;

public class Main {

    private static final String RAINBOW_BANNER = "\n @|fg(62) I|@@|fg(33) mg|@" +
            "@|fg(46) S|@@|fg(190) o|@@|fg(196) rt|@@|fg(206) " +
            "To|@@|fg(200) o|@@|fg(93) l|@ @|fg(46) " +
            "v1.1.0|@\n";

    public static void main(String[] args) {

        System.out.println(CommandLine.Help.Ansi.AUTO.string(RAINBOW_BANNER));

        System.exit(new CommandLine(new ImgSortRunner())
                .setParameterExceptionHandler(new CliArgsErrorHandler())
                .execute(args));
    }
}