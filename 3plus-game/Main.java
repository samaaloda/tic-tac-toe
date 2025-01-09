import java.util.Scanner;
//main class that initializes and calls on other classes as necessary
public class Main {
	//below checks if given String is an integer
	public static boolean isInteger (String inputtedString) {
		try {
			Integer.parseInt(inputtedString);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	//below outputs a message of which the user returns an answer with. 
	public static int inputInteger (String message) {
		Scanner userInput = new Scanner(System.in);
		boolean invalidInput = true;
		String userEntered;
		int result = 0;
		
		while (invalidInput) {
			System.out.print(message);
			userEntered = userInput.nextLine();
			if (isInteger(userEntered)) {
				result = Integer.parseInt(userEntered);
				invalidInput = false;
			}
			else
				System.out.println("Please enter a valid integer value");
			
	}
		return result;
	}
		
	//below checks to see if user input is between the highbound and lowbound, and is an integer
	public static int inputIntegerBetween(String message, int lowBound, int highBound) {
		Scanner userInput = new Scanner(System.in);
		boolean invalidInput = true;
		int result=0;
	
		do {
			result = inputInteger(message);
			if (result > highBound)
				System.out.println("Please enter a number lower than or equal to "+highBound);
			else if (result <lowBound)
				System.out.println("Please enter a number larger than or equal to "+lowBound);
			else
				invalidInput = false;
		} while (invalidInput);
		return result;
}
	public static void main(String [] args) {
		System.out.println("Welcome to TicTacToe!\n");
		int size=inputIntegerBetween("What size would you like your game to be? ",3, 20); //initializes size of the board
		int M=inputIntegerBetween("What size would you like to look for? ",3, size); //initializes the size of which the user may look for
		UpgradedGame game = new UpgradedGame(size,M); 
		game.playGame();

		System.out.println("Thanks for playing! See you next time ;)");
	}
}
