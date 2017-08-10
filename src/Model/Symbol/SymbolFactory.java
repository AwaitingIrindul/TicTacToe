package Model.Symbol;

import Model.Symbol.Symbol;

import java.util.function.Supplier;

/**
 * Created by Irindul on 03/08/2017.
 */
public class SymbolFactory {


    public static Symbol create(Supplier<Symbol> supplier){
        return supplier.get();
    }

}
