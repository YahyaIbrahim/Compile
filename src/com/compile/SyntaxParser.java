package com.compile;
import java.util.ArrayList;
public class SyntaxParser
{
    private LL1 myLL1Table;
    
    public SyntaxParser(LL1 myLL1Table){
        this.myLL1Table = myLL1Table;
    }
    
    public Tree parse(ArrayList<Token> inputTokens){
        
        // add $ to end of input
        inputTokens.add(new Token(Token.dollar, "", -1, -1));
        
        // create and initialize syntax tree
        Tree tree = new Tree();
        Node startingSymbolNode = new Node(Token.STARTING_SYMBOL);
        tree.setRoot(startingSymbolNode);
        
        // create and initialize stack
        Stack stack = new Stack();
        stack.push(new Node(Token.dollar));
        stack.push(startingSymbolNode);
        
        // main loop: repeat until stack becomes empty
        boolean hasError = false;
        System.out.println("Syntax analysis started...");
        while(stack.size() > 0){
            
            // pop and read next element in stack
            Node stackNode = stack.pop();
            int stackNodeType = stackNode.getType();
            
            // read next element in input (without removing it)
            Token token = inputTokens.get(0);
            int tokenType = token.getType();
            
            // if stack symbol is a non-terminal: check LL1 table for production
            if(!Token.isTerminal(stackNodeType)){
                
                // Get production rule from LL1 table
                int[] productionRule = myLL1Table.getProductionRule(stackNodeType, tokenType);
                
                // If no such a production, it's an error
                if(productionRule.length == 0){
                    String errorLocation = " in line " + token.getRow() + " at position " + token.getPosition();
                    String unexpected =  "<" + Token.getTokenName(tokenType) + "> token '" + token.getLexeme() + "'";
                    if(tokenType == Token.dollar){
                        errorLocation = "";
                        unexpected = "end of code";
                    }
                    System.out.println("Error in code" + errorLocation + ": Unexpected " + unexpected);
                    hasError = true;
                    break;
                }
                
                // Create nodes for this production rule
                ArrayList<Node> tempNodeList = new ArrayList<>();
                for(int element: productionRule){
                    tempNodeList.add(new Node(element));
                }
                
                // Add nodes to tree
                for(Node node : tempNodeList){
                    tree.addNode(stackNode, node);
                }
                
                // Push nodes to stack (in reverse order)
                for(int i = tempNodeList.size() - 1; i >= 0; i--){
                    Node node = tempNodeList.get(i);
                    if(node.getType() != Token.epsilon){ // don't push epsilon
                        stack.push(node);
                    }
                }
            } 
            
            // if stack symbol is a terminal: match it with input token
            else {
                // if it's a match: link token to node, and remove token from input
                if(stackNodeType == tokenType){
                    stackNode.setToken(token);
                    inputTokens.remove(0);
                }
                // if it's not a match: it's an error
                else {
                    String errorLocation = " in line " + token.getRow() + " at position " + token.getPosition();
                    String unexpected = "<" + Token.getTokenName(tokenType) + "> token '" + token.getLexeme() + "'";
                    if(tokenType == Token.dollar){
                        errorLocation = "";
                        unexpected = "end of code";
                    }
                    String expectation = "<" + Token.getTokenName(stackNodeType) + "> token";
                    if(stackNodeType == Token.dollar){
                        expectation = "end of code";
                    }
                    System.out.println("Error in code" + errorLocation + ": Unexpected " + unexpected + ", expected " + expectation + " instead.");
                    hasError = true;
                    break;
                }
            }
        }
        
        if(!hasError){
            System.out.println("Syntax analysis has successfully finished");
        } else {
            // return empty tree if there's an error
            tree = new Tree();
        }
        
        return tree;
    }
}

