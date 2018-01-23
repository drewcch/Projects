#Andrew Chien

import connectfour



def values_to_symbols(value: int) -> str:
    '''Takes in the value at a board position and returns *, R, and
    Y for NONE, Red, and Yellow, respectively'''

    #dictionary to translate each value from connectfour into its respective symbol
    symbols_dict = {connectfour.NONE: '*', connectfour.RED: 'R', connectfour.YELLOW: 'Y'}    

    return symbols_dict[value]


    
def display_game_board(board: [[int]]) -> None:
    '''Displays the current state of the game board'''

    #Labels each column of the board from 1 to the value of BOARD_COLUMNS  
    for column_num in range(connectfour.BOARD_COLUMNS):     
        print(column_num+1, end = ' ')
    print()

    #prints the game board
    for row in range(connectfour.BOARD_ROWS):          
        for col in range(connectfour.BOARD_COLUMNS):
            
            #replaces 0's, 1's, and 2's in board with *, R, Y 
            board_symbol = values_to_symbols(board[col][row])     
            print(board_symbol, end = ' ')
            
        print()

    print()




def run_drop(game_state: connectfour.GameState, column: int) -> connectfour.GameState:
    '''Drops a piece into a selected column if it is valid'''

    #tries to drop a piece into selected column, updates the game board if it is valid
    try:                        
        new_GameState = connectfour.drop(game_state, column-1)  
        display_game_board(new_GameState.board)

        #Returns a new gamestate with the opposite player
        return new_GameState        

        
    except connectfour.InvalidMoveError:        
        print('COLUMN IS FULL')
        
        #Returns the same gamestate with the same player
        return game_state       




def run_pop(game_state: connectfour.GameState, column: int) -> connectfour.GameState:
    '''Pops a piece from the bottom of a selected column if it is valid''' 
    
    try:
        new_GameState = connectfour.pop(game_state, column-1)
        display_game_board(new_GameState.board)
        return new_GameState
        

    except connectfour.InvalidMoveError:
        print('COLUMN DOES NOT HAVE YOUR PIECE AT THE BOTTOM ROW')
        return game_state        

    



