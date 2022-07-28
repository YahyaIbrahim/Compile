package com.compile;
public class Tree
{
    private Node root;
    
    public Tree(){
        root = null;
    }
    
    public void setRoot(Node newRoot){
        root = newRoot;
    }
    
    public Node getRoot(){
        return root;
    }
    
    public void addNode(Node parent, Node child){
        parent.addChild(child);
    }
    
    @Override
    public String toString(){
        if(root == null){
            return "Tree is empty";
        }
        return root.toString();
    }
    
}
