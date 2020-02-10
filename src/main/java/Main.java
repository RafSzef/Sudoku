public class Main implements Runnable{
    GUI gui = new GUI();

//    public static void newThread(Thread thread) throws InterruptedException {
//        if (gui.isFinished){
//            thread.interrupt();
//            thread.join();
//            Thread thread1 = new Thread(new Solver());
//            thread1.start();
//            thread1.join();
//        }
//    }
    public static void main(String[] args) throws InterruptedException {
        Thread solvThead = new Thread(new Solver());
        solvThead.start();
        solvThead.join();
   //    newThread(solvThead);
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

