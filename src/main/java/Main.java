public class Main implements Runnable{
    // Create gui object
    GUI gui = new GUI();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Main()).start();
    }

    public void run() {
        while (true)
            gui.repaint();
    }
}

