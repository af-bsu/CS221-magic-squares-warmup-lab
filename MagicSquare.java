import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Javadoc copied from the interface file for easy access:
 * 
 * Methods required for a class that validates
 * or creates magic squares in files with format:
 *   dimensionN
 *   v1 v2 ... vn
 *   ...
 *   vn1 vn2 ... vnn
 * e.g.
 *   3
 *   4 9 2
 *   3 5 7
 *   8 1 6
 * 
 * Two constructors are required.
 * 
 * The first constructor takes a filename, only,
 * and attempts to read that file. If the file
 * cannot be opened or is not in the correct
 * format, a FileNotFoundException should be thrown.
 *   public MagicSquare(String filename) throws FileNotFoundException
 * This constructor is required to call a private
 * utility method
 *   private int[][] readMatrix(String filename) throws FileNotFoundException
 * to open and read the file into a 2D int array.
 * 
 * The second constructor takes a filename and
 * an int for the dimension N of a new NxN magic
 * square. A generated matrix should be written
 * in the required format to a file with the given
 * name.
 *   public MagicSquare(String filename, int dimension) throws IOException
 * This constructor is required to call a private
 * utility method
 *   private void writeMatrix(int[][] matrix, String filename) throws IOException
 * to write the matrix to the file.
 */

public class MagicSquare implements MagicSquareInterface {
    private int[][] matrixArray;
    private boolean isValidSquare;

    /**
     * 
     * @param filename File name for the method to attempt to open and read
     * @throws FileNotFoundException
     */
    @SuppressWarnings("null")
    public MagicSquare(String filename) throws FileNotFoundException {
        Scanner scan = null; // Variable scope is important ;)
        try {
            scan = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.out.println("Error: File does not exist");
            printUsageStatement();
            scan.close();
            System.exit(2);
        }
        
        scan.close();
    }
    
    public MagicSquare(String filename, int dimension) {
        
    }

    @Override
    public boolean isMagicSquare() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int[][] getMatrix() {
        return matrixArray;
    }

    @Override
    public String toString() {
        String header = "The matrix\n";
        String footer = "\nis a magic square.";
        if (!isValidSquare) {
            footer = "\nis not a magic square.";
        }

        // Not an ideal solution but it's my best for now
        String matrixString = "";
        for (int i = 0; i < matrixArray.length; i++) {
            matrixString += "\t";
            for (int j = 0; j < matrixArray.length; j++) {
                matrixString += matrixArray[i][j];
                if (j < matrixArray.length - 1) {
                    matrixString += " ";
                } else {
                    matrixString += "\n";
                }
            }
        }

        String finalMatrixString = header + matrixString + footer;
        return finalMatrixString;
    }

    /**
     * 
     * @param filename
     * @return a final two-dimensional integer array that represents the matrix (magic square)
     * @throws FileNotFoundException
     */
    @SuppressWarnings("ConvertToTryWithResources")
    private int[][] readMatrix(String filename) throws FileNotFoundException {
        File file = new File(filename);

        Scanner dimensionReader = new Scanner(file);
        int dimension = dimensionReader.nextInt();
        dimensionReader.close();

        int[][] matrix = new int[dimension][dimension];

        int lineCount = 0;
        int numCount = 0;
        Scanner linescan = new Scanner(file);
        linescan.nextLine(); // Ignore the line which reads only the magic square's dimension
        while (linescan.hasNextLine()) {
            String line = linescan.nextLine();
            Scanner numScan = new Scanner(line);
            numScan.useDelimiter("//s+");
            numCount = 0;
            while (numScan.hasNext()) {
                matrix[lineCount][numCount] = numScan.nextInt();
                numCount++;
            }
            lineCount++;
            numScan.close();
        }

        linescan.close();
        return matrix;
    }

    private void writeMatrix(int[][] matrix, String filename) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
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
