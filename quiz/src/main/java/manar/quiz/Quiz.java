package manar.quiz;

public class Quiz
{
    public static void main(String[] args)
    {
        Question quizzer;
        quizzer = new MultipleChoiceQuestion("Which movie did Leonardo DiCaprio star in", "I Am Legend", "The Wolf of Wall", "Pirates of the Caribbean", "The Mask", "Django Unchained", "B"); quizzer.check();
        quizzer = new MultipleChoiceQuestion("Which movie did Jim Carrey star in", "I Am Legend", "The Wolf of Wall", "Pirates of the Caribbean", "The Mask", "Django Unchained", "D"); quizzer.check();
        quizzer = new MultipleChoiceQuestion("Which movie did Jamie Foxx star in", "I Am Legend", "The Wolf of Wall", "Pirates of the Caribbean", "The Mask", "Django Unchained", "E"); quizzer.check();
        quizzer = new MultipleChoiceQuestion("Which movie did Johnny Depp star in", "I Am Legend", "The Wolf of Wall", "Pirates of the Caribbean", "The Mask", "Django Unchained", "C"); quizzer.check();
        quizzer = new MultipleChoiceQuestion("Which movie did Will Smith star in", "I Am Legend", "The Wolf of Wall", "Pirates of the Caribbean", "The Mask", "Django Unchained", "A"); quizzer.check();
        quizzer = new TrueFalseQuestion("1 Celsius is 33.8 Fahrenheit", "True");quizzer.check();
        quizzer = new TrueFalseQuestion("1 newton is equal to 105 grams.", "False");quizzer.check();
        quizzer = new TrueFalseQuestion("1 Kilometer = 0.621371 Miles", "True");quizzer.check();
        quizzer = new TrueFalseQuestion("1 Pound-force per square inch = 7000 Pascal", "False");quizzer.check(); 
        quizzer = new TrueFalseQuestion("1 liter = 35 Oz", "True"); quizzer.check();
      
        quizzer.showResults();
    }
}