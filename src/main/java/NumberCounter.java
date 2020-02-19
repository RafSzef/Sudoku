/**
 * NumberCounter class is a singleton containing information about which
 * and how many of given numbers are displayed in sudoku.
 * Counts usages of numbers from 1-9 and stores them in private array at index lowered by 1 than the given number.
 * For example information of how many number 1 are stored in countArray[0], number 5 in countArray[4]  e.t.c.
 * It's a singleton because it's used in Functions and GUI class and I needed a constant values to proper work.
 * It was quickest solution without using Spring.
 * To invoke use: NumberCounter numberCounter = NumberCounter.getInstance().
 * @author Rafal Szefler
 * @version 1.0.0
 */
class NumberCounter {
    // Creating singleton
    public static final NumberCounter INSTANCE = new NumberCounter();
    private NumberCounter(){}
    public static NumberCounter getInstance(){
        return INSTANCE;
    }
    // array which contains 9 numbers from 0-9
    private int[] countArray = new int[9];

    /**
     * Method used to clear countArray.
     */
    public void clear() {
        for (int i = 0; i < countArray.length; i++) countArray[i] = 0;
        System.out.println("array cleared");
    }

    /**
     * Method used for increasing count of given number in array.
     * Increases number by 1 in index [number -1];
     * @param number number to count
     */
    public void addNumber(int number) {
        if (number > 0) countArray[number - 1]++;
    }

    /**
     * Method used for decreasing count of given number in array.
     * Decrease number by 1 in index [number -1];
     * @param number number to count
     */
    public void removeNumber(int number) {
        if (number > 0) countArray[number - 1]--;
    }

    /**
     * Method used to get value of how many numbers are displayed.
     * @param number number from 1 to 9.
     * @return return how many numbers are displayed.
     */
    public int getNumber(int number) {
        if (number >= 0) return countArray[number];
        return 0;
    }
}
