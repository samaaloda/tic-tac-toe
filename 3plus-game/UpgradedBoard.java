import java.util.ArrayList;
public class UpgradedBoard extends Board{
	int M;

	//constructor for class that intializes based on size and M which is the size looking for
	public UpgradedBoard(int size, int M) {
		super(size);
		if(size<3 || size >20)
			throw new IllegalArgumentException("Size cannot be smaller than 3 or greater than 20.");
		if(M<3||M>20)
			throw new IllegalArgumentException("M cannot be smaller than 2 or bigger than 20.");
		this.M=M;
	}

	//extracts winner from an array by checking if a symbol is the same M times in a row from arr given
	private char extractWinner(char[] arr) {
		boolean found=false;
		for(int k=0;k<arr.length-M+1;k++) {
				found = true;
				for(int j=0;j<M-1;j++) {
					if(arr[j+k]!=arr[j+1+k])
						found=false;
				}
				if(found&&arr[k]!='-')
					return arr[k];
		}
		return '-';
	}
	
	//returns a char array of cols
	private char[] getCols(char[][] arr, int index) {
		char[] newarr=new char[arr.length];
		for(int i=0;i<arr.length;i++)
			newarr[i]=arr[i][index];
		return newarr;
	}

	//returns a char array from ArrayList
	private char[] toArr(ArrayList<Character> chars) {
		char[] newarr= new char[chars.size()];
		for(int i=0;i<newarr.length;i++) {
			newarr[i]=chars.get(i);
		}
		return newarr;
	}

	//returns the winner from the right diagonal, if exists
	private char getRDiag() {
		//start from rows
		int row=0;
		for(int i=0;i<(marks.length-M+1);i++) {
			ArrayList<Character> chars=new ArrayList<>();
			int k=row;
			while(k<marks.length) {
				chars.add(marks[k][k-row]);//1?
				k++;
			}
			char[] newChars=toArr(chars);
			row++;
			if(extractWinner(newChars)!='-')
				return extractWinner(newChars);
		}
		
		//go to cols
		int col=0;
		for(int i=0;i<(marks.length-M);i++) {
			int k=col;
			ArrayList<Character> chars=new ArrayList<>();
			while(k<marks.length) {
				chars.add(marks[k-col][k]);
				k++;
			}
			char[] newChars=toArr(chars);
			col++;
			if(extractWinner(newChars)!='-')
				return extractWinner(newChars);
		}
		return '-';
		
	}
	
	//returns winner from left diagonal, if exists
	private char getLDiag(char[][] arr) {
		//start from rows
		int row=0;
		for(int i=0;i<(marks.length-M+1);i++) {
			ArrayList<Character> chars=new ArrayList<>();
			int k=row;
			while(k<marks.length) {
				chars.add(marks[k][(marks.length-1)-(k-row)]);
				k++;
			}
			char[] newChars=toArr(chars);
			row++;
			if(extractWinner(newChars)!='-')
				return extractWinner(newChars);
		}
		
		//go to cols
		int col=0;
		for(int i=0;i<(marks.length-M);i++) {
			int k=col;
			ArrayList<Character> chars=new ArrayList<>();
			while(k<marks.length) {
				chars.add(marks[k-col][marks.length-1-k]);
				k++;
			}
			char[] newChars=toArr(chars);
			col++;
			if(extractWinner(newChars)!='-')
				return extractWinner(newChars);
		}
		return '-';
	}
	
	//goes through all helper functions to see if a winner exists in any of these functions. If so, retuns their symbol, otherwise returns '-'
	public char haveWinner() {
		
		//check rows
		for(int i=0;i<marks.length;i++) {
			if(extractWinner(marks[i])!='-')
				return extractWinner(marks[i]);
		}
		
		//check cols
		for(int i=0;i<marks.length;i++) {
			if(extractWinner(getCols(marks,i))!='-')
				return extractWinner(getCols(marks,i));
		}
		char result = getRDiag();
		if(result!='-')
			return result;
		
		return getLDiag(marks);

	}
	
	
}
