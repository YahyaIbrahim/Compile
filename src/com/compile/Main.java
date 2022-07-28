package com.compile;
import java.util.ArrayList;
public class Main
{

    
    public static void main(String[] args)
    {
       // Lexical analysis
        MyScanner scanner = new MyScanner();
        ArrayList<Token> tokens = scanner.tokenize("read.txt");
        if(tokens.size() == 0){
            return;
        }
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println(String.format("%-20s%-50s%-9s%-9s", "TOKEN", "LEXEME", "ROW", "POSITION"));
        System.out.println("----------------------------------------------------------------------------------------");
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println(tokens.get(i));
        }
        System.out.println("----------------------------------------------------------------------------------------");
        
        // Syntax analysis
        LL1 myLL1Table = new LL1();
        SyntaxParser parser = new SyntaxParser(myLL1Table);
        Tree syntaxTree = parser.parse(tokens);
        System.out.println(syntaxTree);
    }
}