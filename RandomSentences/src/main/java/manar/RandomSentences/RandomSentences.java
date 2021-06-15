package manar.RandomSentences;

class RandomSentence{
static final String [] conjunction = {"and", "or", "but", "because"};
static final String [] proper_noun = {"Fred" , "Jane" , "Richard Nixon" , "Miss America"};
static final String [] common_noun = {"man" , "woman" , "fish" , "elephant" , "unicorn"};
static final String [] determiner = {"a" , "the" , "every" , "some"};
static final String [] adjective = {"big" , "tiny" , "pretty" , "bald"};
static final String [] intransitive_verb = {"runs" , "jumps" , "talks" , "sleeps"};
static final String [] transitive_verb = {"loves" , "hates" , "sees" , "knows" , "looks for" , "finds"};

public static void main(String[] args) {
    while(true){
        System.out.println(randomSentance()+"\n");
        try {
            Thread.sleep(3000);
        }
        catch (InterruptedException e) {}
    }
}

/**
 * this method will return a random string statement.
*/
static String randomSentance(){
   return simpleSentence() + " " 
   + ((Math.random()>0.5) ? conjunction[(int)(Math.random()*conjunction.length)] : "") + " " 
   + ((Math.random()>0.5) ?  randomSentance() : "") + " ";
}

static String simpleSentence(){
   return nounPhrase() + " " 
   + verbPhrase();
}

/**
 * this method will return a random string composed of a noun phrase.
*/
static String nounPhrase(){

    if(Math.random() > 0.5){
        return " " +  proper_noun[(int)(Math.random() * proper_noun.length)];
    }
    else{
        return " " +  determiner[(int)(Math.random() * determiner.length)] + " " 
        + ((Math.random() > 0.5) ? adjective[(int)Math.random() * adjective.length] : "") + ". "
        + common_noun[(int)(Math.random() * common_noun.length)] 
        + ((Math.random() > 0.5) ? " who "+ verbPhrase() : "");
    }  
}

/**
 * this method will return a random string composed of a verb phrase.
*/
static String verbPhrase(){
    double rand = Math.random();
    if(rand > 0.75){
        return intransitive_verb[(int)(Math.random() * intransitive_verb.length)];
    }
    else if(rand > 0.5){
        return transitive_verb[(int)(Math.random() * transitive_verb.length)] +" " + verbPhrase();
    }
    else if(rand > 0.2){
        return "is " + adjective[(int)(Math.random() * adjective.length)];
    }
    else{
        return " believes that " + simpleSentence();
    }
}
}
