package com.compile;
import java.util.ArrayList;
public class Node 
{
        
        private int type;
        private int depth;
        private Token token;
        private Node parent;
        private ArrayList<Node> children;
        
        public Node(int type){
            this.type = type;
            depth = 0;
            token = null;
            parent = null;
            children = new ArrayList<Node>();
        }
        
        public int getType(){
            return type;
        }
        
        public void setToken(Token token){
            this.token = token;
        }
        
        public void setParent(Node parent){
            this.parent = parent;
            this.depth = parent.depth + 1;
        }
        
        public void addChild(Node child){
            child.setParent(this);
            children.add(child);
        }
        
        public ArrayList<Node> getChildren(){
            return children;
        }
        
        
        public boolean isTerminal(){
            return Token.isTerminal(type);
        }
        
        @Override 
        public String toString(){
            String str = "";
            
            // Indentation
            for(int i = 0; i < depth; i++){
                str += "..";
            }
            
            // Layer number and symbol name
            str += "@ Layer " + depth + ": " + Token.getTokenName(type) + "\n";
            
            // String representation of children
            for(Node child : children){
                str += child.toString();
            }
            
            return str;
        }
    }
