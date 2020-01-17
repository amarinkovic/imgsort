package io.mankea.tools.imgrenamer;

import io.mankea.tools.imgrenamer.cli.CliArgsErrorHandler;
import io.mankea.tools.imgrenamer.cli.ImgRenameRunner;
import picocli.CommandLine;

public class Application {

    private static final String RAINBOW_BANNER = "\n @|fg(62) I|@@|fg(33) mg|@" +
            "@|fg(46) Re|@@|fg(190) na|@@|fg(196) me|@@|fg(206) " +
            "To|@@|fg(200) o|@@|fg(93) l|@ @|fg(46) " +
            "v1.0.0|@\n";

    public static void main(String[] args) {

        System.out.println(CommandLine.Help.Ansi.AUTO.string(RAINBOW_BANNER));

        System.exit(new CommandLine(new ImgRenameRunner())
                .setParameterExceptionHandler(new CliArgsErrorHandler())
                .execute(args));
    }
}