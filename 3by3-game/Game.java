import java.util.Scanner;
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

import java.util.concurrent.CountDownLatch;

//public class Game that manages the board and the players, and manages game flow.
public class Game {
	public Board board;
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
			players[num-1]=new CompPlayer(num, symbol, 'C');
		else
			players[num-1]=new HumanPlayer(num, symbol, 'H');
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

class GUIGame extends Game implements ActionListener {
    int count = 0;
    JFrame frame;
    JLabel title;
    JPanel grid;
	JPanel options;
    JLabel status;
	JLabel pick;

	JButton[][] buttons;

	JButton Coption;
	JButton Hoption;

	JPanel everyth;

	

    public GUIGame() {
        super();
        frame = new JFrame();
		frame.setSize(1000,1000);

		Coption= new JButton("Computer");
		Hoption= new JButton("Human");
		status = new JLabel("Starting Game...");
		status.setFont(new Font("Arial", Font.BOLD, 14));
		
		everyth=new JPanel();
		everyth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		everyth.setLayout(new GridLayout(4,1));
		everyth.setBackground(Color.lightGray);

        title = new JLabel("Tic-Tac-Toe", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
		title.setBackground(Color.white);

		options= new JPanel();
		options.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        options.setLayout(new GridLayout(1, 2));
		options.setBackground(Color.lightGray);


		options.add(Coption);
		options.add(Hoption);

		Coption.addActionListener(this);
		Hoption.addActionListener(this);


        grid = new JPanel();
        grid.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        grid.setLayout(new GridLayout(3, 3));
		grid.setBackground(Color.DARK_GRAY);

		buttons = new JButton[3][3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				buttons[i][j]=new JButton("-");
				grid.add(buttons[i][j]);
				buttons[i][j].setBackground(Color.white);
				buttons[i][j].setFont(new Font("Arial", Font.BOLD, 16));
			}
		}



		everyth.add(title);		
		
		everyth.add(grid);
		everyth.add(status);
		everyth.add(options);

        frame.add(everyth, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setTitle("Tic-Tac-Toe");
        frame.setVisible(true);

        playGame();
    }

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source.getText().equals("-")) {
			if(count%2==0)
				source.setText("X");
			else
				source.setText("O");
            count++;
        }
    }

    public int[] giveMark(char[][] moves, Player pl) {
        final int[] choice = {-1, -1};
		final CountDownLatch latch = new CountDownLatch(1);

        ActionListener listener = e -> {
            JButton source = (JButton) e.getSource();
            if (source == buttons[0][0]){ 
				choice[0] =0; 
				choice[1] = 0;
			}
            else if (source == buttons[0][1]){ 
				choice[0] =0; 
				choice[1] = 1;
			}
            else if (source == buttons[0][2]){
				choice[0] =0; 
				choice[1] = 2;
			}
            else if (source == buttons[1][0]) {
				choice[0] =1; 
				choice[1] = 0;
			}
            else if (source == buttons[1][1]) {
				choice[0] =1; 
				choice[1] = 1;
			}
            else if (source == buttons[1][2]) {
				choice[0] =1; 
				choice[1] = 2;
			}
            else if (source == buttons[2][0]) {
				choice[0] =2; 
				choice[1] = 0;
			}
            else if (source == buttons[2][1]) {
				choice[0] =2; 
				choice[1] =1;
			}
            else if (source == buttons[2][2]) {
				choice[0] =2; 
				choice[1] = 2;
			}
			latch.countDown();
        };

		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
				buttons[i][j].addActionListener(listener);
		}

		try {
        	latch.await(); 
   		} catch (InterruptedException ex) {
        	ex.printStackTrace(); 
    	}
		
		return choice;
    }

	public void displayGame(int[] choice, char sym){

		buttons[choice[0]][choice[1]].setText(""+sym);

	}

	public void getPlayers(int num, char sym, String text){
		status.setText(text);
		final char[] choice= {0};
		final CountDownLatch latch = new CountDownLatch(1);
		 ActionListener listener = e -> {
            JButton source = (JButton) e.getSource();
			if(source==Hoption){
				choice[0]='H';
			}
			else if(source==Coption){
				choice[0]='C';
			}
			latch.countDown();
		};
		Hoption.addActionListener(listener);
		Coption.addActionListener(listener);

		try {
        	latch.await(); 
   		} catch (InterruptedException ex) {
        	ex.printStackTrace(); 
    	}

		if(choice[0]=='H')
			players[num]=new HumanPlayer(num, sym, 'H');
		else if(choice[0]=='C')
			players[num]=new CompPlayer(num,sym, 'C');
	}

    public void playGame() {
		int[] choice;
		status.setText("Player 1 is X and Player 2 is O.");
		
		getPlayers(0,'X',"Is Player 1 a human or computer?");
		getPlayers(1,'0', "Is Player 2 a human or computer?");

		Coption.setEnabled(false);
		Hoption.setEnabled(false);

			board.empty();
		while(!isOver() || board.haveWinner()=='-') {
			status.setText("Player 1's turn: ");
			if(players[0].getType()=='H')
				choice=giveMark(board.getMarks(), players[0]);
			else
				choice=players[0].makeMove(board.getMarks());
			board.modifyBoard(players[0].getSymbol(), choice[0], choice[1]);//modifies board as necessary
			displayGame(choice, players[0].getSymbol());
			if(isOver() || (board.haveWinner() !='-')) {//check if board has returned winner or if there are no more marks on the board
				winner = board.haveWinner();
				break;
			}
			status.setText("Player 2's turn: ");
			if(players[1].getType()=='H')
				choice=giveMark(board.getMarks(), players[1]);
			else
				choice=players[1].makeMove(board.getMarks());
			board.modifyBoard(players[1].getSymbol(), choice[0], choice[1]);
			displayGame(choice, players[1].getSymbol());
			if(isOver() || (board.haveWinner() !='-')) {
				winner = board.haveWinner();
				break;
			}
			}
		if(winner !='-'){
			status.setText("Congratulations "+winner+" has won.\n"); //announces winner
		}
		else
			status.setText("That was a tie. Good game though!\n"); //anounces a tie
		

		
		return;
		
	}
}

