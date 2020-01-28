import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Solver implements Runnable{

    private Integer[] firstLine = {1,2,3,4,5,6,7,8,9};
    int[][] boardMatrix = new int[9][9];
    Random rand = new Random();

    public Solver () {
        shuffleFirstRow();
        fillFirstLine();
    }

    public Solver(int[][] matrix) {
        super();
        this.boardMatrix = matrix;
    }

    private void shuffleFirstRow ()
    {
        List<Integer> intFirstRow = Arrays.asList(firstLine);
        Collections.shuffle(intFirstRow);
        intFirstRow.toArray(firstLine);
        System.out.println(Arrays.toString(firstLine));
    }

    private void fillFirstLine () {
        for (int i=0; i<9; i++)
            boardMatrix[i][0] = firstLine[i];

    }

    private boolean isInRow(int row, int number) {
        for (int i = 0; i < 9; i++)
            if (boardMatrix[row][i] == number)
                return true;

        return false;
    }

    private boolean isInCol(int col, int number) {
        for (int i = 0; i < 9; i++)
            if (boardMatrix[i][col] == number)
                return true;

        return false;
    }

    private boolean isInBox(int row, int col, int number) {
        int r = row - row % 3;
        int c = col - col % 3;

        for (int i = r; i < r + 3; i++)
            for (int j = c; j < c + 3; j++)
                if (boardMatrix[i][j] == number)
                    return true;

        return false;
    }

    private boolean isOk(int row, int col, int number) {
        return !isInRow(row, number)  &&  !isInCol(col, number)  &&  !isInBox(row, col, number);
    }

    public boolean solve() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                // we search an empty cell
                if (boardMatrix[row][col] == 0) {
                    // we try possible numbers
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
    static int[][] finalMatrix = new int[9][9];
    public static int[][] getFinalMatrix() {
        return finalMatrix;
    }



    public void run() {
        try {
            while (!solve()) {
                shuffleFirstRow();
                fillFirstLine();
                solve();
                finalMatrix = getBoardMatrix();
            }
        }catch (Exception e){
            new myException(e);
          //  System.out.println(e);
        }
    }
}
class myException extends Exception{
    myException(Exception e){
        Solver solver = new Solver();
        solver.run();
    }
}
