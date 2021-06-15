package manar.quiz;

import java.awt.*;
import javax.swing.*;

public abstract class Question extends QuestionDialog {
    static int nQuestions = 0;
    static int nCorrect = 0;
    QuestionDialog question;
    String correctAnswer;

    Question(String question)
    {
        this.question = new QuestionDialog();
        this.question.setLayout(new GridLayout(0,1));
        this.question.add(new JLabel(" "+question+" ",JLabel.CENTER));
    }

    void check ()
    {
        //cheching for correct answer and incrementing score counters
        nQuestions++;
        //correct answer 
        if(ask().equals(correctAnswer))
        {
            nCorrect++;
            JOptionPane.showMessageDialog(null,"Correct.");
        }  
        //incorect answer
        else JOptionPane.showMessageDialog(null,"Incorrect. The correct answer is " + correctAnswer + '.');
    }

    String ask()
    {
        question.setVisible(true);
        return question.answer;
    }

    static void showResults()
    {
        JOptionPane.showMessageDialog(null , String.format("correct %d out of %d questions.", nCorrect, nQuestions));
    }

    void initQuestionDialog() 
    {
        question.setModal(true);
        question.pack();
        question.setLocationRelativeTo(null);
    }

}
