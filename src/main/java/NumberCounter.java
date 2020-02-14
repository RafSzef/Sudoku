class NumberCounter {
    // Creating singleton
    public static final NumberCounter INSTANCE = new NumberCounter();
    private NumberCounter(){}
    public static NumberCounter getInstance(){
        return INSTANCE;
    }
    // array which contains 9 numbers from 0-9
    int[] countArray = new int[9];

    public void clear() {
        for (int i = 0; i < countArray.length; i++) countArray[i] = 0;
        System.out.println("array cleared");
    }

    public void addNumber(int position) {
        if (position > 0) countArray[position - 1]++;
    }

    public void removeNumber(int position) {
        if (position > 0) countArray[position - 1]--;
    }

    public int getNumber(int position) {
        if (position >= 0) return (int) (countArray[position]);
        return 0;
    }
}
