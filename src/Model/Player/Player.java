package Model.Player;

import Model.Grid;
import Model.Symbol.Symbol;
import javafx.util.Pair;

/**
 * Created by Irindul on 03/08/2017.
 */
public abstract class Player {

    protected Grid grid;
    protected Symbol symbol;

    public Symbol getSymbol() {
        return symbol;
    }

    public Player(Symbol symbol) {
        this.symbol = symbol;
    }

    public abstract Pair<Integer, Integer> obtainPosition();

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Pair<Integer, Integer> play(){
        boolean go;
        Pair<Integer, Integer> position;
        do{
            go = false;
            position =  obtainPosition();
            try {
                grid.place(position.getKey(), position.getValue(), symbol);
            } catch (IllegalStateException e){ //Exception is raised when tile is not empty
                System.out.println(e.getMessage());
                go = true;
            }
        } while(go);
        return position;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj == this )
            return true;
        if(!(obj instanceof Player))
            return false;
        Player other = (Player) obj;
        if(other.symbol == this.symbol)
            return  true;

        //Different
        return false;



    }
    public abstract void feedback(Pair<Integer, Integer> position, Pair<Integer, Integer> position2, Symbol symbol1);
    public abstract void hasWon();
    public abstract void draw();
    public abstract void lost();
    public abstract void reset();
}
