/**
 * Main class used to run sudoku.
 * It only creates new gui object from GUI class then starts to repaint it indefinitely.
 * @author Rafal Szefler
 * @version 1.0.0
 */
public class Main implements Runnable{
    // Create gui object
    GUI gui = new GUI();

    /**
     * Main method which creates new Main thread and starts it.
     * @throws InterruptedException throws Interrupted Exception.
     */
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Main()).start();
    }

    /**
     * Override run method, indefinitely repaints gui object.
     */
    public void run() {
        while (true)
            gui.repaint();
    }
}

