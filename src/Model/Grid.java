package Model;

import Model.Symbol.Empty;
import Model.Symbol.Symbol;
import Model.Symbol.SymbolFactory;

import java.util.ArrayList;

/**
 * Created by Irindul on 03/08/2017.
 */
public class Grid {

    private ArrayList<Tile> tiles;

    public Grid(int n) {
        if(n <= 0 )
            throw new IllegalArgumentException("Number of tiles must be positive");
        int sqrt = (int) Math.sqrt(n);
        if(sqrt * sqrt != n )
            throw new IllegalArgumentException("The grid must be a square");



        tiles = new ArrayList<>(n);

        SymbolFactory factory = new SymbolFactory();

        for(int i = 0; i < n; i ++){
            tiles.add(new Tile(factory.create(Empty::new))); //Initializing with empty tile
        }
    }

    public int convertTo1D(int i, int j){
        int width = (int) Math.sqrt(tiles.size());
        return width*i + j;
    }

    public void place(int i, int j, Symbol symbol){
        place(convertTo1D(i, j), symbol);
    }

    private void place (int i, Symbol symbol){
        if(symbol == null)
            throw new IllegalArgumentException("Symbol must exist");

        if(symbol instanceof Empty)
            tiles.get(i).placeSymbol(symbol);
        else if(tiles.get(i).getSymbol() instanceof Empty)
            tiles.get(i).placeSymbol(symbol);
        else {
            throw new IllegalStateException("Tile must be empty");
        }
    }

    public Symbol peek(int i, int j){
        return peek(convertTo1D(i, j));
    }

    public Symbol peek(int i ){
        return tiles.get(i).getSymbol();
    }

    public int size(){
        return tiles.size();
    }

    public int squareSize() {
        return (int) Math.sqrt(size());
    }
}
