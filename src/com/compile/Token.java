package com.compile;

public class Token {
    
    //////////////////// Static part //////////////////////////
    
    public static final int INVALID_TOKEN = -1;
    public static final int SPACE = 0;
    public static final int ID = 1;
    public static final int IF_KW = 2;
    public static final int Then_KW = 3;
    public static final int int_KW = 4;
    public static final int else_KW = 5;
    public static final int while_KW = 6;
    public static final int do_KW= 7;
    public static final int print_KW =8;
    public static final int return_KW =9;
    public static final int NUM = 10;
    public static final int add = 11;
    public static final int sub = 12;
    public static final int mul = 13 ;
    public static final int div = 14;
    public static final int equal = 15;
    public static final int compare = 16;
    public static final int less = 17;
    public static final int greater = 18;
    public static final int less_than = 19;
    public static final int greater_than=20;
    public static final int not_equal = 21;
    public static final int left_bracket = 22;
    public static final int semicolon = 23;
    public static final int left = 24;
    public static final int right = 25;
    public static final int right_bracket =26;
    public static final int digit=27;
    
     // terminals (tokens)
    //public static final int a = 28;
    //public static final int b = 29;
    //public static final int space = 28;
    public static final int dollar = 28;
    public static final int epsilon = 29;
    public static final int NUM_OF_TERMINALS = 30;
    
    // non-terminals (in grammar)
    public static final int NONTERINAL_OFFSET_VALUE = 1001;
    public static final int S = 1001;
    public static final int A = 1002;
    public static final int B = 1003;
    public static final int E = 1004;
    public static final int D = 1005;
    public static final int M = 1006;
    public static final int L = 1007;
    public static final int F = 1008;
    public static final int H = 1009;
    public static final int NUM_OF_NON_TERMINALS = 9;
    public static final int STARTING_SYMBOL = S;
    
    
    public static boolean isTerminal(int terminalOrNonTerminalValue){
        return (terminalOrNonTerminalValue < NONTERINAL_OFFSET_VALUE);
    }

    
    public static String getTokenName(int tokenType){
        switch(tokenType){
            case SPACE:
                return "SPACE";
            case digit:
                return "digit";
            case ID:
                return "ID";
            case IF_KW:
                return "IF_KW";
                
            case Then_KW:
                return "Then_KW";
            case int_KW:
                 return "int_KW";
            case else_KW:
                 return "else_KW";     
            case  while_KW:
                 return "while_KW";
            case do_KW:
                return "do_KW";    
            case print_KW:
                 return "print_KW";   
            case return_KW:
                 return "return_KW";
            case add:
                return "add";
            case sub:
                return "sub";
            case mul:
                return "mul";
            case div:
                return "div";
            case equal:
                return "equal";
            case compare:
                return "==";
            case less:
                return "less";
            case greater:
                 return "greater";
            case less_than:
                return "less_than";
            case greater_than:
                return "greater_than";
            case not_equal:
                return "not_equal";
            case left_bracket:
                return "left_bracket";
            case semicolon:
                return "semicolon";
            case left:
                return "left";
            case right:
                return "right";
            case right_bracket:
                return "right_bracket";
            case NUM:
                return "NUM";
                // terminals
//            case a:
//                return "a";
//            case b:
//                return "b";
//            case space:
//                return "SPACE";
            case dollar:
                return "$";
            case epsilon:
                return "epsilon";
            
            // non-terminals
            case S:
                return "S";
            case A:
                return "A";
            case B:
                return "B";
            case E:
                return "E";
            case D:
                return "D";
            case M:
                return "M";
            case L:
                return "L";
            case F:
                return "F";
            case H:
                return "H";
            default:
                return "INVALID_TOKEN";
        }
    }
    
    
    ///////////////// Non-static part ///////////////////////
    
    private int type;
    private String lexeme;
    private int row;
    private int position;
     
    // constrctor 
    public Token(int type, String lexeme, int row, int position){
        this.type = type;
        this.lexeme = lexeme;
        this.row = row;
        this.position = position;
    }
     public int getType(){
        return type;
    }
    
    public String getLexeme(){
        return lexeme;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getPosition(){
        return position;
    }
    @Override
    public String toString(){
        return String.format("%-20s%-50s%-9d%-9d", getTokenName(this.type), this.lexeme, this.row, this.position);
    }
}