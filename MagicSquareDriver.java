import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Driver class to check or create magic squares.
 * 
 * @author Amira Freeman
 */
public class MagicSquareDriver {
    /**
     * Parses command-line arguments and either validates an existing magic
     * square from a file, or generates a new square and writes it to a file.
     * 
     * Expects two or three arguments, in order: a flag ({@code -check} or
     * {@code -create}); a filename to read from, if checking, or write to,
     * if creating; and a size to generate a magic square with (create),
     * which must be odd and at least three (3).
     * 
     * @param args Command-line arguments which specify a flag, filename,
     * and size [if creating a square], respectively
     */
    public static void main(String[] args) {
        // Authenticate number of arguments - this needs to be done before variable declaration
        if (args.length < 2 || args.length > 3) {
            System.out.println("Error: Lack of arguments");
            printUsageStatement();
            System.exit(1);
        }

        String flag = args[0];
        String filename = args[1];
        int size;
        MagicSquare square;

        // Authenticate flags
        if ( (!flag.equals("-check") && !flag.equals("-create")) || (flag.equals("-create") && args.length < 3) ) {
            System.out.println("Error: Incorrect use or lack of mandatory flags");
            printUsageStatement();
            System.exit(2);
        }

        // Checking sequence (size argument gets ignored even if provided)
        if (flag.equals("-check")) {
            try {
                square = new MagicSquare(filename);
                System.out.println(square.toString());
            } catch (FileNotFoundException e) {
                System.out.println("Error: File does not exist or is not in the expected format");
                printUsageStatement();
                System.exit(3);
            }
        }

        // Creating sequence
        if(flag.equals("-create")) {
            try {
                size = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Error: Specified size for magic square is not a number");
                printUsageStatement();
                System.exit(4);
                return; // This return statement is to get the compiler to quit whining about int size not being initialized
            }

            if (size % 2 == 0 || size < 3) {
                System.out.println("Error: Specified size for magic square is either not odd, less than three (3), or both");
                printUsageStatement();
                System.exit(5);
            }

            try {
                square = new MagicSquare(filename, size);
                System.out.println(square.toString());
            } catch (IOException e) {
                System.out.println("Error: Could not write to file " + filename);
                printUsageStatement();
                System.exit(6);
            }
        }
    }

    /**
     * Prints a usage statement using two lines for each use case.
     * Ignores cases where the check flag is used with a size argument
     * as the latter can be safely ignored as long as the provided
     * filename exists in the working directory.
     */
    private static void printUsageStatement() {
        System.out.println("Usage: java MagicSquareDriver -check <filename>");
        System.out.println("       java MagicSquareDriver -create <filename> <size>");
    }
}