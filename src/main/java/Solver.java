import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Solver class is used to prepare sudoku You solve during the game !
 * Uses Random() class to randomize first row of matrix on invocation.
 * solve() method uses Random() class and recursion - time needed to complete may vary.
 * Due to this better wait until Solver finish its work before redrawing GUI.
 * @see Random
 * @see Runnable
 */

public class Solver implements Runnable{


    private Integer[] firstLine = {1,2,3,4,5,6,7,8,9};
    int[][] boardMatrix = new int[9][9];
    Random rand = new Random();
    static int[][] finalMatrix = new int[9][9];

    /**
     * Default constructor shuffles first row which contains numbers from 1-9 then insert it to matrix.
     */
    public Solver () {
        shuffleFirstRow();
        fillFirstLine();
    }
    /**
     * Method used to shuffle first row. It's a remnant from the time I were learning how to shuffle arrays.
     * Uses Collections shuffle.
     * @see Collections
     */
    private void shuffleFirstRow () {
        List<Integer> intFirstRow = Arrays.asList(firstLine);
        Collections.shuffle(intFirstRow);
        intFirstRow.toArray(firstLine);
        System.out.println(Arrays.toString(firstLine));
    }

    /**
     * Method used to fill first row of matrix.
     */
    private void fillFirstLine () {
        for (int i=0; i<9; i++)
            boardMatrix[i][0] = firstLine[i];

    }

    /**
     * Checks if given numbers is in row.
     * @param row   row from matrix
     * @param number    given number
     * @return  returns true if number is in row. Else returns false.
     */
    private boolean isInRow(int row, int number) {
        for (int i = 0; i < 9; i++)
            if (boardMatrix[row][i] == number)
                return true;
        return false;
    }

    /**
     * Check if given number is in column.
     * @param col column from matrix
     * @param number    given number
     * @return  returns true if number is in column. Else returns false.
     */
    private boolean isInCol(int col, int number) {
        for (int i = 0; i < 9; i++)
            if (boardMatrix[i][col] == number)
                return true;
        return false;
    }

    /**
     * Check if given number is in box. Box is one of nine squares in which numbers must not duplicate to solve sudoku.
     * @param row   row from matrix
     * @param col   column from matrix
     * @param number     given number
     * @return  returns true if number is present in square. Else returns false.
     */
    private boolean isInBox(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (boardMatrix[i][j] == number)
                    return true;

        return false;
    }
    /**
     * Combines isInRow, isInCol and isInBox. If all are false allows solve method to proceed.
     * @param row   row of matrix we are checking
     * @param col   column of matrix we are checking
     * @param number    given number
     * @return  true when number is NOT present in row, column and box
     */
    private boolean isOk(int row, int col, int number) {
        return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
    }
    /**
     * Method used to fill empty sudoku or solve existing sudoku. It iterates through matrix.
     * If value of matrix is equal to 0 it picks random value between 1-9 and check if it appears in this row, column and box.
     * ChosenNumber is inserted into matrix only if number is unique in row, column and box then we backtrack recursively.
     * If number is not a solution we insert 0 in matrix then try another number.
     *
     * Disclaimer:
     * Due to fact that one for loop uses random number between 0-9 as starting condition there is a possibility that
     * it will select ie. 9 and stop after 1 iteration.
     * @return false when can't solve sudoku and true when sudoku is solved
     */
    public boolean solve() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // we search an empty cell
                if (boardMatrix[row][col] == 0) {
                    // we enter random number
                    for (int randTmp = rand.nextInt(9); randTmp <= 9; randTmp++) {
                        if (isOk(row, col, randTmp)) {
                            // number ok. it respects sudoku constraints
                            boardMatrix[row][col] = randTmp;

                            if (solve()) { // we start backtracking recursively
                                return true;
                            } else { // if not a solution, we empty the cell and we continue
                                boardMatrix[row][col] = 0;
                            }
                        }
                    }

                    return false; // we return false
                }
            }
        }

        return true; // sudoku solved
    }

    public int[][] getBoardMatrix() {
        return boardMatrix;
    }

    public static int[][] getFinalMatrix() {
        return finalMatrix;
    }

    /**
     * Repeats itself until sudoku is solved. Each time it can't be solved changes first row and starts again.
     * In case of error prints error then creates new Solver object and retries run() method.
     */

    public void reset() {
        run();
    }
    public void run() {
        try {
            while (!solve()) {
                shuffleFirstRow();
                fillFirstLine();
                solve();
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        if (boardMatrix[row][col] == 0){
                            System.out.println("Got empty matrix, restarting.");
                            run();
                        }
                    }
                }
                finalMatrix = getBoardMatrix();
            }
        }catch (Exception e){
            new myException(e);
        }
    }
}

/**
 *  Custom exception handler used to create new object and perform run() method.
 */
class myException extends Exception{
    myException(Exception e){
        System.out.println("Exception while running Solver. New instance of Solver created.");
        System.err.println(e);
        Solver solver = new Solver();
        solver.run();
    }
}
