package manar.quiz;

import javax.swing.*;

public class TrueFalseQuestion extends Question {

    TrueFalseQuestion(String question , String answer)
    {
        super(question);
        JPanel buttons = new JPanel();
        addButton(buttons,"TRUE");
        addButton(buttons,"FALSE");
        this.question.add(buttons);
        initQuestionDialog();
        answer = answer.toUpperCase();
        if(answer.equals("Y") || answer.equals("T") ||answer.equals("YES") ||answer.equals("TRUE"))  super.correctAnswer = "TRUE";
        else if(answer.equals("N") || answer.equals("F") ||answer.equals("NO") ||answer.equals("FALSE")) super.correctAnswer =  "FALSE";
        else throw new IllegalArgumentException("A true false question can only have TRUE or FALSE as answers.");
    }

    void addButton(JPanel buttons, String label) 
    {
        JButton button = new JButton(label);
        button.addActionListener(question);
        buttons.add(button);
    }
}
