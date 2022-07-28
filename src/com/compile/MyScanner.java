package com.compile;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class MyScanner 
{
    private static int CHUNK_SIZE = 1024;   
    private DFA dfa;
    private int currentState;
    
    public MyScanner(){
        this.build();
        this.reset();
    }
    
    private void build(){
        
        int numOfStates = 53;
        this.dfa = new DFA(numOfStates);
        
        //////////////// Transitions /////////////
        // if
        this.dfa.addTransition(0, 'i', 1);
        this.dfa.addTransition(1, 'f', 24);
       
        // int
        this.dfa.addTransition(1, 'n', 25);
        this.dfa.addTransition(25, 't', 38);
        
        // then
        this.dfa.addTransition(0,'t', 2);
        this.dfa.addTransition(2, 'h', 26);
        this.dfa.addTransition(26, 'e', 39);
        this.dfa.addTransition(39, 'n', 44);
        
        // while
        this.dfa.addTransition(0, 'w', 3);
        this.dfa.addTransition(3, 'h', 27);
        this.dfa.addTransition(27, 'i', 40);
        this.dfa.addTransition(40, 'l', 45);
        this.dfa.addTransition(45, 'e', 49);
        
      
        // do
        this.dfa.addTransition(0,'d',4);
        this.dfa.addTransition(4,'o',28);
    
        // return
        this.dfa.addTransition(0,'r',5);
        this.dfa.addTransition(5,'e',29);
        this.dfa.addTransition(29,'t',41);
        this.dfa.addTransition(41,'u',46);
        this.dfa.addTransition(46,'r',50);
        this.dfa.addTransition(50,'n',52);
        
        
        // else       
        this.dfa.addTransition(0,'e',6);
        this.dfa.addTransition(6,'l',30);
        this.dfa.addTransition(30,'s',42 );
        this.dfa.addTransition(42,'e',47);
        
        // print
        this.dfa.addTransition(0,'p',7);
        this.dfa.addTransition(7,'r',31);
        this.dfa.addTransition(31,'i',43);
        this.dfa.addTransition(43,'n',48);
        this.dfa.addTransition(48,'t',51);
        // digit and number
        this.dfa.addTransitionsForAllDigits(0, 8);
        this.dfa.addTransitionsForAllDigits(8, 32);
        this.dfa.addTransitionsForAllDigits(32, 32);
        
        // identifier
        this.dfa.addTransitionsForAllLettersExcept(0, new char[]{'i', 't', 'w', 'd', 'r', 'e', 'p'}, 9);
        this.dfa.addTransitionsForAllDigits(9, 33);
        this.dfa.addTransitionsForAllDigits(33, 33);
        for(int s = 1; s <= 7; s++){
            this.dfa.addTransitionsForAllDigits(s, 9);
        }
        
        
        // +
        this.dfa.addTransition(0,'+',10);
        
        // -
        this.dfa.addTransition(0,'-',11);
        
         // *
        this.dfa.addTransition(0,'*',12);
        
        // /
        this.dfa.addTransition(0,'/',13);
        
        // punctuation
        this.dfa.addTransition(0,'(',14);
        this.dfa.addTransition(0,')',15);
        this.dfa.addTransition(0,';',16);
        this.dfa.addTransition(0,'=',17);
        this.dfa.addTransition(17,'=',34);
        this.dfa.addTransition(18,'=',35);
        this.dfa.addTransition(19,'=',36);
        this.dfa.addTransition(0,'>',18);
        this.dfa.addTransition(0,'<',19);
        // state 14
        this.dfa.addTransition(0,'!', 20);
        this.dfa.addTransition(20,'=', 37);
        
        // state 15
        this.dfa.addTransition(0,'{', 21);
        this.dfa.addTransition(0,'}', 22);
        this.dfa.addTransitionsForWhitespace(0, 23);
        
        //////////////// Outputs /////////////////
        for(int s = 1; s <= 7; s++){
            this.dfa.setOutput(s, Token.ID);
        }
        this.dfa.setOutput(24, Token.IF_KW);
        this.dfa.setOutput(38, Token.int_KW);
        this.dfa.setOutput(44,Token.Then_KW);
        this.dfa.setOutput(49, Token.while_KW);
        this.dfa.setOutput(28, Token.do_KW);
        this.dfa.setOutput(52, Token.return_KW);
        this.dfa.setOutput(47, Token.else_KW);
        this.dfa.setOutput(51, Token.print_KW);
        this.dfa.setOutput(8, Token.NUM);
        this.dfa.setOutput(32, Token.NUM);
        this.dfa.setOutput(9, Token.ID);
        this.dfa.setOutput(33, Token.ID);
        this.dfa.setOutput(10, Token.add);
        this.dfa.setOutput(11, Token.sub);
        this.dfa.setOutput(12, Token.mul);
        this.dfa.setOutput(13, Token.div);
        this.dfa.setOutput(14, Token.left);
        this.dfa.setOutput(15, Token.right);
        this.dfa.setOutput(16, Token.semicolon);
        this.dfa.setOutput(17, Token.equal);
        this.dfa.setOutput(34, Token.compare);
        this.dfa.setOutput(35, Token.greater_than);
        this.dfa.setOutput(36, Token.less_than);
        this.dfa.setOutput(18, Token.greater);
        this.dfa.setOutput(19, Token.less);
        this.dfa.setOutput(37, Token.not_equal);
        this.dfa.setOutput(21, Token.right_bracket);
        this.dfa.setOutput(22, Token.left_bracket);
        this.dfa.setOutput(23, Token.SPACE);
        this.dfa.print();
    }
    
    private boolean handleSymbol(char symbol){
        int nextState = this.dfa.getNextState(this.currentState, symbol);
        if(nextState == DFA.INVALID_STATE){
            return false;
        } else {
            this.currentState = nextState;
            return true;
        }
    }
    
    private void reset(){
        this.currentState = 0;
    }
    
    public ArrayList<Token> tokenize(String filename){
        
        // Prepare for tokenization
        ArrayList<Token> tokens = new ArrayList<>();
        String currentLexeme = ""; // collect thr letter lexeme 
        int lineCounter = 1;  
        int pos = 0;  
        int pos_left = 1; 
        boolean exitWithError = false;
        this.reset();
        
        // Read from file
        File file = new File(filename);
        try {
            // Read a chunk from file
            FileInputStream inputStream = new FileInputStream(file);
            byte[] chunk = new byte[CHUNK_SIZE];
            int chunkLen = 0;
            while ((chunkLen = inputStream.read(chunk)) != -1) {
                // Store the chunk in inputString
                chunk = Arrays.copyOfRange(chunk, 0, chunkLen);
                String inputString = new String(chunk, "UTF-8");
                
                // Tokenize input string (don't worry if a token spans two chunks)
                for(int i = 0; i < inputString.length(); i++){
            
                    // get current symbol
                    char c = inputString.charAt(i);
                    pos++;

                    // ignore and count new lines
                    if(c == '\n'){
                        lineCounter++;
                        pos = 0;
                    }

                    // handle current symbol
                    boolean noError = this.handleSymbol(c);
                    

                    // If No error: Accumulate token lexeme
                    if(noError == true){
                        currentLexeme += c;
                    } 

                    // If error
                    else {
                        // If current state is final, add new token (not including c)
                        if(this.dfa.isFinalState(this.currentState)){

                            // rollback newline counter
                            if(c == '\n'){
                                lineCounter--;
                            }

                            // Add token (if not space)
                            int tokenType = this.dfa.getOutput(this.currentState);
                            if(tokenType != Token.SPACE){
                                Token token = new Token(tokenType, currentLexeme, lineCounter, pos_left);
                                tokens.add(token);
                            }
                            currentLexeme = "";
                            this.currentState = 0;
                            

                            // read c in next iteration
                            i--;
                            pos_left = pos;
                            pos--;
                        } 

                        // If current state is not final, print error message and stop
                        else {
                            System.out.println("Error at line " + lineCounter + " at position " + pos + ": Unexpected symbol '"+c+"'");
                            exitWithError = true;
                            break;
                        }
                    }
                }
                if(exitWithError){
                    break;
                }
            }
            // add last token
            if(!exitWithError && currentLexeme.length() > 0){
                int tokenType = this.dfa.getOutput(this.currentState);
                if(tokenType != Token.SPACE){
                    Token token = new Token(tokenType, currentLexeme, lineCounter, pos_left);
                    tokens.add(token);
                }
            }
        } catch (FileNotFoundException fnfE) {
            System.err.println("File not found: " + file.getAbsolutePath());
        } catch (IOException ioE) {
            System.err.println("IOExceotion: " + ioE);
        }
        
        return tokens;
    }    
}