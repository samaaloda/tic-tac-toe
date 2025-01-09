import java.util.Scanner;

public class Main {
	public static void main(String [] args) {
		System.out.println("\nWelcome to TicTacToe! I dare you to win against the computer ;)");
		Game game = new Game(); //creates a new instance of class Game
		game.playGame(); //plays game

		System.out.println("\nTold you you couldn't win ;) Bye now!");
	}
}

