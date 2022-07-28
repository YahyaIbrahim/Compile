package com.compile;

import java.util.HashMap;

public class LL1 {
    
    private int[][][] table;
    
    public LL1(){
        initTable();
        build();
    }
    
    private void initTable(){
        /**
         * Initialize all table entries to empty rules (empty arrays)
         */
        table = new int[Token.NUM_OF_NON_TERMINALS][Token.NUM_OF_TERMINALS][];
        for(int row = 0; row < table.length; row++){
            for(int col = 0; col < table[row].length; col++){
                table[row][col] = new int[]{};
            }
        }
    }
    
    private void build(){
        /**
         * Build LL1 parsing table (add production rules)
         */
        addProductionRule(Token.S, Token.ID, new int[]{Token.ID , Token.equal, Token.E, Token.semicolon, Token.A});
        addProductionRule(Token.S, Token.IF_KW, new int[]{Token.IF_KW, Token.D, Token.Then_KW, Token.S});
        addProductionRule(Token.A, Token.ID, new int[]{Token.ID , Token.equal, Token.E, Token.semicolon, Token.A});
        addProductionRule(Token.A, Token.dollar, new int[]{Token.epsilon});
        addProductionRule(Token.D, Token.ID, new int[]{Token.E, Token.B, Token.E});
        addProductionRule(Token.D, Token.left, new int[]{Token.E, Token.B, Token.E});
        addProductionRule(Token.D, Token.NUM, new int[]{Token.E, Token.B, Token.E});
        addProductionRule(Token.B, Token.compare, new int[]{Token.compare});
        addProductionRule(Token.E, Token.ID, new int[]{Token.M, Token.L});
        addProductionRule(Token.E, Token.left, new int[]{Token.M, Token.L});
        addProductionRule(Token.E, Token.NUM, new int[]{Token.M, Token.L});
        addProductionRule(Token.L, Token.Then_KW, new int[]{Token.epsilon});
        addProductionRule(Token.L, Token.right, new int[]{Token.epsilon});
        addProductionRule(Token.L, Token.add, new int[]{Token.add ,Token.M ,Token.L});
        addProductionRule(Token.L, Token.semicolon, new int[]{Token.epsilon});
        addProductionRule(Token.L, Token.compare, new int[]{Token.epsilon});
        addProductionRule(Token.M, Token.ID, new int[]{Token.F, Token.H});
        addProductionRule(Token.M, Token.left, new int[]{Token.F, Token.H});
        addProductionRule(Token.M, Token.NUM, new int[]{Token.F, Token.H});
        addProductionRule(Token.H, Token.Then_KW, new int[]{Token.epsilon});
        addProductionRule(Token.H, Token.right, new int[]{Token.epsilon});
        addProductionRule(Token.H, Token.add, new int[]{Token.epsilon});
        addProductionRule(Token.H, Token.mul, new int[]{Token.mul , Token.F , Token.H });
        addProductionRule(Token.H, Token.semicolon, new int[]{Token.epsilon });
        addProductionRule(Token.H, Token.compare, new int[]{Token.epsilon });
        addProductionRule(Token.F, Token.ID, new int[]{Token.ID});
        addProductionRule(Token.F, Token.left, new int[]{Token.left,Token.E,Token.right});
        addProductionRule(Token.F, Token.NUM, new int[]{Token.NUM });
    }
    
    private void addProductionRule(int nonTerminal, int terminal, int[] rule){
        /**
         * Add the given production rule to the LL1 parsing table
         */
        int row = nonTerminal - Token.NONTERINAL_OFFSET_VALUE;
        int col = terminal;
        table[row][col] = rule;
    }
    
    public void display(){
        /**
         * Display the LL1 parsing table (for debugging)
         */
        System.out.println("Displaying LL1 parsing table:");
        for(int row = 0; row < table.length; row++){
            for(int col = 0; col < table[row].length; col++){
                int nonTerminal = row + Token.NONTERINAL_OFFSET_VALUE;
                int terminal = col;
                String nonTerminalStr = Token.getTokenName(nonTerminal);
                String terminalStr = Token.getTokenName(terminal);
                System.out.print("("+nonTerminalStr+", "+terminalStr+") -> ");
                for(int k = 0; k < table[row][col].length; k++){
                    int symbol = table[row][col][k];
                    String symbolStr = Token.getTokenName(symbol);
                    System.out.print(symbolStr + " ");
                }
                System.out.println("");
            }
        }
        System.out.println("End of LL1 parsing table");
    }
    
    public int[] getProductionRule(int nonTerminal, int terminal){
        /**
         * Return the production rule at the given index
         */
        int row = nonTerminal - Token.NONTERINAL_OFFSET_VALUE;
        int col = terminal;
        return table[row][col];
    }
}
