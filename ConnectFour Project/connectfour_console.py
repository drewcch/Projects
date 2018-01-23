#Andrew Chien, 75644982
#Xinhao Liang, 62113399

import connectfour
import connectfour_modules



def console_user_interface() -> None:
    '''Runs console user interface'''
    print('Welcome to Connect-Four!')
    print('One player will use Red pieces designated by R \n' +
          'while another player will use Yellow pieces designated by Y')
    new_GameState = connectfour.new_game()
    print('Here is the starting game board: \n')
    connectfour_modules.display_game_board(new_GameState.board)
    run_turn(new_GameState)



def run_turn(game_state: connectfour.GameState) -> int:
    '''Runs a turn by first checking if there is a winner; otherwise the user
    is asked whether to drop or pop a piece'''

    #initialize value where there is no winner when starting a game
    winner = False     

    while not winner:

        #checks whether the winning player is NONE from connectfour
        if connectfour.winner(game_state) == connectfour.NONE:      
            
            print("It is currently Player " + connectfour_modules.values_to_symbols(game_state.turn) + "'s turn")

            game_state = drop_or_pop(game_state)
            
            
        #checks whether player R has won the game by comparing with RED from connectfour                
        elif connectfour.winner(game_state) == connectfour.RED:   
            print('RED player has won!')

            #winner has been declared, ending loop and the game
            winner = True   


        #player Y wins the game if connectfour.winner is neither NONE nor RED 
        else:               
            print('YELLOW player has won!')
            winner = True



def drop_or_pop(game_state: connectfour.GameState) -> connectfour.GameState:
    '''Asks user to drop or pop a piece from the board.
    Loops through until a valid command is entered'''
    while True:
        
        choice = input('Enter D to drop a piece into a column or P to pop a piece out of a column: ')

        #Checks for a valid column and goes to run drop in connectfour modules to drop a piece
        if choice == 'D':
            column = check_valid_column()    
            game_state = connectfour_modules.run_drop(game_state, column)   
            return game_state


        #Checks for a valid column and goes to run pop in connectfour modules to pop a piece
        elif choice == 'P':
            column = check_valid_column() 
            game_state = connectfour_modules.run_pop(game_state, column)   
            return game_state


        #Invalid command, loops through to ask user again
        else:
            print('ERROR, INVALID OPTION: PLEASE CHOOSE AGAIN')
    

    

def check_valid_column() -> int:
    '''Asks user to input a column and number, checks if the column number is
    a integer and within the range of columns, then outputs the column number.
    Loops otherwise until the column inputed is valid.'''
    
    while True:
        column = input('Choose a column to drop a piece into: ')
        
        #checks whether the column entered is a number, loops otherwise
        if not column.isdigit():                
            print('PLEASE ENTER A NUMBER WHEN CHOOSING A COLUMN')


        #checks whether column number is valid 
        elif int(column) > 0 and int(column) <= connectfour.BOARD_COLUMNS:
            return int(column)          


        #loops when number entered is not in range of columns
        else:               
            print('PLEASE CHOOSE A COLUMN BETWEEN 1 TO 7')






if __name__ == '__main__':
    console_user_interface()
