package com.compile;

public class DFA
{
    
    public static final int INVALID_STATE = -1;
    
    private static final int TABLE_WIDTH = 256;
    
    private int numOfStates;
    private int[][] table;
    private int[] outputs;
    
    
    public DFA(int numOfStates){
        
        // Allocate table, and array of outputs
        this.numOfStates = numOfStates;
        this.table = new int[numOfStates][TABLE_WIDTH];
        this.outputs = new int[numOfStates];
        
        // By default, all transitions are invalid
        for(int i = 0; i < numOfStates; i++){
            for(int j = 0; j < TABLE_WIDTH; j++){
                this.table[i][j] = INVALID_STATE;
            }
        }
        
        // By default, all states have output = INVALID_TOKEN
        for(int i = 0; i < numOfStates; i++){
            this.outputs[i] = Token.INVALID_TOKEN;
        }
    }
    
    public void addTransition(int stateFrom, char symbol, int stateTo){
        this.table[stateFrom][symbol] = stateTo;
    }

    private static boolean isElementInArray(char x, char[] arr){
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == x){
                return true;
            }
        }
        return false;
    }
    
    public void addTransitionsForAllLettersExcept(int stateFrom, char[] exceptions, int stateTo){
        for(char c = 'a'; c <= 'z'; c++){
            if(isElementInArray(c, exceptions)){
                continue;
            }
            this.addTransition(stateFrom, c, stateTo);
        }
        for(char c = 'A'; c <= 'Z'; c++){
            if(isElementInArray(c, exceptions)){
                continue;
            }
            this.addTransition(stateFrom, c, stateTo);
        }
    }
    
    public void addTransitionsForAllDigits(int stateFrom, int stateTo){
        for(char c = '0'; c <= '9'; c++){
            this.addTransition(stateFrom, c, stateTo);
        }
    }
    
    public void addTransitionsForWhitespace(int stateFrom, int stateTo){
        this.addTransition(stateFrom,' ', stateTo);
        this.addTransition(stateFrom,'\t', stateTo);
        this.addTransition(stateFrom,'\r', stateTo);
        this.addTransition(stateFrom,'\n', stateTo);
    }
    
    public int getNextState(int stateFrom, char symbol){
        return this.table[stateFrom][symbol];
    }
    
    public void setOutput(int state, int output){
       this.outputs[state] = output;
    }
    
    public int getOutput(int state){
        return this.outputs[state];
    }
    
    public boolean isFinalState(int state){
        return (this.outputs[state] != Token.INVALID_TOKEN);
    }
    
    public void print(){
        for(int i = 0; i < this.numOfStates; i++){
            System.out.println("----- state " + i + "-----");
            for(int j = 0; j < TABLE_WIDTH; j++){
                if(this.table[i][j] == INVALID_STATE){
                    continue;
                }
                String c = String.valueOf((char)j);
                if(c.equals(" ")){
                    c = "\\s";
                }
                if(c.equals("\t")){
                    c = "\\t";
                }
                if(c.equals("\r")){
                    c = "\\r";
                }
                if(c.equals("\n")){
                    c = "\\n";
                }
                System.out.println("("+i+", "+c+") --> " + this.table[i][j]);
                
            }
        }
    }
    
    public void printOutputs(){
        for(int i = 0; i < this.numOfStates; i++){
            System.out.println("output of " + i + " = " + this.outputs[i]);
        }
    }
    
}