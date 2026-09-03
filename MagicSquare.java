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
    public boolean isMagicSquare() {
        return true;
    }
}
