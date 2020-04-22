package com.wingmar.taffybear.budget;

import com.wingmar.taffybear.budget.tx.TransactionService;
import com.wingmar.taffybear.budget.tx.Transactions;
import com.wingmar.taffybear.budget.tx.UnidentifiableTransactions;
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
            new BudgetRunner().run(new File(filePath), includeHeaders);
            System.out.println("Done.");
        } catch (ParseException e) {
            new HelpFormatter().printHelp("syntax", options);
        }
    }

    private void run(File file, boolean includeHeaders) throws IOException {
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(BudgetApplicationContext.class);

        final TransactionService transactionService = context.getBean(TransactionService.class);
        final UnidentifiableTransactions unidentifiableTransactions = UnidentifiableTransactions
                .fromFile(file, includeHeaders);
        System.out.println(String.format("Found %d transactions across the following date range: %s. Uploading...",
                unidentifiableTransactions.size(), unidentifiableTransactions.getDateRange()));
        final Transactions transactions = transactionService.uploadCsv(file, includeHeaders);

        if (transactions.isEmpty()) {
            System.out.println("No transactions uploaded.");
        } else {
            System.out.println(String.format("Uploaded %d transaction(s) across the following date range: %s.",
                    transactions.size(), transactions.getDateRange()));
            transactions.forEach(System.out::println);
        }
    }
}
