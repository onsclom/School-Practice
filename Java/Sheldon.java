import java.util.Scanner;

public class Sheldon 
{
    enum choices 
    {
        ROCK,
        PAPER,
        SCISSORS,
        LIZARD,
        SPOCK
    }

    public static final int OPTIONS = 5;

    public static void main (String[] args)
    {
        Scanner in = new Scanner(System.in);
        boolean playAgain = true;
        int userWins = 0;
        int computerWins = 0;
        int tieAmount = 0;
        System.out.println("Welcome to Sheldon's Rock, Paper, " +
                            "Scissors, Lizard, or Spock!");
        
        do 
        {
            //get computers choice
            int randomNum = (int) (Math.random() * OPTIONS);
            String compChoice = "";

            switch(choices.values()[randomNum])
            {
                case ROCK:
                    compChoice = "Rock";
                    break;

                case PAPER:
                    compChoice = "Paper";
                    break;

                case SCISSORS:
                    compChoice = "Scissors";
                    break;

                case LIZARD:
                    compChoice = "Lizard";
                    break;

                case SPOCK:
                    compChoice = "Spock";
                    break;
            }

            System.out.print("Please enter your choice: ");
            String input = in.nextLine();

            System.out.println("User: " + input);
            System.out.println("Computer: " + compChoice);

            //STARTING EVALUATION CODE
            if (input.equals(compChoice))
            {
                System.out.println("Tie!");
                tieAmount++;
            }
            else if (input.equals("Rock"))
            {
                if (compChoice.equals("Spock"))
                {
                    System.out.println("Spock vaporizes Rock! Computer Wins!");
                    computerWins++;
                }
                else if (compChoice.equals("Lizard"))
                {
                    System.out.println("Rock crushes Lizard! User wins!");        
                    userWins++;           
                }
                else if (compChoice.equals("Scissors"))
                {
                    System.out.println("Rock crushes Scissors! User wins!");       
                    userWins++;             
                }
                else if (compChoice.equals("Paper"))
                {
                    System.out.println("Paper covers Rock! Computer wins!");       
                    computerWins++;             
                }
            }
            else if (input.equals("Paper"))
            {
                if (compChoice.equals("Spock"))
                {
                    System.out.println("Paper disproves Spock! User wins!");
                    userWins++;
                }
                else if (compChoice.equals("Lizard"))
                {
                    System.out.println("Lizard eats Paper! Computer wins!");        
                    computerWins++;           
                }
                else if (compChoice.equals("Scissors"))
                {
                    System.out.println("Scissors cuts Paper! Computer wins!");       
                    computerWins++;             
                }
                else if (compChoice.equals("Rock"))
                {
                    System.out.println("Paper covers Rock! User wins!");       
                    userWins++;             
                }
            }
            else if (input.equals("Scissors"))
            {
                if (compChoice.equals("Spock"))
                {
                    System.out.println("Spock smash Scissors! Computer wins!");
                    computerWins++;
                }
                else if (compChoice.equals("Lizard"))
                {
                    System.out.println("Scissors decapitates Lizard! User wins!");        
                    userWins++;           
                }
                else if (compChoice.equals("Rock"))
                {
                    System.out.println("Rock crushes Scissors! Computer wins!");       
                    computerWins++;             
                }
                else if (compChoice.equals("Paper"))
                {
                    System.out.println("Scissors cuts Paper! User wins!");       
                    userWins++;             
                }
            }
            else if (input.equals("Lizard"))
            {
                if (compChoice.equals("Spock"))
                {
                    System.out.println("Lizard poisons Spock!");
                    userWins++;
                }
                else if (compChoice.equals("Rock"))
                {
                    System.out.println("Rock crushes Lizard! Computer wins!");        
                    computerWins++;           
                }
                else if (compChoice.equals("Scissors"))
                {
                    System.out.println("Scissors decapitates Lizard! Computer wins!");       
                    computerWins++;             
                }
                else if (compChoice.equals("Paper"))
                {
                    System.out.println("Lizard eats Paper! User wins!");       
                    userWins++;             
                }
            }
            else if (input.equals("Spock"))
            {
                if (compChoice.equals("Rock"))
                {
                    System.out.println("Spock vaporizes Rock! User Wins!");
                    userWins++;
                }
                else if (compChoice.equals("Lizard"))
                {
                    System.out.println("Lizard poisons Spock! Computer Wins!");        
                    computerWins++;           
                }
                else if (compChoice.equals("Scissors"))
                {
                    System.out.println("Spock smashes Scissors! User wins!");       
                    userWins++;             
                }
                else if (compChoice.equals("Paper"))
                {
                    System.out.println("Paper disproves Spock! Computer wins!");       
                    computerWins++;             
                }
            }

            System.out.print("Do you want to play again (Y/N)?");
            input = in.nextLine();
            if (!input.equals("Y"))
            {
                playAgain = false;
            }
        }
        while (playAgain);

        //output stuff
        System.out.println("Number of times the user won: " + userWins);
        System.out.println("Number of times the computer won: " + computerWins);
        System.out.println("Number of times the user and computer tied: " + tieAmount);
        System.out.println("Average number of gestures to determine a winner: I NEED TO DO THIS STILL");
    }
}