#Andrew Chien



import othello_game_logic





def set_game_rules() -> othello_game_logic.GameState:
    '''
    Defines the game rules of Othello according to user input
    '''
    
    #number of rows/cols is an even integer between 4 and 16 (inclusive)
    rows = int(input())
    cols = int(input())

    #initializes whether player 'B' or 'W' will start the game
    first_turn = input()

    #initializes win condition for the game 
    win_type = input()

    #saves user input for the initial board into a list 
    board = initial_board(rows)

    #creates GameState object
    GameState = othello_game_logic.GameState(rows, cols, first_turn, win_type, board)
  
    return GameState







def initial_board(rows: int) -> list:
    '''
    Using the number of rows previously inputted by the user, takes in the user
    input for an initial board and returns a board list
    '''
    board = []

    for row in range(rows):
        user_input = input()
        board.append(user_input.split())

    return board






def user_move_input() -> list:
    '''
    Takes user input for two numbers and places them into a list containing
    a row number and a column number
    '''
    
    row_col_list = []
    
    position = input().strip()

    #turns user str input into a list
    piece_position = position.split()

    row = int(piece_position[0])-1
    row_col_list.append(row)
    
    col = int(piece_position[1])-1
    row_col_list.append(col)

    return row_col_list
    
         
    



def run_game(GameState: othello_game_logic.GameState) -> None:
    '''
    Runs the game of Othello
    '''
    
    # winner is set as '' by default if there are valid moves to be made, meaning
    # the game is not over
    while GameState.winner() == '':
        
        GameState.display_score()
    
        GameState.display_game_board()

        GameState.display_turn()

        valid_move = False

        #loops until the current player inputs a valid move
        while not valid_move:
            
            valid_move = GameState.make_move(user_move_input())

    # goes to the show winner function once no more moves can be made
    # from either player and winner has been chosen (BLACK, WHITE, or NONE)
    show_winner(GameState)




    

def show_winner(GameState: othello_game_logic.GameState) -> None:
    '''
    Displays the winner of Othello if there is one
    '''
    GameState.display_score()
    
    GameState.display_game_board()

    print('WINNER: ' + GameState.winner())







def user_interface() -> None:
    '''
    Runs the user interface of Othello
    '''
    
    print('FULL')
    
    GameState = set_game_rules()

    run_game(GameState)







if __name__ == '__main__':
    user_interface()
    
