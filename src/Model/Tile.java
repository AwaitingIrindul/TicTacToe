package Model;

import Model.Symbol.Symbol;

/**
 * Created by Irindul on 03/08/2017.
 */
public class Tile {
    private Symbol symbol;


    public Tile(Symbol symbol) {
        this.symbol = symbol;
    }

    public void placeSymbol(Symbol symbol){
        this.symbol = symbol;
    }

    public Symbol getSymbol(){
        return symbol;
    }
}
