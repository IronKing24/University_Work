package manar.quadraticformulacalculator;

import java.awt.event.*;
import javax.swing.*;


class QuadraticFormulaCalculator {
    public static void main(String[] args) {
        new QuadraticFormula();    
    }

/**
 * Returns the larger of the two roots of the quadratic equation
 * A*x*x + B*x + C = 0, provided it has any roots.  If A == 0 or
 * if the discriminant, B*B - 4*A*C, is negative, then an exception
 * of type IllegalArgumentException is thrown.
 */
static public double root( double A, double B, double C )
throws IllegalArgumentException {
    if (A == 0) {
            throw new IllegalArgumentException("A can't be zero.");
        }
        else {
            double disc = B*B - 4*A*C;
            if (disc < 0)
                throw new IllegalArgumentException("Discriminant < zero.");
                return  (-B + Math.sqrt(disc)) / (2*A);
        }
    }
}

class QuadraticFormula extends JFrame implements ActionListener, FocusListener{

    JTextField textfield1,textfield2,textfield3;
    JLabel output;
    JButton yesbotton;
    JButton nobotton;
    JButton calculatebotton;
    static String outstring = "";
    double a;
    double b;
    double c;

    static String text1 = "Please enter the value of A, here";
    static String text2 = "Please enter the value of B, here";
    static String text3 = "Please enter the value of C, here";

    QuadraticFormula(){
        setTitle("Quadratic formula app");
        
        JLabel quadraticformulatext = new JLabel();
        quadraticformulatext.setText("value = (-b +- sqrt(b^2 - 4ac))/2a");
        quadraticformulatext.setBounds(50,10,700,30);
       
        textfield1 = new JTextField(text1);  
        textfield1.setBounds(50,50, 200,30);
        textfield1.addFocusListener(this);
       
        textfield2 = new JTextField(text2);  
        textfield2.setBounds(50,100, 200,30);
        textfield2.addFocusListener(this);
        
        textfield3 = new JTextField(text3);  
        textfield3.setBounds(50,150, 200,30);
        textfield3.addFocusListener(this);
       
        calculatebotton = new JButton("Calculate");
        calculatebotton.addActionListener(this); 
        calculatebotton.setBounds(50,200,100,50);
        
        yesbotton = new JButton("Yes");
        yesbotton.addActionListener(this); 
        yesbotton.setBounds(50,200,100,50);
        yesbotton.setVisible(false);

        nobotton = new JButton("No");
        nobotton.addActionListener(this); 
        nobotton.setBounds(200,200,100,50);
        nobotton.setVisible(false);

        output = new JLabel();
        output.setBounds(50,250,700,30);
        
        add(quadraticformulatext);
        add(textfield1); 
        add(textfield2);  
        add(textfield3); 
        add(calculatebotton);
        add(yesbotton);
        add(nobotton);
        add(output);
        setSize(400,400);  
        setLayout(null);  
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource().equals(calculatebotton)){
        try{
         a = Double.parseDouble(this.textfield1.getText());
         b = Double.parseDouble(this.textfield2.getText());
         c = Double.parseDouble(this.textfield3.getText());
        }
        catch(IllegalArgumentException i)
        {
            output.setText("Invalid input, please enter only numaric values");
        }

        try{
           output.setText("your result is: "+ String.valueOf(QuadraticFormulaCalculator.root(a,b,c))+" ,Would you like to do another calculation");
        }
        catch(IllegalArgumentException i){
            output.setText(i.getMessage());
        }

        this.textfield1.setEditable(false);
        this.textfield2.setEditable(false);
        this.textfield3.setEditable(false);
        this.calculatebotton.setVisible(false);
        this.yesbotton.setVisible(true);
        this.nobotton.setVisible(true);
    }
    else if(e.getSource().equals(yesbotton)){
        this.textfield1.setText(text1);
        this.textfield2.setText(text2);
        this.textfield3.setText(text3);
        this.textfield1.setEditable(true);
        this.textfield2.setEditable(true);
        this.textfield3.setEditable(true);
        calculatebotton.setVisible(true);
        this.yesbotton.setVisible(false);
        this.nobotton.setVisible(false);
        output.setText("");
    }
    else if(e.getSource().equals(nobotton)){
        System.exit(0);
    }
    }
    

    public void focusGained(FocusEvent e) {
       JTextField component = (JTextField)e.getSource();
       component.setText("");
    }
    
    public void focusLost(FocusEvent e)
    {
        if(e.getSource().equals(this.textfield1) && this.textfield1.getText().equals("")){
                this.textfield1.setText(text1);
            }
        else if(e.getSource().equals(this.textfield2) && this.textfield2.getText().equals("")){
            this.textfield2.setText(text2);
        }    
        else if(e.getSource().equals(this.textfield3) && this.textfield3.getText().equals("")){
            this.textfield3.setText(text3);
        }
    }
}