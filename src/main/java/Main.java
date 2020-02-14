public class Main implements Runnable{
    GUI gui = new GUI();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Main()).start();
    }

    public void run() {

        int[][] matrix = Solver.getFinalMatrix();
        gui.getMatrix(matrix);
        while (true)
            gui.repaint();

    }
}

