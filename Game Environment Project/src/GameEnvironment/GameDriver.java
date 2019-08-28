package GameEnvironment;
import boardGameGUI.BoardGame;

import java.io.IOException;
public abstract class GameDriver {

	public BoardGame boardGUI;
	public int currentPlayer;
	public Player[] players;

	public GamePiece[][] boardArray;

	public String winner;


	public GameDriver(String player1Name, String player2Name, int rows, int cols, String gameName) throws IOException {
		boardArray = new GamePiece[rows][cols];
	}


	public abstract void initializeBoardArray();
	//Run your game logic in here
	public abstract void runGame();
	//Return winner
	public String getWinner() {
		return winner;
	}
	//Will add current turn on GUI soon so you can update it along with this

	public void switchTurn() {
		if (currentPlayer == 0) {
			currentPlayer = 1;
		}
		else {
			currentPlayer = 0;
		}
	}

	//Will add score on GUI soon so you can update it along with this
	public void updateScore(int playerIndex, int value) {
		players[playerIndex].setPlayerScore(value);
	}

	public abstract boolean isGameOver();
	public abstract boolean isLegalMove(int x, int y);
	public abstract void makeMove(int x, int y);
}
