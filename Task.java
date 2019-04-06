package ua.edu.ukma.mariia_synelnyk;

public class Task {

    private int a;
    private int b;
    private int result;
    private char action1;

    public Task(int a, int b, int result, char action1) {
        this.a = a;
        this.b = b;
        this.result = result;
        this.action1 = action1;
    }


    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public int getResult() {
        return result;
    }

    public char getAction1() {

        return action1;
    }

    @Override
    public String toString() {
        return ""+a+action1+b;
    }
}
