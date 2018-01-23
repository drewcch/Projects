package TicTacToe;

/**
 * The Game Logic class implements Tic-Tac-Toe game logic by performing tasks such as checking 
 * if there is a winner in the game or if a move desired by the user is a valid unoccupied spot
 * in the game board. 
 */
public class GameLogic {
	
	/**
	 * This method determines if the row and column entered by the user to make a move
	 * are valid positions in the game board that are currently unoccupied.
	 * @param row -> row integer entered by the user
	 * @param col -> column integer entered by the user
	 * @param board -> board object containing the game board that determines if the move is valid
	 * @return -> True or False, depending on if the move is valid
	 */
	public boolean isValid(int row, int col, Board board) {
		//integers entered are invalid, not a position on the board
		if (row < 1 || row > 3 || col < 1 || col > 3) {
			return false;
		}
		
		int pos = (row-1)*3 + (col-1);
		String [] moves = board.getGameBoard();
		
		//position in game board is occupied in that spot
		if (moves[pos] != " ") {
			return false;
		}
		return true;	
	}
	
	/**
	 * This method determines whether the current player is a winner; this method is always called
	 * at the end of someone's turn. The winner is found if there are 3 of the current player's
	 * moves in a row, across a column, or diagonally across.
	 * @param board -> board object containing the game board that determines if there is a winner
	 * @param turn -> String representing the current player's turn
	 * @return -> True or False, depending on if there is a winner
	 */
	public boolean isWinner(Board board, String turn) {
		winningSequence(board, turn);
		String winner = board.getWinner();
		return (winner == turn);
	}
	
	/**
	 * This method determines if there is a winning sequence on the board by checking all possible
	 * winning combinations for the current player and modifies the winner accordingly if there is a
	 * winner; otherwise, the winner variable within board is not modified.
	 * @param board -> board object containing the game board that determines if the current player is a winner
	 * @param turn -> String representing the current player's turn
	 */
	public void winningSequence(Board board, String turn) {
		String [] moves = board.getGameBoard();
		
		if (
		(moves[0] == turn && moves[1] == turn && moves[2] == turn) ||
		(moves[3] == turn && moves[4] == turn && moves[5] == turn) ||
		(moves[6] == turn && moves[7] == turn && moves[8] == turn) ||
		(moves[0] == turn && moves[3] == turn && moves[6] == turn) ||
		(moves[1] == turn && moves[4] == turn && moves[7] == turn) ||
		(moves[2] == turn && moves[5] == turn && moves[8] == turn) ||
		(moves[0] == turn && moves[4] == turn && moves[8] == turn) ||
		(moves[2] == turn && moves[4] == turn && moves[6] == turn)
		) {
			board.setWinner(turn);
		}	
	}
	
}
