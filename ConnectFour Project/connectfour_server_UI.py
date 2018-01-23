#Andrew Chien, 75644982

import connectfour_server_protocol
import connectfour
import connectfour_modules
import socket





def user_interface() -> None:
    '''Runs the network version of connectfour'''
    host = read_host()
    port = read_port()

    print('Connecting to {} (port {})...'.format(host, port))
    
    try:
        connection = connectfour_server_protocol.connectfourserver(host, port)

        try:
            username = read_username()
            print(connectfour_server_protocol.welcome(connection, username))
            status = connectfour_server_protocol.send_and_receive(connection, 'AI_GAME')
            print('SERVER IS ' + status)
            print()
            run_connectfour_game(connection, status)

        finally:
            connectfour_server_protocol.close(connection)
 
    except socket.gaierror:
        print('SORRY, INVALID HOST NAME WAS ENTERED')
        

        


def run_connectfour_game(connection: connectfour_server_protocol.connectfourConnection, server_status: str) -> None:
    '''Runs the network version of the Connectfour Game'''
    print('Welcome to the network version of Connect-Four!')
    print('The client will use Red pieces designated by R \n' +
          'and the server will use Yellow pieces designated by Y')
    new_GameState = connectfour.new_game()
    print('Here is the starting game board: \n')
    connectfour_modules.display_game_board(new_GameState.board)
    print("The game will begin with the client's turn: \n")

    #initializes value so there is no winner at the start of the game
    winner = False

    while not winner:

        #checks if it is the user's turn
        if server_status == 'READY':
    
            user_turn = client_turn(new_GameState, connection)
            new_GameState = user_turn[0]
            server_status = user_turn[1]
            
            #user sent an invalid command violating protocol, forces connection to disconnect
            if server_status == 'ERROR':
                print('THE USER DID NOT MAKE A VALID MOVE DURING ITS TURN, CONNECTION WILL NOW CLOSE')
                break


        #checks to see if the user made a valid move to make it the server's turn                            
        elif server_status == 'OKAY':

            print("It is now the server's turn: ")
            server_turn = AI_turn(new_GameState, connection)
            server_GameState = server_turn[0]
            server_status = server_turn[1]

            #server made an invalid command violating protocol, forces connection to disconnect
            if server_GameState == new_GameState:
                print('THE SERVER DID NOT MAKE A VALID MOVE DURING ITS TURN, CONNECTION WILL NOW CLOSE')
                break
            
                
            else:
                new_GameState = server_GameState
                print("It is now the client's turn: ")
                
                
                
        #checks if client has won the game to end loop
        elif server_status == 'WINNER_RED':
            print('THE CLIENT HAS WON')
            winner = True


            
        #checks if server has won the game to end loop
        elif server_status == 'WINNER_YELLOW':
            print('THE SERVER HAS WON')
            winner = True
            

        #checks if user made an invalid move that did not violate the protocol
        elif server_status == 'INVALID':
            print("CLIENT HAS MADE AN INVALID MOVE: IT IS STILL THE CLIENT'S TURN: ")

            #used to update the status of the server from INVALID to READY
            #so user can make another move
            server_status = connectfour_server_protocol.send_and_receive(connection, '')     

            



def client_turn(game_state: connectfour.GameState, connection: connectfour_server_protocol.connectfourConnection) -> (connectfour.GameState, str):
    '''Performs the client's turn by getting his/her input.
    Returns a tuple consisting of the updated GameState as well as the
    server's status in the form of a str.'''
    
    client_move = user_pop_or_drop(game_state)
    game_state = client_move[0]
    server_status = connectfour_server_protocol.send_and_receive(connection, client_move[1])
    return (game_state, server_status)





def AI_turn(game_state: connectfour.GameState, connection: connectfour_server_protocol.connectfourConnection) -> (connectfour.GameState, str):
    '''Performs the server's turn and returns a tuple consisting of the updated GameState
    along with the server's status in the form of a str.
    If an invalid command/column is entered, the server did not make a valid move
    and the original game state is returned to disconnect the connection.'''
    
    #used to receive the server's move, assigns it into a str
    server_move = connectfour_server_protocol.send_and_receive(connection, '')     
    column = server_move[-1]

    #checks if server column is an integer
    if column.isdigit():
        server_column = int(server_move[-1])


        #checks if server column is within the range of columns of the board
        if server_column > 0 and server_column <= connectfour.BOARD_COLUMNS:
            

            if server_move[0] == 'D':
                game_state = connectfour_modules.run_drop(game_state, server_column)

                #used to update the status of the server after making its move
                server_state = connectfour_server_protocol.send_and_receive(connection, '')
                
                return (game_state, server_state)
            

            elif server_move[0] == 'P':
                game_state = connectfour_modules.run_pop(game_state, server_column)
                server_state = connectfour_server_protocol.send_and_receive(connection, '')
                return (game_state, server_state)

            
            #server made an invalid command that was neither POP nor DROP
            else:           
                return (game_state, server_move)

            
        #server did not enter a column number within the range of the board
        else:           
            return (game_state, server_move)

        
    #server did not enter a valid column number that could be changed in an int
    else:          
        return (game_state, server_move)

    


        
def user_pop_or_drop(game_state: connectfour.GameState) -> (connectfour.GameState, str):
    '''Asks user to drop or pop a piece from the board by entering D or P.
    The connection is disconnected if anything other than D or P are entered to
    make a move or if anything other than an integer is entered for the column.
    Otherwise a tuple is returned with the updated gamestate as well as the move
    made (consists of either DROP or POP followed by the column number)'''
    
    choice = input('Enter D to drop a piece into a column or P to pop a piece out of a column: ')
    column = input('Choose a column to drop or pop a piece into: ')


    #Checks if command is valid, column is valid
    
    if choice == 'D' and user_select_column(column):   
        game_state = connectfour_modules.run_drop(game_state, int(column))  
        return (game_state, 'DROP ' + column)



    #Checks for valid move but invalid column selected by user
    #Server status will be INVALID if column is an integer or ERROR if column is not an integer
    
    elif choice == 'D' and not user_select_column(column):
        return (game_state, 'DROP ' + column)               



    #Check for valid command and column
    
    elif choice == 'P' and user_select_column(column):
        game_state = connectfour_modules.run_pop(game_state, int(column))    
        return (game_state, 'POP ' + column)


    #Check for valid move, invalid column
    
    elif choice == 'P' and not user_select_column(column):
        return (game_state, 'POP ' + column)        

    

    #Invalid move made by user, server status is ERROR
    
    else:
        print('INVALID MOVE MADE')
        return (game_state, 'INVALID MOVE MADE')            





def user_select_column(column: str) -> bool:
    '''Returns true if column number is an integer and if it is between 1 and 7.
    If the column is not an integer, the subsequent DROP/POP command will
    produce ERROR from the server, disconnecting the connection.
    If the column is an integer but not within the range of 1 and 7, the
    subsequent DROP/POP command will produce INVALID from the server,
    therefore asking the user to try making another move.'''
    try:
        
        col = int(column)
        
        #if valid column is entered within the range of columns
        
        if col > 0 and col <= connectfour.BOARD_COLUMNS:
            return True                                     

        
        #invalid column is entered, but column is still an integer
        #so connection is not disconnected
        
        else:
            return False                        



    #invalid column is entered that is not an integer, will disconnect connection
        
    except ValueError:
        return False                            









####Functions below (excluding if name main) are used prior to beginning
####the connectfour game, such as reading the host, port, and username    



def read_host() -> str:
    '''Asks user to input a valid host and returns host if it is not left blank'''
    while True:
        host = input('Please input a Host: ').strip()

        if len(host) == 0:
            print('Please specify a host either by name or by an IP address')
            
        else:
            return host


        

def read_port() -> int:
    '''Asks user to input a valid port and returns port if it is an integer'''
    while True:
        try:
            port = int(input('Please input a Port: ').strip())

            if port < 0 or port > 65535:
                print('Ports must be an integer between 0 and 65535')
            else:
                return port

        except ValueError:
            print('Ports must be an integer between 0 and 65535')
            



def read_username() -> str:
    '''Asks user to input a valid username to play connectfour and returns
    username if there are not white spaces and is not left blank'''
    while True:
        username = input('Username: ').strip()

        if len(username) == 0:
            print('Please specify a username')

        elif not check_for_white_space(username):
            print('Please enter a valid username')

        else:
            return username




def check_for_white_space(username: str) -> bool:
    '''Checks for white spaces in username and returns True if no white spaces
    are found; otherwise returns false'''
    for char in username:
        if char == ' ' or char == '\t':
            return False
        
    return True







    

if __name__ == '__main__':
    user_interface()

