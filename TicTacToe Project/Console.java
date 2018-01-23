package TicTacToe;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The console class is responsible for displaying output onto the standard console such as 
 * the game board, important game information such as who has won or whose turn it is, as well
 * as prompting the user to enter in commands for the row and column. 
 */
public class Console {
	
	/**
	 * The following method displays a welcome message and prints out the starting board
	 * to the user, which will be blank.
	 */
	public static void consoleUI() {
		System.out.println("Welcome to Tic-Tac-Toe!\n"
				+ "The user will use \"X\" to make a move and the AI will use \"O\" to make a move");
		System.out.println("Here is the starting gameboard:\n");
		Board board = new Board();
		printBoard(board);
		System.out.println("\nTo make a move, the user will specify the row (between 1 to 3)"
				+ " and the column (between 1 to 3) of the desired move");
		runUserTurn(board);

	}
	
	/**
	 * This method prints the current state of the game board as well as the current 
	 * moves held within the game_board instance variable of the Board object
	 * @param board -> represents a board object that contains the game board with the moves 
	 */
	public static void printBoard(Board board) {
		String[] game_board = board.getGameBoard();
		System.out.println("+---+---+---+");
		System.out.println("| "+ game_board[0] + " | " + game_board[1] + " | " + game_board[2] + " |");
		System.out.println("+---+---+---+");
		System.out.println("| "+ game_board[3] + " | " + game_board[4] + " | " + game_board[5] + " |");
		System.out.println("+---+---+---+");
		System.out.println("| "+ game_board[6] + " | " + game_board[7] + " | " + game_board[8] + " |");
		System.out.println("+---+---+---+");
	}
	
	/**
	 * This method runs the user's turn of Tic-Tac-Toe. The user will be prompted to make a move 
	 * and if the move is valid, the the board object will be updated to reflect changes in the 
	 * game board. A winner is then checked if there is one to end the game or if there are no 
	 * more remaining moves left on the board, before going to the AI's turn.
	 * @param board -> represents a board object that contains the game board to be updated
	 */
	public static void runUserTurn(Board board) {
		GameLogic logic = new GameLogic();
		boolean valid = false;
			
		while (!valid) {
			System.out.println("\nIt is currently the user's turn \n");
			int [] user_move = getUserMove();
			int row = user_move[0];
			int col = user_move[1];
			
			if (logic.isValid(row, col, board)) {
				board.userMove(row, col);
				System.out.println();
				printBoard(board);
				valid = true;
			}
			else {
				System.out.println("\nThe position indicated by the entered row and column"
					+ " is either invalid or already occupied\n");
				printBoard(board);
			}
		}
		    
		ArrayList<Integer> avail_moves = board.getEmptyIndexes();	
		if (avail_moves.isEmpty() || logic.isWinner(board, board.getTurn())) {
			displayWinner(board);
		}
			
		else {
			board.updateTurn();
			runAITurn(board);
		}
	}
	
	/**
	 * This method runs the AI's turn of Tic-Tac-Toe. In order for the AI to make a move, 
	 * a copy of the board object is made so an algorithm can be performed to determine the
	 * AI's next move through the AIMove method. The game board is then updated with this AI
	 * move and a winner is then checked if there is one to end the game or if there are no 
	 * more remaining moves left on the board, before going to the user's turn.
	 * @param board -> represents that will be copied and contains the game board to be updated
	 */
	public static void runAITurn(Board board) {
		GameLogic logic = new GameLogic();
		AILogic ai = new AILogic();
		
		System.out.println("\nIt is currently the AI's turn \n");
		
		Board newboard = new Board(board);
		int[] ai_Move = ai.AIMove(newboard, newboard.getTurn());
		board.algorithmGameMove(ai_Move[0], board.getTurn());
		printBoard(board);
		
		ArrayList<Integer> avail_moves = board.getEmptyIndexes();
		if (avail_moves.isEmpty() || logic.isWinner(board, board.getTurn())) {
			displayWinner(board);
		}
		
		else {
			board.updateTurn();
			runUserTurn(board);
		}
	}
	
	
	/**
	 * This method displays the winner of the Tic-Tac-Toe game if there is one onto the 
	 * standard console; otherwise, it will display the game has ended in a tie. 
	 * @param board -> board object that contains the winner instance variable
	 */
	public static void displayWinner(Board board) {
		String winner = board.getWinner();
		
		if (winner == "X") {
			System.out.println("\nThe Player has won!");
		}
		
		else if (winner == "O" ){
			System.out.println("\nThe AI has won!");
		}
		
		else {
			System.out.println("\nThe Game Ended in a Tie!");
		}		
	}
	/**
	 * This method continuously prompts the user to enter an integer representing the row of
	 * the move and an integer representing the column of the move. This method does not check
	 * if these integers are valid, only that what the user inputs are integers.
	 * @return -> integer array containing the row int in one index and the col int in the other
	 */
	public static int[] getUserMove () {
		int row, col;
		
		//continuously prompt the user to enter an integer representing the row
		while (true) {
			Scanner rw = new Scanner(System.in);
			System.out.print("Please enter a row number between 1 to 3 (integer): ");
			try {
				row = rw.nextInt();
				break;
			}
			catch (Exception InputMismatchException) {
				System.out.println("\nAn integer was not entered");
			}
		}
	    
		//continuously prompt the user to enter an integer representing the column
		while (true) {
			Scanner cl = new Scanner(System.in);
			System.out.print("Please enter a column number between 1 to 3 (integer): ");
			try {
				col = cl.nextInt();
				break;
			}
			catch (Exception InputMismatchException) {
				System.out.println("\nAn integer was not entered");
			}
		}
		
		int[] result = {row,col};
		return result;
	}
	
	/**
	 * This is the main method that runs the consoleUI, which begins the Tic-Tac-Toe game
	 */
	public static void main(String[] args) {
		consoleUI();
	}

}
