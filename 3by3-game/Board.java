import java.util.ArrayList;
//class ParentBoard checks for winning conditions and displays the board
 class ParentBoard {
	protected char [][] marks;
	
	//below is a constructor for if the user were not to include a given size, the size defaults to 3
	public ParentBoard() {
		marks=new char[3][3];
	}
	
	//below is a constructor for sizes set to the size given by user
	public ParentBoard(int size) {
		if(size<3)
			throw new IllegalArgumentException("Size of board must be 3 or greater.");
		marks=new char[size][size];
	}
	
	//below sets a value of char mark to row and col given in the parameters
	public void setValue(int row, int col, char mark) {
		if(mark != 'X' && mark != 'O')
			throw new IllegalArgumentException("A mark can only be X or O");
		marks[row][col]=mark;
	}
	
	
	//below modifies board by setting the char symbol into the row and col provided
	public void modifyBoard(char symbol, int row, int col) {
		marks[row][col]=symbol;
	}
	
	//below returns a String of dashes with the amount given as a parameter
	private String giveDashes(int amount) {
		String str="";
		for (int i=0;i<amount;i++) {
			str+="-";
		}
		return str;
	}

	//below returns marks as a 2D char array
	public char[][] getMarks(){
		return marks;
	}
	
	//below displays the board 
	public void displayBoard() {
		String str="\n";
		for(int i=0;i<marks.length;i++) {
			for(int j=0;j<marks[i].length;j++) {
				str+=" "+marks[i][j]+" |";
			}
			str=str.substring(0, str.length()-1);
			str+="\n"+giveDashes(marks.length*4-1)+"\n";
		}
		str=str.substring(0,str.length()-marks.length*4-1);
		System.out.println("\n"+ str+"\n");
	}
	
	//below returns a boolean of if a move is made
	public boolean moveMade(int row, int col) {
		return !(marks[row][col]=='-');
	}
	
	//below empties out the board by setting all of its values to - 
	public void empty() {
		for(int i=0;i<marks.length;i++) {
			for (int j=0;j<marks[0].length;j++)
				marks[i][j]='-';
		}
	}
}

//class below extends the parent class above, by adding on checking for winner condition functions
public class Board extends ParentBoard{

	//constructor for this class calls on parent class
	public Board() {
		super();
	}

	//constructor for this class calls on parent class with given size
	public Board(int size) {
		super(size);
	}

	//checks for each row to see if winner is found
	private char checkRow() {
		boolean found;
		int row;
		for(row=0;row<marks.length;row++) {
			found = true;
			for(int j=0;j<marks[0].length-1;j++) {
				if(marks[row][j]!=marks[row][j+1])
					found=false;
			}
			if(found&&marks[row][0]!='-')
				return marks[row][0];
		}
		return '-';
	}

	//checks for each col to see if winner is found
	private char checkCol() {
		boolean found;
		int col;
		for(col=0;col<marks[0].length;col++) {
			found = true;
			for(int j=0; j<marks.length-1;j++) {
				if(marks[j][col]!=marks[j+1][col])
					found=false;
			}
			if(found&&marks[0][col]!='-')
				return marks[0][col];
		}
		return '-';
	}

	//checks right diagonal to see if winner found
	private char checkRDiagonal() {
		
		for(int i=0;i<marks.length-1;i++) {
			if(marks[i][marks.length-i-1]!=marks[i+1][marks.length-i-2])
				return '-';
		}
		return marks[0][marks.length-1];
		
	}
	
	//checks left diagonal to see if winner found
	private char checkLDiagonal() {
		
		for(int i=0;i<marks.length-1;i++) {
			if(marks[i][i]!=marks[i+1][i+1])
				return '-';
		}
		return marks[0][0];
		
	}

	//checks all helper functions to see if winner is found in any of them. If winner is found, it will return thei symbol, otherwise it will return a '-'
	public char haveWinner() {
		
		if(checkRow()!='-')
			return checkRow();
		else if (checkCol()!='-')
			return checkCol();
		else if(checkRDiagonal()!='-')
			return checkRDiagonal();
		else
			return checkLDiagonal();
	}

	
	
}

