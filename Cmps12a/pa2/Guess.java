//Guess.java
//Thomas Brochard
//
import java.util.Scanner;

class Guess{
   public static void main(String[] args){
      
      int tries = 100;
      int answer = (int)Math.ceil(Math.random()*10);
      int guess = 0;
      String direction;

      Scanner sc=new Scanner(System.in);
      System.out.printf("I'm thinking of an integer in the range 1 to 10. You have three guesses.\n");
    
      String[] round = {"first", "second", "third"};

      for(int i=0; i<tries; i++){
        System.out.printf("Enter your %s guess: ", round[i]);
        guess = sc.nextInt();
	if(guess ==  answer){
          System.out.printf("You win! ");
          System.exit(1);
	}
	else{
	  direction = ((guess < answer)? "low" : "high");
          System.out.printf("Your guess is too %s. \n", direction);
	}
      }
   
      System.out.printf("You lose. The number  was %d.", answer);
   }
}
