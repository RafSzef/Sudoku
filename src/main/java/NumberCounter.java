class NumberCounter {
    int[] countArray = new int[9];
    public void clear() {
        for (int i = 0; i < countArray.length; i++) countArray[i] = 0;
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
