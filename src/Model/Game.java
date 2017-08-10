package Model;

import Model.Player.Player;
import Model.Symbol.Symbol;
import Model.Symbol.SymbolCounter;
import javafx.util.Pair;

/**
 * Created by Irindul on 03/08/2017.
 */
public class Game {

    private Player first;
    private Player second;
    private Grid grid;

    private Player current;
    private Player winner;
    private SymbolCounter col;
    private SymbolCounter line;
    private boolean verbose;
    private SymbolCounter diag = new SymbolCounter(2); //No matter the size of the square, only two diag all the time.
    private int nb = 0;

    public Game(Player first, Player second, boolean verbose) {
        this.first = first;
        this.second = second;

        grid = new Grid(9);
        first.setGrid(grid);
        second.setGrid(grid);

        current = first;
        int n = grid.squareSize();
        col = new SymbolCounter(n);
        line = new SymbolCounter(n);

        this.verbose = verbose;
    }

    private void switchPlayers(){
        current = current.equals(first) ? second : first;
    }

    public void play(){
        Pair<Integer, Integer> a1p1 = null;
        Pair<Integer, Integer> a1p2 = null;
        int i = 0;
        int j = 0;
            if(verbose)
            display();
        while(!isFinnish()){


            Pair<Integer, Integer> pos = current.play();
            update(pos);
            whoWon(); //Changes reward


            if(current.equals(first))
                a1p1 = pos;

            if(current.equals(second))
                a1p2 = pos;


            if(a1p1 != null && a1p2 != null){
                if(current.equals(second))
                    first.feedback(a1p1, a1p2, second.getSymbol());
                else if(current.equals(first)){
                    second.feedback(a1p2, a1p1, first.getSymbol());
                }
            }

            //feedback for ai

            switchPlayers();
           // current.feedback(pos, s);
            if(verbose)
                display();
        }


        reset();
    }

    private void whoWon() {
        int n = grid.squareSize();
        for (int i = 0; i < n; i++) {
            if(col.getCount(i) == n){
                winner = getPlayerFromSymbol(col.getSymbol(i));
                break;
            }

            if(line.getCount(i) == n){
                winner = getPlayerFromSymbol(line.getSymbol(i));
                break;
            }

            if(i < 2){
                if(diag.getCount(i) == n){
                    winner = getPlayerFromSymbol(diag.getSymbol(i));
                    break;
                }

            }
        }
        if (winner != null) {
            winner.hasWon();

            //We notify other player
            (winner.equals(first) ? second : first).lost();

        }

        else if(isFinnish()) {
            first.draw();
            second.draw();
        }

    }

    private void reset(){
        first.reset();
        second.reset();
    }

    private Player getPlayerFromSymbol(Symbol s){

        return first.getSymbol().equals(s) ? first : second;

    }

    private void update(Pair<Integer, Integer> pos) {
        int i = pos.getKey(); //line
        int j = pos.getValue(); //column

        Symbol current = grid.peek(i, j);
        line.increment(current, i);
        col.increment(current, j);
        if(i == j)
            diag.increment(current, 0);
        if(grid.squareSize() - 1 - i == j)
            diag.increment(current, 1);
        nb++;
    }

    private void display() {
        int n = (int) Math.sqrt(grid.size());

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(grid.peek(i, j).getChar() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    private void winner(){

    }

    private boolean isFinnish() {
        int n = grid.squareSize();

        if(nb == grid.size())
            return true;
        for (int i = 0; i < n; i++) {
            if(col.getCount(i) == n)
                return true;
            if(line.getCount(i) == n)
                return true;
            if(i < 2){
                if(diag.getCount(i) == n)
                    return true;
            }
        }

        return false;
    }
}
