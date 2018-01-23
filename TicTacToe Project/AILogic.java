package TicTacToe;
import java.util.ArrayList;

/**
 * The AI Logic class implements the AI logic by calculating a move for the AI that will prevent 
 * them from losing and uses various methods for this task. This is done by looking ahead into the
 * Tic-Tac-Toe game and calculating both the AI's and the user's moves that will bring them to
 * victory.
 */
public class AILogic {
	
	/**
	 * This method finds the highest scoring move for the AI (leads the AI to win) or the 
	 * lowest scoring move for the user (leads the user to win) in subsequent method calls and returns 
	 * the index in the game board for this move. This is done so the AI can make the highest scoring 
	 * move in its turn while taking in account the possibility of the user making moves that will lead 
	 * them to victory so as to counteract this possibility. This algorithm looks ahead and plays the 
	 * Tic-Tac-Toe game on the copy of the game board so as to not impact the actual game board.
	 * @param board -> board object containing the game board
	 * @param turn -> String representing the current player's turn 
	 * @return -> An integer array containing the index of the move and the score
	 */
	public int [] AIMove(Board board, String turn) {
		//Finds the empty indexes in the game board
		ArrayList<Integer> avail_moves = new ArrayList<Integer>();
		avail_moves = board.getEmptyIndexes();
		
		//Determines whether the theoretical game has reached an ending state
		int[] end = endingState(board, avail_moves);
		//Determines whether the game has ended yet
		if (end[1] != Integer.MIN_VALUE) {
			return end;
		}
		
		//Creates move set for the current player containing all the possible moves
		ArrayList<int[]> moveset = makeMoveSet(board,avail_moves,turn);
		int [] final_move = new int[2];
		
		/* Loops to find the highest scoring move in the moveset if it is the AI's turn and
		 * remembers this move to return later. 
		 */
		if (turn == "O") {
			int score = Integer.MIN_VALUE;
			for (int i = 0; i < moveset.size(); i++) {
				int [] move = moveset.get(i);
				if (move[1] > score) {
					final_move = move;
					score = move[1];
				}
			}
		}
		
		/* Loops to find the lowest scoring move in the moveset if it is the user's turn and
		 * remembers this move to return later. 
		 */
		else {
			int score = Integer.MAX_VALUE;
			for (int i = 0; i < moveset.size(); i++) {
				int [] move = moveset.get(i);
				if (move[1] < score) {
					final_move = move;
					score = move[1];
				}
			}
		}
		
		//Returns the resulting move that will bring the AI or user closest to victory
		return final_move;	
	}
	
	/**
	 * This method determines if the theoretical game has reached an ending state (win, lose, tie)
	 * defined by the following: -1 if the player has won, 0 if it is a draw, 1 if the AI 
	 * has won, or Integer.MIN_VALUE if the game has not yet ended. These values are then 
	 * returned as the second index of an integer array, whose first index is -1 to indicate 
	 * this is not an actual available index in game board as this is the ending state of the game.
	 * @param board -> Board object containing the game board
	 * @param avail_moves -> ArrayList of integers containing the available indexes in the game board
	 * @return -> An integer array representing the ending result of the game
	 */
	public int[] endingState(Board board, ArrayList<Integer> avail_moves) {
		GameLogic logic = new GameLogic();
		
		//Tests if the player has won
		if (logic.isWinner(board, "X")) {
			//Resets winner to " " as algorithm is looking ahead, has not actually made the move yet
			board.setWinner(" ");
			int [] result = {-1,-1};
			return result;
		}
		
		//Tests if the AI has won
		else if (logic.isWinner(board, "O")) {
			//Resets winner to " "
			board.setWinner(" ");
			int [] result = {-1,1};
			return result;
		}
		
		else if (avail_moves.size() == 0){
			int [] result = {-1,0};
			return result;
		}
		
		else {
			int [] result = {-1,Integer.MIN_VALUE};
			return result;
		}
	} 
	
	/**
	 * This method loops through the available moves in the game board to create a move set for 
	 * the current player. This move set takes in account the opposing player making the highest scoring
	 * (for the AI) or lowest scoring (for the user) move to bring them closest to victory and collects 
	 * the resulting scores from calling AIMove into an ArrayList. 
	 * @param board -> board object containing the game board
	 * @param avail_moves -> ArrayList of integers containing the available indexes in the game board
	 * @param turn -> String representing the current player's turn
	 * @return -> ArrayList containing length 2 integer arrays, which contain the index and score of each move in the moveset
	 */
	public ArrayList<int[]> makeMoveSet(Board board, ArrayList<Integer> avail_moves, String turn) {
		ArrayList<int[]> moveset = new ArrayList<int[]>();
		for (int i = 0; i < avail_moves.size(); i++) {
			//creates a move array
			int[] move = new int[2];
			int index = avail_moves.get(i);
			move[0] = index;
			//temporarily updates the game board to reflect the current player making a move
			board.algorithmGameMove(index, turn);
			
			if (turn == "O") {
				//opposing player's turn to make a move to look ahead into the Tic-Tac-Toe game
				int [] final_move = AIMove(board, "X");
				//sets the score according to the ending state
				move[1] = final_move[1];
			}
			
			else {
				//opposing player's turn
				int [] final_move = AIMove(board, "O");
				//sets the score according to the ending state
				move[1] = final_move[1];
			}
			
			//reverts the index in game board back to empty as the algorithm was looking ahead, did not actually make a move
			board.algorithmGameMove(index," ");
			moveset.add(move);
		}
		return moveset;
	}
	
}
