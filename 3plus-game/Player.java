import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

//parent class Player that holds attribute for each player
public abstract class Player {
	private int num;
	private char symbol;
	
	//constructor for this class with their num and symbol
	public Player(int num, char symbol) {
		this.num=num;
		this.symbol=symbol;
	}
	
	//returns the num of the player
	public int getNum() {
		return num;
	}

	//returns the symbol of the player 
	public char getSymbol() {
		return symbol;
	}

	//abstract class that, based on the board, returns the choice of the move made
	public abstract int[] makeMove(char[][] marks);
		
}

//class that extends parent class Player that is for human players
class HumanPlayer extends Player{

	//contructor for the class that calls on parent class
	public HumanPlayer(int num, char symbol) {
		super(num, symbol);
	}
	
	//returns an int array declaring the human player's choice of row and column of where they'd like to place their mark
	//user must input their their choice as (2,3) indicating row 2 and column 3
	public int[] makeMove(char [][] marks) {
		
		Scanner userInput= new Scanner(System.in);
		String ans;
		do {
			System.out.println("Please enter your entry like (2,3) to indicate row 2 and column 3.");
			System.out.print("Please enter desired row and column: ");
			ans = userInput.nextLine();
			if(ans.length()<5)
				continue;
			if(ans.charAt(0)!='('||ans.charAt(ans.length()-1)!=')'||ans.indexOf(',')==-1)
				continue;
			else if(isInteger(ans.substring(1,ans.indexOf(','))) && isInteger(ans.substring(ans.indexOf(',')+1,ans.length()-1))) {
				int row = Integer.parseInt(ans.substring(1,ans.indexOf(',')));
				int col = Integer.parseInt(ans.substring(ans.indexOf(',')+1, ans.length()-1));
				if(row<=marks.length&&row>=1 &&col<=marks.length&&col>=1&&marks[row-1][col-1]=='-') 
					return (new int[] {row-1, col-1});
				else
					continue;			
			} 
			
		} while(true);

	}

	//below returns a boolean of whether inputtedString is an integer or not
	private boolean isInteger (String inputtedString) {
		try {
			Integer.parseInt(inputtedString);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
		
}

//class CompPlayer that extends parent class Player for computer player
class CompPlayer extends Player{

	//constructor for this class that calls on parent class
	public CompPlayer(int num, char symbol) {
		super(num, symbol);
	}
	
	//below makes a random move based on valid spots on the bord
	public int[] makeMove(char [][] marks) {
		ArrayList<int[]>validArr=validArray(marks);
			
		Random rand = new Random();
		int[] choice = validArr.get(rand.nextInt(validArr.size()));
		return choice;

	}
	
	//below returns an ArrayList of possible choices
	private ArrayList<int[]> validArray(char[][] marks){
		ArrayList <int[]> validSpots=new ArrayList<>();
		for(int i=0;i<marks.length;i++) {
			for(int j=0;j<marks.length;j++) {
				if(marks[i][j]=='-') {
					int[] spot= {i,j};
					validSpots.add(spot);
				}
			}
		}
		return validSpots;
		
	}

	
	
}
