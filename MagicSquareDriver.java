import java.io.File;
import java.io.FileNotFoundException;

/**
 * Driver class to check or create magic squares.
 * 
 * @author Amira Freeman
 */
public class MagicSquareDriver {
    public static void main(String[] args) {
        // Define variables
        boolean checkingMatrix = false;
        boolean creatingMatrix = false;
        File matrixFile;

        // Authenticate arguments
        switch (args[0]) {
            case "-check" -> {
                checkingMatrix = true;
            }
            case "-create" -> {
                creatingMatrix = true;
            }
            default -> {
                System.out.println("Error: Incorrect use or lack of mandatory flags");
                System.out.println("Usage: java MagicSquareDriver <-check | -create> <filename> < |size>");
                System.exit(1);
            }
        }

        try {
            
        } catch (FileNotFoundException e) {
            e.getMessage();
        }

        if ()
    }
}