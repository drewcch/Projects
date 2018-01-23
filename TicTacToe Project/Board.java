package TicTacToe;
import java.util.ArrayList;

/**
 * This class is represents a board object and contains various instance variables, a default constructor, a
 * copy constructor, and various methods to provide board information for Tic-Tac-Toe.
 *
 */
public class Board {
	
	//private instance variables used in the board class
	private String[] game_board;
	private String winner;
	private String turn;
	
	/**
	 * The default Board constructor, which sets the instance variables of the board object
	 * to their beginning state.
	 */
	public Board() {
		String[] moves = {" "," "," "," "," "," "," "," "," "};
		this.game_board = moves;
		this.winner = " ";
		this.turn = "X";
	}
	
	/**
	 * The copy constructor takes in a board object and copies over its instance variables
	 * (game_board, winner, and turn); this is called whenever the AI makes a turn to 
	 * perform the AI algorithm so it does not impact the actual board object of the game.
	 * @param board -> board object which will have its instance variables copied over
	 */
	public Board(Board board) {
		this.game_board = board.game_board;
		this.winner = board.winner;
		this.turn = board.turn;
	}
	
	/**
	 * This method updates the game board according to the user's move during his/her turn. 
	 * @param row -> the row indicating the user's move
	 * @param col -> the column indicating the user's move
	 */
	public void userMove(int row, int col) {
		int move_pos = (row-1)*3 + (col-1);
		this.game_board[move_pos] = this.turn;
	}
	
	/**
	 * Updates the turn instance variable by changing it to AI if the user just made a move or 
	 * user if the AI just made a move.
	 */
	public void updateTurn() {
		if (this.turn == "X") {
			this.setTurn("O");
		}
		else {
			this.setTurn("X");
		}
	}
	
	/**
	 * This method loops through the game board and finds the indexes that have no moves
	 * by either the player or the AI (index = " "). These indexes are then appended into an 
	 * ArrayList that is returned (ArrayList is used since the length is unknown).
	 * @return -> An ArrayList of integers containing the indexes of empty spots in the game board
	 */
	public ArrayList<Integer> getEmptyIndexes() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < this.game_board.length; i++) {
			if (this.game_board[i] == " ") {
				result.add(i);
			}
		}
		return result;
	}
	
	/**
	 * Accessor method that returns the game board, which is a String array
	 * @return -> A String array representing the game board
	 */
	public String[] getGameBoard() {
		return this.game_board;
	}
	
	/**
	 * This method update an index of the game board with the current player's turn.
	 * This is used when the AI makes a move or when the game is looking ahead in the 
	 * AILogic class where moves are made by indexes of the game_board as opposed to 
	 * rows and columns that are inputed by the user.
	 * @param index -> integer representing an index in the game board
	 * @param turn -> String representing the current player's turn
	 */
	public void algorithmGameMove(int index, String turn) {
		this.game_board[index] = turn;
	}
	
	/**
	 * Accessor method that returns the game's winner, which is represented as a string.
	 * @return -> String representing the winner
	 */
	public String getWinner() {
		return this.winner;
	}
	
	/**
	 * Mutator method that sets the game's winner, which is represented as a string
	 * @param winner -> String representing the winner of the game
	 */
	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	/**
	 * Accessor method that returns the game's turn, which is represented as a string.
	 * @return -> String representing the turn
	 */
	public String getTurn() {
		return this.turn;
	}
	
	/**
	 * Mutator method that sets the game's turn, which is represented as a string
	 * @param winner -> String representing the turn in the game
	 */
	public void setTurn(String turn) {
		this.turn = turn;
	}
	
}
