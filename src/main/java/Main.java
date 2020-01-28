public class Main implements Runnable{
    Solver solver = new Solver();
    GUI gui = new GUI();

    public static void main(String[] args) throws InterruptedException {
        Thread solvThead = new Thread(new Solver());
        solvThead.start();
        solvThead.join();
        new Thread(new Main()).start();
    }



    public void run() {

        int[][] matrix = Solver.getFinalMatrix();
        gui.getMatrix(matrix);
        while (true)
        {
            gui.repaint();
        }
    }
}
