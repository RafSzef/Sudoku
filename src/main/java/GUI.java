import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private int bottomMargin = this.getHeight()/11;
    //
    int number = -1;



    private int[][] matrix = new int[9][9];
    private int[][] hiddenMatrix = new int[9][9];
    private int[][] inputMatrix = new int[9][9];
    private int[][] secondMatrix = new int [9][9];

    public void getMatrix(int[][] matrix){
            this.matrix=matrix;
    }

    /**
     *
     */
    private void setHiddenMatrix (){
        Random rand = new Random();
        for (int i =0; i < 9; i++) {
            for (int j =0; j<9 ;j++) {
                if (rand.nextInt(100) < 40){
                    hiddenMatrix[i][j] = 1;
                }
            }
        }
    }

    public void setInputMatrix (int x, int y, int number){
        inputMatrix[x][y] = number;
    }

    /**
     *
     */
    public GUI ()
    {
        // JFrame parameters
        this.setTitle("Sudoku (:");
        this.setSize(1286, 829);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

        setHiddenMatrix();


//        System.out.println(topMargin);
//        System.out.println(bottomMargin);
//        System.out.println(boxWidth());
//        System.out.println(boxHeight());

        // ContentPane
        Board board = new Board();
        this.setContentPane(board);
        // Mouse Motion Listener
        Move move = new Move();
        this.addMouseMotionListener(move);
        // Mouse Listener
        Click click = new Click();
        this.addMouseListener(click);


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
        } // paintComponent END
    }

    /**
     * Method used to print numbers on function buttons ( j == 9) and on grid.
     * Numbers painted on grid only in places where hiddenMatrix == 1;
     */
    public void paintLabels(Graphics g){
        for (int i = 0; i <9; i++){
            for (int j = 0; j <10; j++){
                if (j == 9 ){
                    if (i +1 == number){
                        g.setColor(Color.orange);
                    } else {
                        g.setColor(Color.white);
                    }
                    //g.setColor(Color.white);
                    g.setFont(new Font("Tacoma", Font.BOLD, boxHeight()));
                    g.drawString(String.valueOf(i +1),
                            boxWidth()/2 -3*spacing + i * (boxWidth()),
                            this.getHeight() - boxHeight() + 3*spacing);
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
                            g.setColor(Color.darkGray);
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

    /**
     * Mouse motion adapter used to get mouse cursor position.
     */
    public class Move extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

           //System.out.println("X : "+mouseX+" , Y: "+mouseY);
        }
    }

    /**
     * Mouse click adapter.
     * If clicked on function buttons changes number to corresponding value.
     * If clicked on grid sets new value to inputMatrix via setInputMatrix()
     */
    public class Click extends MouseAdapter {
        public void mousePressed(MouseEvent mouseEvent) {
            if (inFunctionBoxX() != -1){
                number = inFunctionBoxX() +1;
            }
            if (inBoxX() !=-1 && inBoxY() != -1){
                setInputMatrix(inBoxX(), inBoxY(), number);
                System.out.println("Mouse clicked in box [" + inBoxX() +", " + inBoxY() + "]");


            }else System.out.println("Mouse clicked outside of any boxes");
        }
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


}
