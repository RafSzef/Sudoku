import java.awt.*;
import java.util.Date;
import java.util.Random;

public class Functions {
    // Invoking singleton
    NumberCounter numberCounter = NumberCounter.getInstance();
    //Difficulty settings variables
    public int difficultyVar =2;                        // default difficulty setup
    private int difficulty = 40;                        // default difficulty
    String difficultyString = "Normal";                 // default difficulty string
    Color difficultyColor;                              // color for difficulty string
    // Progress and error counters
    public int correctNumbers = 0;                      // number of numbers in correct position
    public int numberOfErrors = 0;                      // number of mistakes made
    // Date used to show time needed to resolve sudoku
    Date dateAtStart;                                   // date at new game start
    Date dateAtEnd;                                     // date when sudoku is finished
    // 2D arrays used
    private int[][] matrix = new int[9][9];             // stores resolved sudoku obtained from solver
    private int[][] hiddenMatrix = new int[9][9];       // stores position of numbers shown on grid at beginning
    private int[][] inputMatrix = new int[9][9];        // stores numbers entered during game
    //
    private int number = -1;                            // number used in input matrix
    public boolean finished = false;                    // flag used to mark successful finish of sudoku
    public boolean hideMyMistakesPlease =false;         // boolean used to switch mistakes display

    // GETTERS
    public int getNumber() { return number; }
    public int getMatrix(int x, int y) { return matrix[x][y]; }
    public int getHiddenMatrix(int x, int y){ return hiddenMatrix[x][y]; }
    public int getInputMatrix(int x, int y){ return inputMatrix[x][y]; }
    public int[][] getMatrix() { return matrix; }
    public int[][] getHiddenMatrix() { return hiddenMatrix; }
    public int[][] getInputMatrix() { return inputMatrix; }
    // SETTERS
    public void setNumber(int number) { this.number = number; }
    public void setInputMatrix (int x, int y, int number){
        inputMatrix[x][y] = number;
    }
    // Public methods

    public String hideMyMistakesSwitch () { String mistakesString;
        if (!hideMyMistakesPlease) return mistakesString = "Mistakes  :" + numberOfErrors;
        return mistakesString = "Mistakes  : OFF";
    }
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
    public void isFinished (){
        if (correctNumbers == 81) {
            dateAtEnd = new Date();
            System.out.println("Finish !");
            finished = true;

        }
    }
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
        dateAtStart = new Date();
        finished = false;
    }
    // Private methods
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
    private void clearHiddenMatrix (){
        for (int i =0; i < 9; i++) {
            for (int j =0; j<9 ;j++) {
                hiddenMatrix[i][j] = 0;
            }
        }
    }
    private void clearInputMatrix (){
        for (int i =0; i < 9; i++) {
            for (int j =0; j<9 ;j++) {
                inputMatrix[i][j] = 0;
            }
        }
    }
    private void getMatrix(int[][] matrix){
        this.matrix=matrix;
    }
}
