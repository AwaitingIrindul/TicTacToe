package Model.Symbol;

public class SymbolCounter {

    private Symbol symbols[];
    private int count[];

    public SymbolCounter(int n) {
        count = new int[n];
        symbols = new Symbol[n];

    }

    public void increment(Symbol symbol, int i){
        if(count[i] == 0){
            this.setSymbol(symbol, i);
            count[i]++;
        } else {
            if(getSymbol(i).getChar() == symbol.getChar() )
                count[i]++;
        }

    }

    private void setSymbol(Symbol symbol, int i) {
        this.symbols[i] = symbol;
    }

    public Symbol getSymbol(int i) {
        if(symbols[i] == null)
            return SymbolFactory.create(Empty::new);
        return symbols[i];
    }

    public int getCount(int i) {
        return count[i];
    }
}
