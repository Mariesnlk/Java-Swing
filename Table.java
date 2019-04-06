package ua.edu.ukma.mariia_synelnyk;

import  java.util.concurrent.ThreadLocalRandom;

public class Table {

    private int maxNumber;
    private int taskAmount;
    Task[] task;

    public Task[] generateTasks() {

        return generateTasks(maxNumber, taskAmount);
    }

    /**
     *
     * @param maxNumber
     * @param taskAmount
     * @return task повертається дія додавання або віднімання
     */
    public Task[] generateTasks(int maxNumber, int taskAmount) {
        this.maxNumber = maxNumber;
        this.taskAmount = taskAmount;
        task = new Task[taskAmount];

        for (int i = 0; i < taskAmount; i++) {
            int result, a, b;
            char action1;
            do {
                a = ThreadLocalRandom.current().nextInt(0, maxNumber + 1);
                action1 = (ThreadLocalRandom.current().nextInt(1, 3) == 1) ? '+' : '-';
                b = ThreadLocalRandom.current().nextInt(0, maxNumber + 1);

                result = calculateAnswer(a, action1, b);
            } while (result > maxNumber || result < 0);
            task[i] = new Task(a, b, result, action1);
        }

        return task;
    }

    /**
     *
     * @param a
     * @param action1
     * @param b
     * @return result результат згенерованих чисел і дій над ними
     */
    private int calculateAnswer(int a, char action1, int b) {

        int result = (action1 == '+') ? a + b : a - b;
        return result;
    }


    public int getTaskAmount() {
        return taskAmount;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void setTaskAmount(int taskAmount) {
        this.taskAmount = taskAmount;
    }

    public Task[] getTask() {
        return task;
    }
}