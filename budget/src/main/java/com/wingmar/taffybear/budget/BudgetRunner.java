package com.wingmar.taffybear.budget;

import com.wingmar.taffybear.budget.tx.Budgeter;
import com.wingmar.taffybear.budget.tx.TransactionService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.File;
import java.io.IOException;

public class BudgetRunner {

    public static void main(String[] args) throws IOException {
        final Options options = new Options();
        options.addRequiredOption("f", "file", true, "/path/to/file.csv");
        options.addRequiredOption("i", "include-headers", false,
                "whether or not the input CSV file has a header row");
        final CommandLineParser parser = new DefaultParser();
        try {
            final CommandLine commandLine = parser.parse(options, args);
            final String filePath = commandLine.getOptionValue("f");
            final boolean includeHeaders = commandLine.hasOption("i");
            System.out.println("Running...");
            System.out.println(filePath);
            final AnnotationConfigApplicationContext context =
                    new AnnotationConfigApplicationContext(BudgetApplicationContext.class);

            Budgeter.create(context.getBean(TransactionService.class)).run(new File(filePath), includeHeaders);
            System.out.println("Done.");
        } catch (ParseException e) {
            new HelpFormatter().printHelp("syntax", options);
        }
    }

}
