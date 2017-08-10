package Model.Player;

import Model.Symbol.Empty;
import Model.Symbol.Symbol;
import Model.Symbol.SymbolFactory;
import javafx.util.Pair;

import java.util.*;

public class QLearner extends Player {

    private double epsilon;
    private double alpha;
    private double gamma;
    private boolean optimal;
    private boolean win;
    private boolean draw;
    private boolean lost;
    private Random rg;

    private int nbWon;
    private int nbDraw;
    private int nbLost;
    private HashMap<String, HashMap<Pair<Integer, Integer>, Double>> qtable;

    private QLearner(Symbol symbol) {
        super(symbol);
    }

    public QLearner(Symbol symbol, double epsilon, double alpha, double gamma) {
        super(symbol);
        this.epsilon = epsilon;
        this.alpha = alpha;
        this.gamma = gamma;
        this.win = false;
        this.draw = false;
        rg = new Random();

        qtable = new HashMap<>();
    }

    @Override
    public Pair<Integer, Integer> obtainPosition() {
        return epsilonGreedy();
    }

    private Pair<Integer, Integer> epsilonGreedy() {

        List<Pair<Integer, Integer>> moves = getMoves();
        HashMap<Pair<Integer, Integer>,Double> qValues = getQValues(moves);

        if(!optimal){
            //Explore
            double r = rg.nextDouble();
            if( r < epsilon){
                return exploreMove(moves);
            }
            else {
                return greedyMove(qValues);
            }
        }

        return greedyMove(qValues);

    }

    private Pair<Integer,Integer> greedyMove(HashMap<Pair<Integer, Integer>, Double> moves) {

        
        Comparator<Map.Entry<Pair<Integer, Integer>, Double>> comp = Comparator.comparing(Map.Entry::getValue);


        Optional<Map.Entry<Pair<Integer, Integer>, Double>> result =
        moves.entrySet().stream().max(comp);

        if(result.isPresent())
            return result.get().getKey();
        else {
            System.err.println("Maximum in greedyMove not found");
            return new Pair<>(0, 0);
        }
    }


    private Pair<Integer, Integer> exploreMove(List<Pair<Integer, Integer>> moves) {

        int i = rg.nextInt(moves.size());
        return moves.get(i);
    }


    private HashMap<Pair<Integer, Integer>, Double> getQValues(List<Pair<Integer, Integer>> moves){
        HashMap<Pair<Integer, Integer>, Double> qValues = new HashMap<>();
        String state = stateToString();

        HashMap<Pair<Integer, Integer>, Double> actions = qtable.get(state);

        //Skimming original map to have only relevant moves
        for(Pair<Integer, Integer> move : moves){
            double value = actions.get(move);
            qValues.put(move, value);
        }


        return qValues;


    }
    @Override
    public void feedback(Pair<Integer, Integer> position, Pair<Integer, Integer> position2, Symbol symbol1) {
        double reward = getReward();
        double thisQ, maxQ, newQ;


        // TODO: 10/08/2017 Change previous state to two positions ago........
        grid.place(position.getKey(), position.getValue(), SymbolFactory.create(Empty::new));
        grid.place(position2.getKey(), position2.getValue(), SymbolFactory.create(Empty::new));
        String state = stateToString();
        grid.place(position.getKey(), position.getValue(), symbol); //Placing back everything
        grid.place(position2.getKey(), position2.getValue(), symbol1);
        

        thisQ = qtable.get(state).get(position);
        maxQ = maxQValues();
          //  System.out.println(maxQ);

        newQ = thisQ + alpha * (reward + gamma * maxQ - thisQ);
        //System.out.println(newQ);
        qtable.get(state).put(position, newQ);



    }

    @Override
    public void hasWon() {
        win = true;
        nbWon++;
    }

    @Override
    public void draw() {
        draw = true;
        nbDraw++;
    }

    @Override
    public void lost() {
        lost = true;
        nbLost++;
    }

    @Override
    public void reset() {
        win = false;
        draw = false;
        lost = false;
    }

    private double maxQValues() {
        String state = stateToString();
       // double max = Double.POSITIVE_INFINITY;


        HashMap<Pair<Integer, Integer>, Double> entries = qtable.get(state);

        Optional<Map.Entry<Pair<Integer, Integer>, Double>> result =
         entries.entrySet().stream()
                .max(Map.Entry.comparingByValue(Double::compareTo));

        if(result.isPresent())
            return result.get().getValue();

        System.err.println("Max not found weird tho");

        return 0;
    }

    private double getReward() {
        if(win)
            return 100;
        if (draw)
            return 10;
        if(lost)
            return -100;

        return 0;
    }

    public void setOptimal(boolean optimal) {
        this.optimal = optimal;
    }

    public String stateToString(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < grid.squareSize(); i++) {
            for (int j = 0; j < grid.squareSize(); j++) {
                if(grid.peek(i, j).getChar() == this.symbol.getChar()){
                    sb.append("1");
                } else if (grid.peek(i, j).getChar() == SymbolFactory.create(Empty::new).getChar()){
                    sb.append("0");
                } else {
                    sb.append("2");
                }
            }
        }

        if(!qtable.containsKey(sb.toString())){
            HashMap<Pair<Integer, Integer>, Double> vals = new HashMap<>();
            for(Pair<Integer, Integer> pos : getAllMoves()){
                vals.put(pos, 0d);
            }
            qtable.put(sb.toString(), vals);
        }

        return sb.toString();
    }

    private List<Pair<Integer, Integer>> getAllMoves() {
        ArrayList<Pair<Integer,Integer>> positions = new ArrayList<>();
        for (int i = 0; i < grid.squareSize(); i++) {
            for (int j = 0; j < grid.squareSize(); j++) {
                    positions.add(new Pair<>(i, j));
            }
        }
        return positions;
    }

    private List<Pair<Integer, Integer>> getMoves() {
        ArrayList<Pair<Integer,Integer>> positions = new ArrayList<>();
        for (int i = 0; i < grid.squareSize(); i++) {
            for (int j = 0; j < grid.squareSize(); j++) {
                if(grid.peek(i, j) instanceof Empty){
                    positions.add(new Pair<>(i, j));
                }
            }
        }
        return positions;
    }

    public void print(){
        System.out.println("States seen : " + qtable.size());
        System.out.println("Won games : " + nbWon);

        double pr = ((double) nbLost + nbDraw + nbWon);
        double won = nbWon / pr;
        double lost = nbLost / pr;
        double draw = nbDraw / pr;
        won *=100;
        lost *= 100;
        draw *= 100;


        System.out.println("Won games : " + won + "%" );
        System.out.println("Draw games : " + lost + "%");
        System.out.println("Lost games : " + draw + "%");


    }
}
