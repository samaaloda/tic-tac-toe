//class below extends Game
public class UpgradedGame extends Game{
	private int M;

	//constructor initializes size and M value of the game
	public UpgradedGame(int size, int M) {
		super.board = new UpgradedBoard(size, M);
		super.players=new Player[2];
		super.winner ='-';
		this.M=M;
	}
	
	
}
