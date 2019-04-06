package ua.edu.ukma.mariia_synelnyk;

public class Tester {

    private Table gameModel = new Table();
    private Game view;

    public Tester() {

        view = new Game(this, gameModel);

    }

    public static void main(String[] args) {

        Tester gameController = new Tester();

    }

    public int checkIfInputIsNumber(String input) {
        int number;
        try {
            number = Integer.valueOf(input);
        } catch (NumberFormatException e){
            return -1;
        }
        return number;
    }
}
