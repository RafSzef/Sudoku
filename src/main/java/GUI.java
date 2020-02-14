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
    // Mouse position initialization
    public int mouseX = -100;                           // mouse location at startup. X axis
    public int mouseY = -100;                           // mouse location at startup. Y axis
    // Window layout properties
    final private int spacing = 5;                      // horizontal margin
    private int topMargin = this.getHeight()/11 + 30;   // vertical resizable margins
    // Instances initialization
    NumberCounter numberCounter = NumberCounter.getInstance();
    Functions func = new Functions();

    public GUI ()
    {
        func.difficultySetting(func.difficultyVar);               // sets difficulty
        func.resetIT();                                      //
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
        /**
         * Method used to show input on grid.
         * If InputMatrix[i][j] value is different than matrix [i][j] box changes color to red.
         */
        public void paintInputMatrix (Graphics g){
            int[][] inputMatrix = func.getInputMatrix();
            int[][] hiddenMatrix = func.getHiddenMatrix();
            int[][] matrix = func.getMatrix();
            int number = func.getNumber();
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
            g.setColor(func.difficultyColor);
            g.drawString(func.difficultyString, 260, 45);
            g.setColor(Color.gray);
            g.setFont(new Font("Tacoma", Font.BOLD, 20));
            g.drawString(func.hideMyMistakesSwitch(), 500, 25);
            g.drawString("Complete : " + func.correctNumbers + "/81", 500, 50);
        }

        private void printTimer (Graphics g)
        {
            int sec = 0;
            if (!func.finished){
                sec = (int) ((new Date().getTime() - func.dateAtStart.getTime())/1000);
            }

            if (func.finished) {
                sec = (int) ((func.dateAtEnd.getTime() - func.dateAtStart.getTime())/1000);
            }

            g.setColor(Color.black);
            g.fillRect(this.getWidth() - boxWidth() -spacing  ,
                    spacing ,
                    boxWidth() ,
                    boxHeight()/2) ;
            g.setColor(Color.white);
            g.setFont(new Font("Tacoma", Font.BOLD, 40));
            if (sec < 10) {
                g.drawString("00" + sec, this.getWidth() - boxWidth(), boxHeight() / 2);
            } else if (sec < 100) {
                g.drawString("0" + sec, this.getWidth() - boxWidth(), boxHeight() / 2);
            } else {
                g.drawString(Integer.toString(sec), this.getWidth() - boxWidth(), boxHeight() / 2);
            }

        }

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
        /**
         * Method used to print numbers on function buttons ( j == 9) and on grid.
         * Numbers painted on grid only in places where hiddenMatrix == 1;
         */
        public void paintLabels(Graphics g){
            int number = func.getNumber();
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
                    } else if (func.getHiddenMatrix(i,j) ==1){
                        if (func.getMatrix(i, j) == number){
                            g.setColor(Color.yellow);
                        } else {
                            g.setColor(Color.white);
                        }

                        g.setFont(new Font("Tacoma", Font.BOLD, boxHeight()));
                        g.drawString(String.valueOf(func.getMatrix(i,j)),
                                boxWidth()/2 -3*spacing + i * (boxWidth()),
                                3*boxHeight()/2 + (j*boxHeight() -3*spacing) + topMargin);
                    }
                } // FOR J END
            } // FOR I END
        }   // paintLabels END

    }

    /**
     * Mouse motion adapter used to get mouse cursor position.
     */
    private class Move extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    /**
     * Mouse click adapter.
     * If pressed on function buttons changes number to corresponding value.
     * If pressed on grid sets new value to inputMatrix via setInputMatrix()
     */
    private class Click extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            if (inShowMistakesBox() != -1) func.hideMyMistakesPlease = !func.hideMyMistakesPlease;
            if (inFunctionBox() != -1) func.setNumber(inFunctionBox() + 1);
            if (inNewGameBox() != -1) func.resetIT();
            if (inDifficultyBox() != -1){
                func.difficultyVar++;
                if (func.difficultyVar == 5) func.difficultyVar=1;
                func.difficultySetting(func.difficultyVar);
            }
            if (inBoxX() !=-1 && inBoxY() != -1 && func.getNumber() != 0){
                int x = inBoxX();
                int y = inBoxY();
                int inputMatrix = func.getInputMatrix(x, y);
                int matrix = func.getMatrix(x, y);
                int hiddenMatrix = func.getHiddenMatrix(x, y);
                int number = func.getNumber();
                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
                    if (inputMatrix != matrix && inputMatrix != 0 && hiddenMatrix != 1) {
                        func.setInputMatrix(x, y, 0);
                    }
                }else {
                    int tmp = func.getInputMatrix(x, y);
                    func.setInputMatrix(x, y, number);
                    inputMatrix = func.getInputMatrix(x, y);
                    if (inputMatrix == matrix && tmp != matrix && hiddenMatrix !=1) {
                        func.correctNumbers ++;
                        numberCounter.addNumber(number);
                    } else {
                        if (tmp == matrix && inputMatrix != matrix) {
                            func.correctNumbers --;
                            numberCounter.removeNumber(tmp);
                        }
                        func.numberOfErrors++;
                    }
                }
            }
            func.isFinished();
        }
    }

    /**
     * My Key Adapter. Nested class.
     */
    class Keys extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_R ){
                func.resetIT();
            } else if (e.getKeyCode() == KeyEvent.VK_1|| e.getKeyCode() == KeyEvent.VK_NUMPAD1){
                func.setNumber(1);
            }else if (e.getKeyCode() == KeyEvent.VK_2 || e.getKeyCode() == KeyEvent.VK_NUMPAD2){
                func.setNumber(2);
            }else if (e.getKeyCode() == KeyEvent.VK_3 || e.getKeyCode() == KeyEvent.VK_NUMPAD3){
                func.setNumber(3);
            }else if (e.getKeyCode() == KeyEvent.VK_4 || e.getKeyCode() == KeyEvent.VK_NUMPAD4){
                func.setNumber(4);
            }else if (e.getKeyCode() == KeyEvent.VK_5 || e.getKeyCode() == KeyEvent.VK_NUMPAD5){
                func.setNumber(5);
            }else if (e.getKeyCode() == KeyEvent.VK_6 || e.getKeyCode() == KeyEvent.VK_NUMPAD6){
                func.setNumber(6);
            }else if (e.getKeyCode() == KeyEvent.VK_7 || e.getKeyCode() == KeyEvent.VK_NUMPAD7){
                func.setNumber(7);
            }else if (e.getKeyCode() == KeyEvent.VK_8 || e.getKeyCode() == KeyEvent.VK_NUMPAD8){
                func.setNumber(8);
            }else if (e.getKeyCode() == KeyEvent.VK_9 || e.getKeyCode() == KeyEvent.VK_NUMPAD9){
                func.setNumber(9);
            }
        }
    }

    private int inShowMistakesBox(){
        if (mouseX > 500 && mouseX < 590 && mouseY > 40 && mouseY < 55) return 1;
        return -1;
    }
    private int inNewGameBox (){
        if (mouseX >= 4 * spacing && mouseX <= 245 && mouseY >= 40 && mouseY <= 75) return 1;
        return -1;
    }
    private int inDifficultyBox (){
        if (mouseX >= 268 && mouseX <= 470 && mouseY >= 40 && mouseY <= 75) return 1;
        return -1;
    }

    /**
     * Is the mouse cursor in one of the grid boxes?
     * if so returns the field number on the x axis where the mouse is located.
     * @return -1 if outside any box. 1-9 if in the box.
     */
    private int inBoxX() {
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

    /**
     * Is the mouse cursor in one of the grid boxes?
     * if so returns the field number on the y axis where the mouse is located.
     * @return -1 if outside any box. 1-9 if in the box.
     */
    private int inBoxY () {
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

    /**
     * Is the mouse cursor in one of the function boxes?
     * if so returns the field number on the box where the mouse is located.
     * @return number of box or -1 if outside function box.
     */
    private int inFunctionBox() {
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
}

