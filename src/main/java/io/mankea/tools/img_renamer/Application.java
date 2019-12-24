package io.mankea.tools.img_renamer;

import io.mankea.tools.img_renamer.cli.ErrorHandler;
import io.mankea.tools.img_renamer.cli.ImgRenameRunner;
import picocli.CommandLine;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        System.exit(new CommandLine(new ImgRenameRunner())
                .setParameterExceptionHandler(new ErrorHandler())
                .execute(args));
    }
}