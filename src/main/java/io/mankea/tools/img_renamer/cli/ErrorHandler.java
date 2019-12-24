package io.mankea.tools.img_renamer.cli;

import picocli.CommandLine;

import java.io.PrintWriter;

public class ErrorHandler implements CommandLine.IParameterExceptionHandler {
    @Override
    public int handleParseException(CommandLine.ParameterException e, String[] strings) throws Exception {

        CommandLine cmd = e.getCommandLine();
        PrintWriter writer = cmd.getErr();

        writer.println(e.getMessage());
        CommandLine.UnmatchedArgumentException.printSuggestions(e, writer);
        writer.print(cmd.getHelp().fullSynopsis()); // since 4.1

        CommandLine.Model.CommandSpec spec = cmd.getCommandSpec();
        writer.printf("Try '%s --help' for more information.%n", spec.qualifiedName());

        return cmd.getExitCodeExceptionMapper() != null
                ? cmd.getExitCodeExceptionMapper().getExitCode(e)
                : spec.exitCodeOnInvalidInput();

    }
}
