//Guess.java
//Thomas Brochard
//
import java.util.Scanner;

class Guess{
   public static void main(String[] args){
      int answer= (int)Math.ceil(Math.random()*10);
      int count=0;
      int tries=3;
      int guess=0;
      boolean gameWon=false;
      Scanner sc=new Scanner(System.in);
      
      System.out.println();
      System.out.println("I'm thinking of an integer in the range 1 to 10. You have three guesses.");
      System.out.println();
      while(count<tries && gameWon==false)
      {
         if(count==0)
         {System.out.print("Enter your first guess: ");
            guess=sc.nextInt();  
         }
         if(count==1)
         {System.out.print("Enter your second guess: ");
            guess=sc.nextInt();
         }
         if(count==2)
         {System.out.print("Enter your third guess: ");
            guess=sc.nextInt();
         }
         if(guess>answer)
            {System.out.println("Your guess is too high.");
            System.out.println();}       
         else if(guess<answer)
            {System.out.println("your guess is too low.");
        System.out.println();}
         else
            gameWon=true;
         count++;
        
      }
      
      if(gameWon==true)
         System.out.println("You win!");
      else
         System.out.println("You lose. The number  was "+answer+".");
      System.out.println();
   }
}
