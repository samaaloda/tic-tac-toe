import java.util.Scanner;
import java.util.ArrayList;


//public class Game that manages the board and the players, and manages game flow.
public class Game {
	protected Board board;
	protected Player[] players; //array of type Player
	protected char winner;
	
	//constructor for the class
	public Game() {
		board = new Board();
		board.empty();
		players=new Player[2];
		winner ='-';

	}
	
	//plays game
	public void playGame() {
		System.out.println("Player 1 is X and Player 2 is O. \n");
		startGame();
		do {//start game at least once, keep going if user would like to restart
			board.empty();
		board.displayBoard();
		while(!isOver() || board.haveWinner()=='-') {
			System.out.println("Player 1: ");
			int [] choice1 =players[0].makeMove(board.getMarks());//makes a move as necessary
			board.modifyBoard(players[0].getSymbol(), choice1[0], choice1[1]);//modifies board as necessary
			board.displayBoard();
			if(isOver() || (board.haveWinner() !='-')) {//check if board has returned winner or if there are no more marks on the board
				winner = board.haveWinner();
				break;
			}
			System.out.println("Player 2: ");
			int[] choice2=players[1].makeMove(board.getMarks());
			board.modifyBoard(players[1].getSymbol(), choice2[0], choice2[1]);
			board.displayBoard();
			if(isOver() || (board.haveWinner() !='-')) {
				winner = board.haveWinner();
				break;
			}
			}
		if(winner !='-')
			System.out.println("Congratulations "+winner+" has won.\n"); //announces winner
		else
			System.out.println("That was a tie. Good game though!\n"); //anounces a tie
		int choice=inputIntegerBetween("Would you like to restart? 1 for yes, 2 for no ",1,2);
		if (choice==2)
			break;
		else
			startGame(); //starts game if user would like to restart
		}while(true);
		
	}
	
	//the below method checks if all marks on the board are filled
	protected boolean isOver() {
		char[][] marks = board.getMarks();
		for(int i=0;i<marks.length;i++) {
			for(int j=0;j<marks[0].length;j++) {
				if(marks[i][j]=='-')
					return false;
			}
		}
		return true;
	}

	//the below method initializes the players based on their number (i.e. player 1 or 2) and choice of whether they're computer or human players
	protected void initializePlayers(int choice, int num) {
		char symbol ='O';
		if(num==1) 
			symbol='X';
		if(choice ==2)
			players[num-1]=new CompPlayer(num, symbol);
		else
			players[num-1]=new HumanPlayer(num, symbol);
	}

	//The below method starts the game by getting user input of whether they'd like to play as human or computer
	protected void startGame() {
		System.out.println("Please select whether the players are computer or human. 1 is for Human, 2 is for Computer");
		for(int i=0;i<2;i++) {
			int type=inputIntegerBetween("Is Player "+(i+1)+" a human or computer? ", 1, 2);
			initializePlayers(type, i+1);
		}
		board.empty();
		
	}
	
	//below checks if given String is an integer
	protected boolean isInteger (String inputtedString) {
		try {
			Integer.parseInt(inputtedString);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
	//below outputs a message of which the user returns an answer with. 
	protected int inputInteger (String message) {
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
	protected int inputIntegerBetween(String message, int lowBound, int highBound) {
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
}





