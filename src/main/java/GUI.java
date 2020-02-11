import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Random;

/**
 * GUI (Graphical User Interface) uses Graphics class to draw grid and all function buttons for sudoku.
 * @see Graphics
 * @see JFrame
 */
public class GUI extends JFrame {


    // mouse location at startup
    public int mouseX = -100;
    public int mouseY = -100;
    // horizontal margin
    final private int spacing = 5;
    // vertical resizable margins
    private int topMargin = this.getHeight()/11 + 30;
    //private int bottomMargin = this.getHeight()/11;
    //
    int number = -1;
    private int correctNumbers = 0;
    private int numberOfErrors = 0;
    Date dateAtStart = new Date();
    Date dateAtEnd;
    int sec = 0;
    int difficultyVar =2;
    int difficulty = 40;
    String difficultyString = "Normal";
    Color difficultyColor = new Color(101, 188, 96);
    private boolean hideMyMistakesPlease =false;
    String mistakesString = "Mistakes  :" + numberOfErrors;

    String hideMyMistakesSwitch (){
        if (!hideMyMistakesPlease) return mistakesString = "Mistakes  :" + numberOfErrors;
        return mistakesString = "Mistakes  : OFF";
    }

    NumberCounter numberCounter = new NumberCounter();
    private int[][] matrix = new int[9][9];
    private int[][] hiddenMatrix = new int[9][9];
    private int[][] inputMatrix = new int[9][9];

    public void getMatrix(int[][] matrix){
            this.matrix=matrix;
    }

    void difficultySetting (int diff){
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
     *
     */
    boolean isFinished (){
        if (correctNumbers == 81) {
            dateAtEnd = new Date();
            System.out.println("Finish !");
            return true;
        } else return false;
    }

    private void setHiddenMatrix (){
        Random rand = new Random();
        for (int i =0; i < 9; i++) {
            for (int j =0; j<9 ;j++) {
                if (rand.nextInt(100) < difficulty){
                    hiddenMatrix[i][j] = 1;
                 //   numberCounter.clear();
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
    private void resetIT (){
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
    }
    public void setInputMatrix (int x, int y, int number){
        inputMatrix[x][y] = number;
    }

    /**
     *
     */
    public GUI ()
    {

        difficultySetting(difficultyVar);
        resetIT();
        // JFrame parameters
        this.setTitle("Sudoku (:");
        this.setSize(1286, 829);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);


        // ContentPane
        Board board = new Board();
        this.setContentPane(board);
        // Mouse Motion Listener
        Move move = new Move();
        this.addMouseMotionListener(move);
        // Mouse Listener
        Click click = new Click();
        this.addMouseListener(click);
        // Key Adapter
        Keys keys = new Keys();
        this.addKeyListener(keys);


    }

    public int boxWidth(){
        return (this.getWidth() - 2*spacing)/9;
    }
    public  int boxHeight(){
        return (this.getHeight() - topMargin - 2*spacing)/11;
    }

    /**
     *
     */
    public class Board extends JPanel
    {

        // Colors used
        Color LightSteelBlue = new Color(69, 125, 137);
        Color DarkSeaGreen = new Color(143, 188, 143);
        Color DarkerSeaGreen = new Color(95, 133, 95);

        public void paintComponent(Graphics g) {
            // fill background
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            // Draw Function Buttons
            paintTopUtilityButtons(g);
            for (int i =0; i< 9; i++){
                g.setColor(Color.black);
                g.fillRect(2 * spacing + i * boxWidth(),
                        this.getHeight() - boxHeight() -2*spacing,
                        boxWidth() - 2 * spacing,
                        boxHeight());
            }
            // Draw Grid
            for (int i =0; i < 9; i++) {
                for (int j =0; j<10 ;j++) {
                    if (j == 9){
                        if (mouseX >= 2*spacing+i*boxWidth()+spacing
                                && mouseX <i*boxWidth()+boxWidth() +spacing
                                && mouseY >= boxHeight() + j*boxHeight() + topMargin
                                && mouseY < this.getHeight()+ boxHeight() )
                        {
                            g.setColor(DarkerSeaGreen);
                            g.fillRect(2 * spacing + i * boxWidth(),
                                    this.getHeight() - boxHeight() -2*spacing,
                                    boxWidth() - 2 * spacing,
                                    boxHeight());
                        }
                    }else if (mouseX >= 2*spacing+i*boxWidth()+spacing
                            && mouseX <i*boxWidth()+boxWidth() +spacing
                            && mouseY >= boxHeight() + j*boxHeight() + topMargin -2*spacing
                            && mouseY < 2* boxHeight() + j*boxHeight() -4* spacing + topMargin)
                    {
                        {
                            g.setColor(DarkSeaGreen);
                            g.fillRect(2 * spacing + i * boxWidth(),
                                    boxHeight() / 2 + j * boxHeight() + topMargin,
                                    boxWidth() - 2 * spacing,
                                    boxHeight() - 2 * spacing);
                        }
                    } else if ( i<3 &&  j<3  ) {
                        g.setColor(Color.gray);
                        g.fillRect(  2*spacing + i * boxWidth(),
                                boxHeight()/2 + j * boxHeight () + topMargin,
                                boxWidth ()- 2 * spacing,
                                boxHeight ()- 2 * spacing);
                    }
                    else if (i>5 && j<3 ) {
                        g.setColor(Color.gray);
                        g.fillRect(2*spacing + i*boxWidth(),
                                boxHeight()/2 + j*boxHeight () + topMargin,
                                boxWidth() - 2*spacing,
                                boxHeight() - 2*spacing);
                    }
                    else if (i>2 && i<6 && j>2 && j<6  ) {
                        g.setColor(Color.gray);
                        g.fillRect(2*spacing + i*boxWidth(),
                                boxHeight()/2 + j*boxHeight () + topMargin,
                                boxWidth() - 2*spacing,
                                boxHeight() - 2*spacing);
                    }
                    else if ( i<3 && j>5) {
                        g.setColor(Color.gray);
                        g.fillRect(2 * spacing + i * boxWidth(),
                                boxHeight()/2 + j * boxHeight () + topMargin,
                                boxWidth() - 2 * spacing,
                                boxHeight() - 2 * spacing);
                    }
                    else if (i>5 && j>5) {
                        g.setColor(Color.gray);
                        g.fillRect(2 * spacing + i * boxWidth(),
                                boxHeight()/2 + j * boxHeight () + topMargin,
                                boxWidth() - 2 * spacing,
                                boxHeight()- 2 * spacing);
                    }
                    else {
                        g.setColor(LightSteelBlue);
                        g.fillRect(2*spacing +i*boxWidth(),
                                boxHeight()/2 + j * boxHeight() + topMargin,
                                boxWidth() - 2*spacing ,
                                boxHeight() - 2*spacing);
                    }

                }
            }
            // paint digits
            paintLabels(g);
            paintInputMatrix(g);

            printTimer(g);
        } // paintComponent END
    }

    public void printTimer (Graphics g)
    {
        if (isFinished()) {
            sec = (int) ((dateAtEnd.getTime() - dateAtStart.getTime())/1000);
        } else{
            sec = (int) ((new Date().getTime() - dateAtStart.getTime())/1000);
        }

        g.setColor(Color.black);
        g.fillRect(this.getWidth() - boxWidth() -spacing  ,
                    spacing ,
                    boxWidth() ,
                    boxHeight()/2) ;
        g.setColor(Color.white);
        g.setFont(new Font("Tacoma", Font.BOLD, 40));
        if (sec < 10) {
            g.drawString("00" + Integer.toString(sec), this.getWidth() - boxWidth(), boxHeight() / 2);
        } else if (sec < 100) {
            g.drawString("0" + Integer.toString(sec), this.getWidth() - boxWidth(), boxHeight() / 2);
        } else {
            g.drawString(Integer.toString(sec), this.getWidth() - boxWidth(), boxHeight() / 2);
        }

    }

    /**
     * Method used to print numbers on function buttons ( j == 9) and on grid.
     * Numbers painted on grid only in places where hiddenMatrix == 1;
     */
    public void paintLabels(Graphics g){
        for (int i = 0; i <9; i++){
            for (int j = 0; j <10; j++){
                if (j == 9 ){
                        if (numberCounter.getNumber(i) < 9) {
                            if (i + 1 == number) {
                                g.setColor(Color.orange);
                            } else {
                                g.setColor(Color.white);
                            }
                            //g.setColor(Color.white);
                            g.setFont(new Font("Tacoma", Font.BOLD, boxHeight()));
                            g.drawString(String.valueOf(i + 1),
                                    boxWidth() / 2 - 3 * spacing + i * (boxWidth()),
                                    3 * boxHeight() / 2 + j * boxHeight() + topMargin);
                        }
                } else if (hiddenMatrix[i][j] ==1){
                    if (matrix[i][j] == number){
                        g.setColor(Color.yellow);
                    } else {
                        g.setColor(Color.white);
                    }

                    g.setFont(new Font("Tacoma", Font.BOLD, boxHeight()));
                    g.drawString(String.valueOf(matrix[i][j]),
                            boxWidth()/2 -3*spacing + i * (boxWidth()),
                            3*boxHeight()/2 + (j*boxHeight() -3*spacing) + topMargin);
                }
            } // FOR J END
        } // FOR I END
    }   // paintLabels END

    /**
     * Method used to show input on grid.
     * If InputMatrix[i][j] value is different than matrix [i][j] box changes color to red.
     */
    public void paintInputMatrix (Graphics g){
        for (int i = 0; i <9; i++){
            for (int j = 0; j <9; j++){
                if (inputMatrix[i][j] !=0) {
                    if (hiddenMatrix[i][j] != 1 && inputMatrix[i][j] >0 ) {
                        if (inputMatrix[i][j] != matrix[i][j]) {
                            g.setColor(Color.red);
//                            g.fillRect(2 * spacing + i * boxWidth(),
//                                    boxHeight() / 2 + j * boxHeight() + topMargin,
//                                    boxWidth() - 2 * spacing,
//                                    boxHeight() - 2 * spacing);
                        }
                            else if (matrix[i][j] == number){
                            g.setColor(Color.orange);
                            } else {
                            g.setColor( new Color(219, 255, 231));
                            }

                            g.setFont(new Font("Tacoma", Font.BOLD, boxHeight()));
                            g.drawString(String.valueOf(inputMatrix[i][j]),
                                    boxWidth() / 2 - 3 * spacing + i * (boxWidth()),
                                    3 * boxHeight() / 2 + (j * boxHeight() - 3 * spacing) + topMargin);

                    }
                }
            } // FOR J END
        } // FOR I END
    } // paintInputMatrix END

    public void paintTopUtilityButtons(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Tacoma", Font.BOLD, 40));
        g.drawString("NEW GAME", spacing * 4, 45);
        g.setColor(difficultyColor);
        g.drawString(difficultyString, 260, 45);
        g.setColor(Color.gray);
        g.setFont(new Font("Tacoma", Font.BOLD, 20));
        g.drawString(hideMyMistakesSwitch(), 500, 25);
        g.drawString("Complete : " + correctNumbers + "/81", 500, 50);
    }

    /**
     * Mouse motion adapter used to get mouse cursor position.
     */
    public class Move extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

            //System.out.println(mouseX + " " + mouseY);
        }
    }

    /**
     * Mouse click adapter.
     * If pressed on function buttons changes number to corresponding value.
     * If pressed on grid sets new value to inputMatrix via setInputMatrix()
     */
    public class Click extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            if (inShowMistakesBox() != -1){
             hideMyMistakesPlease = !hideMyMistakesPlease;
            }
            if (inNewGameBox() != -1){
                resetIT();
                System.out.println("Reset invoked by mouse click");
            }
            if (inDifficultyBox() != -1){
                System.out.println("Difficulty setting changed via mouse click");
                difficultyVar++;
                if (difficultyVar == 5) difficultyVar=1;
                difficultySetting(difficultyVar);
            }
            if (inFunctionBoxX() != -1){
                number = inFunctionBoxX() +1;
            }
            if (inBoxX() !=-1 && inBoxY() != -1){
                if (mouseEvent.getButton() == MouseEvent.BUTTON3)
                {
                    setInputMatrix(inBoxX(), inBoxY(), 0);
                    System.out.println("rmouse button");
                }else {
                    int tmp = inputMatrix[inBoxX()][inBoxY()];
                    setInputMatrix(inBoxX(), inBoxY(), number);
                    if (inputMatrix[inBoxX()][inBoxY()] == matrix[inBoxX()][inBoxY()] && tmp != matrix[inBoxX()][inBoxY()]){
                        correctNumbers ++;
                        numberCounter.addNumber(number);
                        System.out.println("correct numbers: " + correctNumbers + "/81");
                    } else if (tmp == matrix[inBoxX()][inBoxY()] && inputMatrix[inBoxX()][inBoxY()] != matrix[inBoxX()][inBoxY()]) {
                        correctNumbers --;
                        numberCounter.removeNumber(number);
                        System.out.println("correct numbers: " + correctNumbers + "/81");
                        numberOfErrors++;
                        System.out.println("Mistakes : " + numberOfErrors);
                    }else {
                        numberOfErrors ++;
                        System.out.println("Mistakes : " + numberOfErrors);
                    }
                   // System.out.println("Mouse clicked in box [" + inBoxX() + ", " + inBoxY() + "]");
                }

            }else System.out.println("Mouse clicked outside of any boxes");

        }
    }
    public class Keys extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_R ){
                resetIT();
            } else if (e.getKeyCode() == KeyEvent.VK_1|| e.getKeyCode() == KeyEvent.VK_NUMPAD1){
                number =1;
            }else if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2){
                number =2;
            }else if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3){
                number =3;
            }else if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4){
                number =4;
            }else if (e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5){
                number =5;
            }else if (e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD6){
                number =6;
            }else if (e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD7){
                number =7;
            }else if (e.getKeyCode() == KeyEvent.VK_8 || e.getKeyCode() == KeyEvent.VK_NUMPAD8){
                number =8;
            }else if (e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_NUMPAD9){
                number =9;
            }
        }
    }

    private int inShowMistakesBox(){
        if (mouseX > 500 && mouseX < 590 && mouseY > 40 && mouseY < 55) return 1;
        return -1;
    }
    int inNewGameBox (){
        if (mouseX >= 4 * spacing && mouseX <= 245 && mouseY >= 40 && mouseY <= 75) return 1;
        return -1;
    }
    int inDifficultyBox (){
        if (mouseX >= 268 && mouseX <= 470 && mouseY >= 40 && mouseY <= 75) return 1;
        return -1;
    }
    public int inBoxX () {
        for (int i =0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (    mouseX >= 2 * spacing + i * boxWidth()+ spacing
                        && mouseX < 2 * spacing + i * boxWidth() + boxWidth() - spacing
                        && mouseY >= 2 * spacing + j * boxHeight() + 50 + 26 + spacing
                        && mouseY < 2 * spacing + j * boxHeight() + 50 + 26 + boxHeight() - spacing) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int inBoxY () {
        for (int i =0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (    mouseX >= 2 * spacing + i * boxWidth() + spacing
                        && mouseX < 2 * spacing + i * boxWidth() + boxWidth() - spacing
                        && mouseY >= 2 * spacing + j * boxHeight() + 50 + 26 + spacing
                        && mouseY < 2 * spacing + j * boxHeight() + 50 + 26 + boxHeight() - spacing) {
                    return j;
                }
            }
        }
        return -1;
    }

    public int inFunctionBoxX() {
        for (int i =0; i < 9; i++) {
                if (mouseX >= 2*spacing+i*boxWidth()+spacing
                        && mouseX <i*boxWidth()+boxWidth() +spacing
                        && mouseY >= 10*boxHeight() + topMargin
                        && mouseY < this.getHeight()+ boxHeight() ) {
                    return i;
                }
            }
        return -1;
    }

    class NumberCounter {
        int[] countArray = new int[9];
        public void clear (){
            for (int i =0; i <countArray.length; i++){
                countArray[i] =0;
            }
        }
        public void addNumber(int position){
            if (position >0) {
                countArray[position - 1]++;
                System.out.println("Added in position" + (position) + " value is : " + countArray[position - 1]);
            }
        }
        public void removeNumber(int position){
            if (position >0) {
                countArray[position - 1]--;
                System.out.println("Removed in position" + (position) + " value is : " + countArray[position - 1]);
            }
        }
        public int getNumber(int position){
           // System.out.println("Returning arr [" + number + "] value is " + countArray[position]);
            if (position >=0){
                return (int)(countArray[position]);
            }
            return 0;
        }
    }
}

