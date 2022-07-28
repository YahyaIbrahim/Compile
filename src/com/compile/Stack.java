package com.compile;
import java.util.ArrayList;
public class Stack
{    
    private ArrayList<Node> data;
    
    public Stack(){
        data = new ArrayList<>();
    }
    
    public void push(Node value){
        data.add(value);
    }
    
    public Node pop(){
        Node value = data.get(data.size() - 1);
        data.remove(data.size() - 1);
        return value;
    }
    
    public int size(){
        return data.size();
    }
}
