import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 * Class containing methods used for operations on 2d arrays.
 * Contains both many package-private field and private field with getters/setters.
 * I did it specifically by wrapping and making private fields that are important to app operation.
 * Mainly used method in this class is resetIt() which starts new game.
 * @author Rafal Szefler
 * @version 1.0.0
 * @see Solver
 */
public class Functions {
            // FIELDS START
    // Invoking singleton
    NumberCounter numberCounter = NumberCounter.getInstance();
    //Difficulty settings variables
    int difficultyVar =2;                               // default difficulty setup
    private int difficulty = 40;                        // default difficulty
    String difficultyString = "Normal";                 // default difficulty string
    Color difficultyColor;                              // color for difficulty string
    // Progress and error counters
    int correctNumbers = 0;                             // number of numbers in correct position
    int numberOfErrors = 0;                             // number of mistakes made
    // Date used to show time needed to resolve sudoku
    Date dateAtStart;                                   // date at new game start
    Date dateAtEnd;                                     // date when sudoku is finished
    private ArrayList<Integer> bestTime = new ArrayList();
    // 2D arrays used
    private int[][] matrix = new int[9][9];             // stores resolved sudoku obtained from solver
    private int[][] hiddenMatrix = new int[9][9];       // stores position of numbers shown on grid at beginning
    private int[][] inputMatrix = new int[9][9];        // stores numbers entered during game
    //
    private int number = -1;                            // number used in input matrix
    boolean finished = false;                           // flag used to mark successful finish of sudoku
    boolean hideMyMistakesPlease =false;                // boolean used to switch mistakes display
    int gamesPlayed = 0 ;                               // games played, starts at 0
            //FIELDS END

    // GETTERS
    public int getNumber() { return number; }
    public int getMatrix(int x, int y) { return matrix[x][y]; }
    public int getHiddenMatrix(int x, int y){ return hiddenMatrix[x][y]; }
    public int getInputMatrix(int x, int y){ return inputMatrix[x][y]; }
    public int[][] getMatrix() { return matrix; }
    public int[][] getHiddenMatrix() { return hiddenMatrix; }
    public int[][] getInputMatrix() { return inputMatrix; }
    public int getBestTime (){
        if (!bestTime.isEmpty()){
            return bestTime.get(0);
        } return 0;
    }
    // SETTERS
    public void setNumber(int number) { this.number = number; }
    public void setInputMatrix (int x, int y, int number){
        inputMatrix[x][y] = number;
    }
    // Public methods

    /**
     * Method changing label displaying number of mistakes made.
     * @return Static Sting or String with number of errors.
     */
    public String hideMyMistakesSwitch () {
        if (!hideMyMistakesPlease) return  "Mistakes  :" + numberOfErrors;
        return  "Mistakes  : OFF";
    }

    /**
     * Method using switch to change difficulty of game. Changes number of fields shown at beginning,
     * and text ( and its color ) displayed next to new game.
     * @param diff from 1 to 4. 1 is very easy; 2 is easy; 3 is medium; 4 is hard.
     */
    public void difficultySetting (int diff){
        switch (diff){
            case 1:
                difficulty = 80;
                difficultyString = "V. EASY";
                difficultyColor = new Color(143, 188, 143);
                break;
            case 2:
                difficulty = 60;
                difficultyString = "EASY";
                difficultyColor = new Color(46, 188, 53);
                break;
            case 3:
                difficulty = 40;
                difficultyString = "MEDIUM";
                difficultyColor = new Color(35, 188, 179);
                break;
            case 4:
                difficulty = 20;
                difficultyString = "HARD";
                difficultyColor = new Color(188, 75, 54);
                break;
        }
    }

    /**
     * Method used to change finished flag when the correct number is equal to 81.
     * Besides changing finished flag this method also: increase gamesPlayed count,
     * creates new Date used to stop time counter,
     * and adds new time to bestTime ArrayList.
     */
    public void isFinished (){
        if (correctNumbers == 81) {
            dateAtEnd = new Date();
            int timeSpent = (int) ((dateAtEnd.getTime() - dateAtStart.getTime())/1000);
            bestTime.add(timeSpent);
            gamesPlayed ++;
            finished = true;
        }
    }

    /**
     * Method used to reset or start new game.
     * It clears all fields and 2D arrays containing information about current game.
     * Then prepare for new game running solver again, resets time and changing finished flag to false.
     */
    public void resetIT (){
        numberCounter.clear();
        number =0;
        correctNumbers =0;
        numberOfErrors = 0;
        clearInputMatrix();
        clearHiddenMatrix();
        Solver solver = new Solver();
        solver.run();
        getMatrix(Solver.getFinalMatrix());
        setHiddenMatrix();
        sortBestTime();
        dateAtStart = new Date();
        finished = false;
    }
    // Private methods

    /**
     * Private method used to pick random spots in which numbers are show at the beginning of game.
     * Depending od difficulty value number of show game field vary.
     * HiddenMatrix can only have a value of 0 (hidden) or 1 (shown)
     */
    private void setHiddenMatrix (){
        Random rand = new Random();
        for (int i =0; i < 9; i++) {
            for (int j =0; j<9 ;j++) {
                if (rand.nextInt(100) < difficulty){
                    hiddenMatrix[i][j] = 1;
                    numberCounter.addNumber(matrix[i][j]);
                    correctNumbers ++;
                }
            }
        }
    }

    /**
     * Method used to clear hiddenMatrix, sets all values to 0.
     */
    private void clearHiddenMatrix (){
        for (int i =0; i < 9; i++) {
            for (int j =0; j<9 ;j++) {
                hiddenMatrix[i][j] = 0;
            }
        }
    }

    /**
     * Method used to clear inputMatrix, sets all values to 0.
     */
    private void clearInputMatrix (){
        for (int i =0; i < 9; i++) {
            for (int j =0; j<9 ;j++) {
                inputMatrix[i][j] = 0;
            }
        }
    }

    /**
     * Method used to get main matrix, sets given 2D array as base matrix for this class.
     * @param matrix 2D array used as matrix in this class.
     */
    private void getMatrix(int[][] matrix){
        this.matrix=matrix;
    }
    /**
     * Method used to sort bestTime ArrayList.
     */
    private void sortBestTime(){
        Collections.sort(bestTime);
    }
}
