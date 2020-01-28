import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;


public class GUI extends JFrame {

    public int mouseX = -100;
    public int mouseY = -100;
    int spacing = 5;
    int topMargin = this.getHeight()/11;
    int bottomMargin = this.getHeight()/11;
    Random rand = new Random();

    private int[][] matrix = new int[9][9];
    private int[][] hiddenMatrix = new int[9][9];
    private int[][] inputMatrix = new int[9][9];

        public void getMatrix(int[][] matrix){
            this.matrix=matrix;
    }
    public GUI ()
    {
        this.setTitle("Sudoku (:");
        this.setSize(1286, 829);
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

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
        // change it back !
    public int boxWidth(){
        int boxWidth = (this.getWidth() - 2*spacing)/9;
        return boxWidth;
    }

    public  int boxHeight(){
        int boxHeight =(this.getHeight() - 2*spacing)/11;
        return boxHeight;
    }

    public class Board extends JPanel
    {
        Color LightSteelBlue = new Color(176, 196, 222);
        Color DarkSeaGreen = new Color(143, 188, 143);
        Color DarkerSeaGreen = new Color(95, 133, 95);

        public void paintComponent(Graphics g)
        {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,0,this.getWidth(),this.getHeight());


            for (int i =0; i < 9; i++) {
                for (int j =0; j<10 ;j++) {
                    if (mouseX >= 2*spacing+i*boxWidth()+spacing
                            && mouseX <2*spacing+i*boxWidth()+boxWidth()-spacing
                            && mouseY >= boxHeight() + j * boxHeight() -spacing
                            && mouseY < 2* boxHeight() + boxHeight()*j - 2*spacing)
                    {
                        if (j ==9){
                        g.setColor(DarkerSeaGreen);
                        g.fillRect(2 * spacing + i * boxWidth(),
                                boxHeight()/2 + j * boxHeight (),
                                boxWidth ()- 2 * spacing,
                                boxHeight() - 2 * spacing);
                        } else
                        g.setColor(DarkSeaGreen);
                        g.fillRect(2 * spacing + i * boxWidth(),
                                boxHeight()/2 + j * boxHeight (),
                                boxWidth ()- 2 * spacing,
                                boxHeight() - 2 * spacing);
                    }/*
                    if(inputMatrix[i][j] == boardMatrix[i][j]){
                        g.setColor(Color.red);
                        g.fillRect(2 * spacing + i * boxWidth(), 2 * spacing + j * boxHeight() + 50, boxWidth() - 2 * spacing, boxHeight() - 2 * spacing);
                    }
                    */
                    else if ( i<3 &&  j<3  ) {
                        g.setColor(Color.gray);
                        g.fillRect(  2*spacing + i * boxWidth(),
                                boxHeight()/2 + j * boxHeight (),
                                boxWidth ()- 2 * spacing,
                                boxHeight ()- 2 * spacing);
                    }
                    else if (i>5 && j<3 ) {
                        g.setColor(Color.gray);
                        g.fillRect(2*spacing + i*boxWidth(),
                                boxHeight()/2 + j*boxHeight (),
                                boxWidth() - 2*spacing,
                                boxHeight() - 2*spacing);
                    }
                    else if (i>2 && i<6 && j>2 && j<6  ) {
                        g.setColor(Color.gray);
                        g.fillRect(2*spacing + i*boxWidth(),
                                boxHeight()/2 + j*boxHeight (),
                                boxWidth() - 2*spacing,
                                boxHeight() - 2*spacing);
                    }
                    else if ( i<3 && j>5 && j<9  ) {
                        g.setColor(Color.gray);
                        g.fillRect(2 * spacing + i * boxWidth(),
                                boxHeight()/2 + j * boxHeight (),
                                boxWidth() - 2 * spacing,
                                boxHeight() - 2 * spacing);
                    }
                    else if (i>5 && j>5 && j<9  ) {
                        g.setColor(Color.gray);
                        g.fillRect(2 * spacing + i * boxWidth(),
                                boxHeight()/2 + j * boxHeight (),
                                boxWidth() - 2 * spacing,
                                boxHeight()- 2 * spacing);
                    }
                    else if(j == 9){
                        g.setColor(Color.black);
                        g.fillRect(2 * spacing + i * boxWidth(),
                                this.getHeight() - boxHeight() - 2*spacing,
                                boxWidth() - 2 * spacing,
                                this.getHeight() -2* spacing);
                    }
                    else {
                        g.setColor(LightSteelBlue);
                        g.fillRect(2*spacing +i*boxWidth(),
                                boxHeight()/2 + j * boxHeight (),
                                boxWidth() - 2*spacing,
                                boxHeight() - 2*spacing);
                    }

                }
            }
            for (int i = 0; i <9; i++){
                for (int j = 0; j <9; j++){
                    if (hiddenMatrix[i][j] ==1){
                        g.setColor(Color.white);
                        g.setFont(new Font("Tahoma", Font.BOLD, boxHeight()));
                    g.drawString(String.valueOf(matrix[i][j]),
                            boxWidth()/2 -3*spacing + i * (boxWidth()),
                            3*boxHeight()/2 + (j*boxHeight() -3*spacing));
                    }

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

        public void mousePressed(MouseEvent mouseEvent) {}

        public void mouseReleased(MouseEvent mouseEvent) {}

        public void mouseEntered(MouseEvent mouseEvent) {}

        public void mouseExited(MouseEvent mouseEvent) {}
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

}
/*
Kalkulacje
    50 góra, 1/10 dół

 */