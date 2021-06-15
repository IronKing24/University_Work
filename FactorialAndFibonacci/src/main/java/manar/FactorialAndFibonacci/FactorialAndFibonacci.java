package manar.FactorialAndFibonacci;

import textio.TextIO;

class FactorialAndFibonacci{
public static void main(String[] args) {
	while(true) {
		 System.out.println("please, inter a value");
		FactorialAndFibonacci(TextIO.getFloat());	
	}
}

//prints out the factorial and the fibonacci of the input value (a)
static void FactorialAndFibonacci(float a)
{
    if(a > 0){
        System.out.println("the factorial value is: " + factorial(a));
        System.out.println("the fibonacci value is: " + fibonacci(a));
    }
    else{
        System.out.println("the value should be greater than 0");
    }

}

//returns the factorial value of the input (a) as a float
static float factorial(float a){
    if(a <= 0){
        return 1;
    }
    return a * factorial(a-1);
}

//returns the fibonacci value of the input (a) as a float
static float fibonacci(float a){
    if(a < 1){
        return 1;
    }
    return fibonacci(a-1) + fibonacci(a-2);
}
}