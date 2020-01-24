import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class GUI extends JFrame {

    public int mouseX = -100;
    public int mouseY = -100;
    int spacing = 5;
    int topMargin = this.getHeight()/12;
    int bottomMargin = this.getHeight()/12;
    Random rand = new Random();

    private Integer[] firstLine = {1,2,3,4,5,6,7,8,9};
    private int[][] boardMatrix = new int[9][9];
    private int[][] hiddenMatrix = new int[9][9];
    private int[][] inputMatrix = new int[9][9];

    public GUI ()
    {
        this.setTitle("Sudoku (:");
        this.setSize(1286, 829);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);



        shuffleFirstRow();
        for (int i=0; i<9; i++) { boardMatrix[i][0] = firstLine[i]; }
        solve();
        for (int i =0; i < 9; i++) {
            for (int j =0; j<9 ;j++) {
                if (rand.nextInt(100) < 20){
                    hiddenMatrix[i][j] = 1;
                }
            }
        }

        System.out.println(topMargin);
        System.out.println(bottomMargin);
        System.out.println(boxWidth());
        System.out.println(boxHeight());

        Board board = new Board();
        this.setContentPane(board);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);
    }

    public int boxWidth(){
        int boxWidth = (this.getWidth() - 2*spacing)/9;
        return boxWidth;
    }

    public  int boxHeight(){
        int boxHeight =(this.getHeight() - 2*spacing)/13;
        return boxHeight;
    }

    public class Board extends JPanel
    {
        Color LightSteelBlue = new Color(176, 196, 222);
        Color DarkSeaGreen = new Color(143, 188, 143);


        public void paintComponent(Graphics g)
        {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,this.getWidth(),this.getHeight());


            for (int i =0; i < 9; i++) {
                for (int j =0; j<9 ;j++) {
                    if (mouseX >= 2*spacing+i*boxWidth()+spacing && mouseX <2*spacing+i*boxWidth()+boxWidth()-spacing
                            && mouseY >=2*spacing+j*boxHeight()+50+26+spacing && mouseY <2*spacing+j*boxHeight()+50+26+boxHeight()-spacing)
                    {
                        g.setColor(DarkSeaGreen);
                        g.fillRect(2 * spacing + i * boxWidth(), 2 * spacing + j * boxHeight ()+ 50, boxWidth ()- 2 * spacing, boxHeight() - 2 * spacing);
                    }/*
                    if(inputMatrix[i][j] == boardMatrix[i][j]){
                        g.setColor(Color.red);
                        g.fillRect(2 * spacing + i * boxWidth(), 2 * spacing + j * boxHeight() + 50, boxWidth() - 2 * spacing, boxHeight() - 2 * spacing);
                    }
                    */
                    else if ( i<3 &&  j<3  ) {
                        g.setColor(Color.gray);
                        g.fillRect(  2 * spacing + i * boxWidth(),
                                2 * spacing + j * boxHeight ()+ topMargin,
                                boxWidth ()- 2 * spacing,
                                boxHeight ()- 2 * spacing);
                    }
                    else if (i>5 && i<9 && j<3 ) {
                        g.setColor(Color.gray);
                        g.fillRect(2 * spacing + i * boxWidth(),
                                2 * spacing + j * boxHeight() + 50,
                                boxWidth() - 2 * spacing,
                                boxHeight() - 2 * spacing);
                    }
                    else if (i>2 && i<6 && j>2 && j<6  ) {
                        g.setColor(Color.gray);
                        g.fillRect(2 * spacing + i * boxWidth(), 2 * spacing + j * boxHeight() + 50, boxWidth() - 2 * spacing, boxHeight() - 2 * spacing);
                    }
                    else if ( i<3 && j>5 && j<9  ) {
                        g.setColor(Color.gray);
                        g.fillRect(2 * spacing + i * boxWidth(), 2 * spacing + j * boxHeight() + 50, boxWidth() - 2 * spacing, boxHeight() - 2 * spacing);
                    }
                    else if (i>5 && i<9 && j>5 && j<9  ) {
                        g.setColor(Color.gray);
                        g.fillRect(2 * spacing + i * boxWidth(), 2 * spacing + j * boxHeight() + 50, boxWidth() - 2 * spacing, boxHeight()- 2 * spacing);
                    }
                    else
                    {
                        g.setColor(LightSteelBlue);
                        g.fillRect(2*spacing +i*boxWidth(), 2*spacing+j*boxHeight()+50, boxWidth() - 2*spacing, boxHeight() - 2*spacing);
                    }
                }
            }
            for (int i = 0; i <9; i++){
                for (int j = 0; j <9; j++){
                    //if (hiddenMatrix[i][j] ==1){
                        g.setColor(Color.white);
                        g.setFont(new Font("Tahoma", Font.BOLD, boxHeight()));
                        g.drawString(String.valueOf(boardMatrix[i][j]), 3 * spacing + boxWidth() / 4 + i * (boxWidth()),  90-30  + boxHeight() + (j*boxHeight() -3*spacing));
                    //}

                }
            }
        }
    }


    public class Move implements MouseMotionListener
    {

        public void mouseDragged(MouseEvent mouseEvent) {

        }

        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();

           System.out.println("X : "+mouseX+" , Y: "+mouseY);
        }
    }


    public class Click implements MouseListener
    {

        public void mouseClicked(MouseEvent mouseEvent) {
            /*
            for (int i =0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (mouseX >= 2 * spacing + i * boxWidth() + spacing && mouseX < 2 * spacing + i * boxWidth() + boxWidth() - spacing
                            && mouseY >= 2 * spacing + j * boxHeight() + 50 + 26 + spacing && mouseY < 2 * spacing + j * boxHeight() + 50 + 26 + boxHeight() - spacing) {
                        inputMatrix[i][j] = 1;
                    }
                }
            }

             */

            if (inBoxX() !=-1 && inBoxY() != -1){
                System.out.println("Mouse clicked in box [" + inBoxX() +", " + inBoxY() + "]");
            }else System.out.println("Mouse clicked outside of any boxes");


        }

        public void mousePressed(MouseEvent mouseEvent) {

        }

        public void mouseReleased(MouseEvent mouseEvent) {

        }

        public void mouseEntered(MouseEvent mouseEvent) {

        }

        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    public int inBoxX ()
    {
        for (int i =0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mouseX >= 2 * spacing + i * boxWidth()+ spacing && mouseX < 2 * spacing + i * boxWidth() + boxWidth() - spacing
                        && mouseY >= 2 * spacing + j * boxHeight() + 50 + 26 + spacing && mouseY < 2 * spacing + j * boxHeight() + 50 + 26 + boxHeight() - spacing) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int inBoxY ()
    {
        for (int i =0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (mouseX >= 2 * spacing + i * boxWidth() + spacing && mouseX < 2 * spacing + i * boxWidth() + boxWidth() - spacing
                        && mouseY >= 2 * spacing + j * boxHeight() + 50 + 26 + spacing && mouseY < 2 * spacing + j * boxHeight() + 50 + 26 + boxHeight() - spacing) {
                    return j;
                }
            }
        }
        return -1;
    }

    public void shuffleFirstRow ()
    {
        List<Integer> intFirstRow = Arrays.asList(firstLine);
        Collections.shuffle(intFirstRow);
        intFirstRow.toArray(firstLine);
        System.out.println(Arrays.toString(firstLine));
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
}
/*
Kalkulacje
    50 góra, 1/10 dół

 */