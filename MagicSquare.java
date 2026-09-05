import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Reads, validates, and generates magic squares stored in files.
 * 
 * @author Amira Freeman
 */
@SuppressWarnings("OverridableMethodCallInConstructor")
public class MagicSquare implements MagicSquareInterface {
    private final int[][] matrixArray;
    private final boolean isValidSquare;

    /**
     * MagicSquare constructor for when the user is reading and verifying
     * a magic square from a specified file.
     * 
     * @param filename File name for the method to attempt to open and read
     * @throws FileNotFoundException if the file does not exist or cannot be opened
     */
    public MagicSquare(String filename) throws FileNotFoundException {
        matrixArray = readMatrix(filename);
        isValidSquare = isMagicSquare();
    }

    /**
     * MagicSquare constructor for when the user is generating and writing
     * a magic square of a specified size to a specified file path.
     * 
     * @param filename File name which the generated square should be written to
     * @param dimension Size or dimension (known internally as 'n') of the (n x n) magic square to generate
     * @throws IOException if the file is not writable or creatable
     */
    public MagicSquare(String filename, int dimension) throws IOException {
        matrixArray = generateMatrix(dimension);
        isValidSquare = isMagicSquare();
        writeMatrix(matrixArray, filename);
    }

    @Override
    public boolean isMagicSquare() {
        int dimension = matrixArray.length;
        int magicNumber = (dimension * (dimension * dimension + 1)) / 2;

        if (dimension == 0) {
            return false;
        }

        // Not a magic square if its dimensions are not equal (1:1)
        for (int row = 0; row < dimension; row++) {
            if (matrixArray[row] == null || matrixArray[row].length != dimension) {
                return false;
            }
        }

        // Boolean arrays are weird. They're easy to work with though.
        boolean[] seen = new boolean[dimension * dimension + 1];
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                int value = matrixArray[row][col];
                if (value < 1 || value > dimension * dimension || seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }

        // Not a magic square if even a single row sum is not the magic number
        for (int row = 0; row < dimension; row++) {
            int rowSum = 0;
            for (int col = 0; col < dimension; col++) {
                rowSum += matrixArray[row][col];
            }
            if (rowSum != magicNumber) {
                return false;
            }
        }

        // Not a magic square if even a single column sum is not the magic number
        for (int col = 0; col < dimension; col++) {
            int colSum = 0;
            for (int row = 0; row < dimension; row++) {
                colSum += matrixArray[row][col];
            }
            if (colSum != magicNumber) {
                return false;
            }
        }

        // Top-left to bottom-right diagonal sum
        int diagonalSum = 0;
        for (int position = 0; position < dimension; position++) {
            diagonalSum += matrixArray[position][position];
        }
        if (diagonalSum != magicNumber) {
            return false;
        }

        // Top-right to bottom-left diagonal sum
        diagonalSum = 0;
        for (int position = 0; position < dimension; position++) {
            diagonalSum += matrixArray[position][dimension - position - 1];
        }

        return diagonalSum == magicNumber;
    }

    @Override
    public int[][] getMatrix() {
        // Return a COPY of the matrix... nearly got me there...
        int[][] copy = new int[matrixArray.length][];
        for (int row = 0; row < matrixArray.length; row++) {
            copy[row] = matrixArray[row].clone();
        }

        return copy;
    }

    @Override
    public String toString() {
        // I didn't know StringBuilder was a class until now. This helps bring resource allocation down since String objects are immutable
        StringBuilder matrixString = new StringBuilder();

        String footer = "is a magic square.";
        if (!isValidSquare) {
            footer = "is not a magic square.";
        }

        matrixString.append("The matrix\n");
        for (int i = 0; i < matrixArray.length; i++) {
            matrixString.append("\t");
            for (int j = 0; j < matrixArray[i].length; j++) {
                matrixString.append(matrixArray[i][j]);
                if (j < matrixArray.length - 1) {
                    matrixString.append(" ");
                } else {
                    matrixString.append("\n");
                }
            }
        }
        matrixString.append(footer);

        return matrixString.toString();
    }

    /**
     * Reads matrices from files using the provided filename and returns a
     * two-dimensional array to save in the instance variable matrixArray.
     * Can read matrices regardless of the characters and the number of them
     * between matrix values (including spaces and tabs).
     * 
     * @param filename File name which the magic square is being read from
     * @return A final two-dimensional integer array that represents the matrix (magic square)
     * @throws FileNotFoundException if the file does not exist or cannot be opened
     */
    @SuppressWarnings("ConvertToTryWithResources")
    private int[][] readMatrix(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner linescan = new Scanner(file);

        try {
            // Scanner cursors are SUPER weird. It took me a while to remember that nextLine() consumes the newline operator
            int dimension = Integer.parseInt(linescan.nextLine().trim());
            int[][] matrix = new int[dimension][dimension];

            for (int row = 0; row < dimension; row++) {
                if (!linescan.hasNextLine()) {
                    throw new FileNotFoundException("File is not in the expected format");
                }

                String line = linescan.nextLine();
                Scanner numscan = new Scanner(line);
                numscan.useDelimiter("\\s+"); // This apparently can see past all whitespace, spaces and tabs alike...
                
                for (int col = 0; col < dimension; col++) {
                    if (!numscan.hasNextInt()) {
                        numscan.close();
                        throw new FileNotFoundException("File is not in the expected format");
                    }

                    matrix[row][col] = numscan.nextInt();
                }

                numscan.close();
            }

            return matrix;
        } catch (java.util.NoSuchElementException | NumberFormatException e) {
            throw new FileNotFoundException("File is not in the expected format");
        } finally {
            linescan.close(); // Been a while since I used finally blocks but they're valid here
        }
    }

    /**
     * Writes the instance's matrix array to a specified file path. Creates the
     * file if it does not already exist. Writes to the file in the mandatory
     * format, with the dimension alone on the first line and the matrix array
     * separated by lines per row. Uses spaces between values.
     * 
     * @param matrix Two-dimensional array which represents the magic square
     * @param filename File name which the magic square will be written to
     * @throws IOException if the file is not writable or creatable
     */
    @SuppressWarnings("ConvertToTryWithResources")
    private void writeMatrix(int[][] matrix, String filename) throws IOException {
        File file = new File(filename);
        file.createNewFile(); // Creates a new file if it does not already exist
        PrintWriter outFile = new PrintWriter(file);

        int n = matrix.length;
        outFile.println(n);
        for (int i = 0; i < n; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < n; j++) {
                row.append(matrix[i][j]);
                if (j < n - 1) {
                    row.append(" ");
                }
            }
            outFile.println(row.toString());
        }

        outFile.close();
    }

    /**
     * Generate a space-separated magic square with specified dimensions and
     * return a two-dimensional array that represents said square.
     * 
     * @param dimension The size or dimension by which to generate the magic square
     * @return A two-dimensional array containing a magic square
     */
    private int[][] generateMatrix(int dimension) {
        // The variable name 'n' for the dimensions from the instructions didn't sit right with me
        int[][] matrix = new int[dimension][dimension];
        int row = dimension - 1;
        int col = dimension / 2;
        int oldRow, oldCol;

        // Researched this algorithm... this is nearly identical to the Siamese-method way of creating magic squares
        for (int i = 1; i <= dimension * dimension; i++) {
            matrix[row][col] = i;

            oldRow = row;
            oldCol = col;
            row++;
            col++;

            if (row == dimension) {
                row = 0;
            }
            if (col == dimension) {
                col = 0;
            }

            if (matrix[row][col] != 0) {
                row = oldRow - 1;
                col = oldCol;
            }
        }

        return matrix;
    }
}
