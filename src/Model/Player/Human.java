package Model.Player;

import Model.Grid;
import Model.Symbol.Symbol;
import javafx.util.Pair;

import java.util.Scanner;

/**
 * Created by Irindul on 03/08/2017.
 */
public class Human extends Player {

    public Human(Symbol symbol) {
        super(symbol);
    }

    @Override
    public Pair<Integer, Integer> obtainPosition() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the two values separated by ENTER");
        System.out.print("First Value : ");
        int i = sc.nextInt();
        System.out.print("Second Value : ");
        int j = sc.nextInt();

        return new Pair<>(i, j);

    }

    @Override
    public void feedback(Pair<Integer, Integer> position, Pair<Integer, Integer> position2, Symbol symbol1) {

    }

    @Override
    public void hasWon() {
        System.out.println("You won !");
    }

    @Override
    public void draw() {
        System.out.println("It's a draw");
    }

    @Override
    public void lost() {
        System.out.println("The other player won");
    }

    @Override
    public void reset() {
        System.out.println("Reseting for new play");
    }

}
