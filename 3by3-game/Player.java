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
	
	//below is a function for the computer to make a move
	public int[] makeMove(char [][] marks) {
		ArrayList<int[]>validArr=validArray(marks);

		if(prioritizeWinning(marks)[0]!=-1 &&checkIfValid(validArr,prioritizeWinning(marks) ) ) { //if win or blocking the win of the other player is possible, the computer player will take it 
			return prioritizeWinning(marks);
		}

		int[] center= {1,1};
		if (checkIfValid(validArr, center)) //if center is available, the computer player will take it
			return center;

		int[][] corners = {{0,0},{0,2},{2,0},{2,2}}; 
		for (int i=0;i<corners.length;i++) { //if corners are available, the computer player will take it
			if(checkIfValid(validArr,corners[i]))
				return corners[i];
		}
			
		Random rand = new Random(); //else a random choice is picked 
		int[] choice = validArr.get(rand.nextInt(validArr.size()));
		return choice;

	}
	
	//below returns a boolean of whether the choice is in the ArrayList validArr
	private boolean checkIfValid(ArrayList<int[]> validArr, int[] choice) {
		for(int i=0;i<validArr.size();i++) {
			if(validArr.get(i)[0]==choice[0] && validArr.get(i)[1]==choice[1])
				return true;
		}
		return false;
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

	//below returns an int array for the choice to see if winning or blocking a win is possible
	private int[] prioritizeWinning(char[][] marks) {
		//check rows
		for(int i=0;i<marks.length;i++) {
			int row=i;
			int col=getDiff(marks[i], super.getSymbol());
			int[] choice= {row,col};
			if(col!=-1)
				return choice;
		}
		
		//check cols
		for(int i=0;i<marks[0].length;i++) {
			int row=getDiff(getCols(marks,i), super.getSymbol());
			int col=i;
			if(row!=-1)
				return new int[] {row,col};
		}
		
		//checks right diagonal
		char[] rDiag=getRDiag(marks);
		int place=getDiff(rDiag, super.getSymbol());
		if(place!=-1)
			return new int[] {place,place};
		
		//checks left diagonal
		char[] lDiag=getLDiag(marks);
		place = getDiff(lDiag, super.getSymbol());
		if(place!=-1)
			return new int[] {place, 2-place};
		
		 return new int[]{-1,-1};
	}
	
	//returns the columns of the array as a character array
	private char[] getCols(char[][] arr, int index) {
		char[] newarr=new char[3];
		for(int i=0;i<arr.length;i++)
			newarr[i]=arr[i][index];
		return newarr;
	}

	//returns the right diagonal of the array as a character array
	private char[] getRDiag(char[][] arr) {
		return new char[] {arr[0][0], arr[1][1], arr[2][2]};
	}
	
	//returns the left diagonal of the array as a character array
	private char[] getLDiag(char[][] arr) {
		return new char[] {arr[0][2], arr[1][1], arr[2][0]};
	}
	
	//returns to see if 2 of the characters in the array given are of same symbol, but are not equal to '-'
	private int getDiff(char[] arr, char key) {
		if(arr[0]==arr[1]&&(arr[0]!=arr[2])&&arr[2]=='-')
			return 2;
		else if(arr[0]==arr[2]&&(arr[0]!=arr[1])&&arr[1]=='-')
			return 1;
		else if(arr[1]==arr[2]&&(arr[2]!=arr[0])&&arr[0]=='-')
			return 0;
		return -1;
	}

	
	
}
