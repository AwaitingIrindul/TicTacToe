import Model.Game;
import Model.Player.Human;
import Model.Player.Player;
import Model.Player.QLearner;
import Model.Player.RandomPlayer;
import Model.Symbol.Circle;
import Model.Symbol.Cross;
import Model.Symbol.SymbolFactory;

public class Main {

    public static void main(String[] args) {

        Player first = new QLearner(SymbolFactory.create(Circle::new), 0.80, 0.80, 0.99);
       // Player second = new QLearner(SymbolFactory.create(Cross::new), 0.70, 0.80, 0.90);
        Player second = new QLearner(SymbolFactory.create(Cross::new), 0.80, 0.80, 0.99);
        System.out.println("Training...");
        for (int i = 0; i < 500000; i++) {

            //Switching players each time

            Game tic = new Game(i%2 == 0 ? first : second, i%2 == 0 ? second : first, false);
            tic.play();
        }

        System.out.println("Your turn");
        Player third = new Human(SymbolFactory.create(Cross::new));
        ((QLearner) first).setOptimal(true);
        ((QLearner) first).print();
        for (int i = 0; i < 6; i++) {
            Game tic = new Game(i%2 == 0 ? first : third, i%2 == 0 ? third : first, true);
            tic.play();
        }



    }
}
