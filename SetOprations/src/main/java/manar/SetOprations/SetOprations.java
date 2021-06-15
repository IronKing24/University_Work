package manar.SetOprations;

import java.util.*;
import textio.TextIO;

/**
 * <equation> ::= <operand> <oprator> <operand>
 * <operand>::= <equation> | <set>
 * <set>::= Array-like structure
 */
public class SetOprations {

    static final Character [] oprators = {'+','-','*'};

    public static void main(String[] args) {

    System.out.println("\n\nEnter an expression");
    TextIO.skipBlanks();
            try{
                TextIO.skipBlanks();
                TreeSet<Integer> tree = new TreeSet<Integer>();
                System.out.println(tree.root.value());
                TextIO.skipBlanks();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

class TreeSet<T> extends OperatorNode<T>{
    public OperatorNode<T> root =  new OperatorNode<T>();
   
    TreeSet(){
        while(TextIO.peek() != '\n'){
        BaseNode<T> bNode = null;
        if(TextIO.peek() == '['){  
            try{
                bNode = new ValueNode<T>();
            }
            catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
            if(root.leftOperand == null){
                root.leftOperand = bNode;
            }
            else{
                root.rightOperand = bNode;
            }
        }
        
        if (Arrays.asList( SetOprations.oprators).contains(TextIO.peek()) && root.value == 0){
            root.value = TextIO.getChar();
        }
        else if(Arrays.asList( SetOprations.oprators).contains(TextIO.peek())){
            TreeSet<T> op =  new TreeSet<T>();
            op.root.value = TextIO.getChar();
            op.leftOperand = root;
            root = op; 
        }
    }
    }
    ArrayList<T> value(){
        return root.value();
    }
}

abstract class BaseNode<T>{
    abstract ArrayList<T> value();
}

class ValueNode<T> extends BaseNode<T>{
    ArrayList<T> value = new ArrayList<T>();

    ValueNode() throws IllegalArgumentException{
        if(TextIO.peek() == '['){
            TextIO.getAnyChar();

            while(TextIO.peek() != ']'){
            	StringBuilder holder = new StringBuilder();
                while(true){
                    if(TextIO.peek()==','){
                        TextIO.getChar();
                        break;
                    }
                    else if(TextIO.peek()==']'){
                        break;
                    }
                    else if(Character.isDigit(TextIO.peek())){
                        holder.append(TextIO.getChar());                       
                    }
                    else throw new IllegalArgumentException("sets can be numbers only");    
                }
                value.add((T)holder.toString());
            }
           
            if(TextIO.peek() == '\n' || Arrays.asList( SetOprations.oprators).contains(TextIO.peek())){
                throw new IllegalArgumentException("sets needs to be closed after they are opened");     
            }
            TextIO.getAnyChar();
        }
    }
    
    ArrayList<T> value(){
        return value;
    }
}

class OperatorNode<T> extends BaseNode<T>{
    public BaseNode<T> rightOperand;
    public BaseNode<T> leftOperand;
    public char value = 0;

    ArrayList<T> value(){
        switch(value){
            case '+':
            return  union(leftOperand.value(), rightOperand.value());

            case '*':
            return intersection(leftOperand.value(), rightOperand.value());

            case '-':
            return difference(leftOperand.value(), rightOperand.value());

            default: return null;
        }
    }

    ArrayList<T> union (ArrayList<T> a, ArrayList<T> b){
        ArrayList<T> holder = new  ArrayList<T>(); 
        holder.addAll(a);
        holder.addAll(b);
        return holder;
    }

    ArrayList<T> intersection (ArrayList<T> a, ArrayList<T> b){
        ArrayList<T> holder = new  ArrayList<T>();
        for (T object : a) {
            if(b.contains(object)){
                holder.add(object);
            }
        }
        return holder;
    }

    ArrayList<T> difference (ArrayList<T> a, ArrayList<T> b){
        ArrayList<T> holder = new  ArrayList<T>();
        
        for (T object : a) {
            if(!b.contains(object)){
                holder.add(object);
            }
        }
        return holder;
    }
}