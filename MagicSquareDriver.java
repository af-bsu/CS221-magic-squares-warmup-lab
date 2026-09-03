import java.io.File;

/**
 * Driver class to check or create magic squares.
 * 
 * @author Amira Freeman
 */
public class MagicSquareDriver {
    /**
     * 
     * @param 
     */
    public static void main(String[] args) {
        // Define variables
        boolean checkingMatrix = false;
        boolean creatingMatrix = false;
        String filename;

        // Authenticate flags
        switch (args[0]) {
            case "-check" -> {
                checkingMatrix = true;
            }
            case "-create" -> {
                creatingMatrix = true;
            }
            default -> {
                System.out.println("Error: Incorrect use or lack of mandatory flags");
                printUsageStatement();
                System.exit(1);
            }
        }

        // Authenticate file if checking
        filename = args[1];
        File testFile = new File(filename);
        if (checkingMatrix && !testFile.exists()) {
            System.out.println("Error: File does not exist");
            printUsageStatement();
            System.exit(2);
        }

        // Authenticate size if creating
        // Conditional summary: if creating a magic square and the specified size is not odd or three (3) or greater, exit
        if (creatingMatrix && ( (Integer.parseInt(args[2]) % 2 == 0) || (Integer.parseInt(args[2]) < 3) )) {
            System.out.println("Error: Specified size for magic square is either not odd, less than three (3), or both");
            printUsageStatement();
            System.exit(3);
        }


    }

    /**
     * Prints a usage statement using two lines for each use case.
     * Ignores cases where the check flag is used with a size argument
     * as the latter can be safely ignored as long as the provided
     * filename exists in the working directory.
     * 
     * @author Amira Freeman
     */
    public static void printUsageStatement() {
        System.out.println("Usage: java MagicSquareDriver -check <filename>");
        System.out.println("       java MagicSquareDriver -create <filename> <size>");
    }
}