package Model.Player;

import Model.Symbol.Empty;
import Model.Symbol.Symbol;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer extends Player {

    private Random rd = new Random();

    public RandomPlayer(Symbol symbol) {
        super(symbol);
    }

    @Override
    public Pair<Integer, Integer> obtainPosition() {

        ArrayList<Pair<Integer,Integer>> positions = new ArrayList<>();
        for (int i = 0; i < grid.squareSize(); i++) {
            for (int j = 0; j < grid.squareSize(); j++) {
                if(grid.peek(i, j) instanceof Empty){
                    positions.add(new Pair<>(i, j));
                }
            }
        }

        int value = rd.nextInt(positions.size());
        return positions.get(value);

    }

    @Override
    public void feedback(Pair<Integer, Integer> position, Pair<Integer, Integer> position2, Symbol symbol1) {

    }


    @Override
    public void hasWon() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void lost() {

    }

    @Override
    public void reset() {

    }
}
