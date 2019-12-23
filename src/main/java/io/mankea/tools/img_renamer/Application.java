package io.mankea.tools.img_renamer;

import picocli.CommandLine;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        System.exit(new CommandLine(new ImgRenameRunner()).execute(args));
    }
}