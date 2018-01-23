#Andrew Chien



# Constants specify the possible board piece positions with "no player" or NONE,
# green player, and white player. 

NONE = '.'
GREEN = 'G'
WHITE = 'W'




class OthelloGameState():

    def __init__(self, board_rows: int, board_cols: int, first_turn: str, win_type: str) -> None:
        '''
        Initializes attributes for the Othello GameState
        '''
        self._rows = board_rows
        self._cols = board_cols
        self._turn = first_turn
        self._win_condition = win_type
        self._board = self.initial_blank_board()
        self._score = self.count_score()




    def initial_blank_board(self) -> [[str]]:
        '''
        Creates an initial blank board with '.' in every position and returns
        a 2D list of strings
        '''
        board = []
        
        for row in range(self._rows):
            board.append([])
            for col in range(self._cols):
                board[-1].append(NONE)

        return board
                



    def change_color(self) -> str:
        '''
        Returns the opposite color of the current turn value
        '''
        if self._turn == WHITE:
            return GREEN

        else:
            return WHITE




    def count_score(self) -> dict:
        '''
        Counts the score (number of pieces on the board at a given time) for
        both players and returns it in a dictionary
        '''
        white_score = 0
        green_score = 0

        for row in range(self._rows):
            for col in range(self._cols):
                
                if self._board[row][col] == WHITE:
                    white_score += 1
                elif self._board[row][col] == GREEN:
                    green_score += 1
                else:
                    pass
                
        return {WHITE: white_score, GREEN: green_score}



    

    def update_score(self) -> None:
        '''
        Updates the score dictionary to the current number of pieces for
        each player on the game board
        '''
        self._score = self.count_score()





    def calculate_winner(self) -> str:
        '''
        Compares the scores of the White and Black players to determine a winner
        and returns the winner (otherwise returns NONE if there is a tie)
        '''
        if self._score[GREEN] == self._score[WHITE]:
            return 'NONE'
        
        elif self._win_condition == 'MOST PIECES WIN':

            if self._score[GREEN] > self._score[WHITE]:
                return 'GREEN'

            else:
                return 'WHITE'

        #win condition equal to 'FEWEST PIECES WIN'
        else:

            if self._score[GREEN] < self._score[WHITE]:
                return 'GREEN'

            else:
                return 'WHITE'





    def make_move(self, row: int, col: int) -> bool:
        '''
        Makes a move on the board according to the previous user input
        for rows and columns and returns True/False depending on whether
        a valid move was made
        '''
        
        if self._is_valid_row_number(row) and self._is_valid_column_number(col):

            if self._board[row][col] == NONE and self.find_all_moves(row, col):
                
                #places current player's piece at position selected 
                self._board[row][col] = self._turn

                #flips opposing player's pieces accordingly
                self.change_all_pieces(row, col)

                #changes the current player's turn
                self._turn = self.change_color()
                
                return True
                
            #there is a piece at the board position or no moves can be made at that position
            else:
                
                return False
            
        #the user did not input a valid row and column number within the range of the board
        else:
            
            return False
                


                


    def change_all_pieces(self, row: int, col: int) -> None:
        '''
        Changes the color of the pieces in all directions to the color of the
        placed piece if a piece of the opposite color (or series of pieces)
        are between the placed piece and another piece of the same color
        '''
        row_increment = -1

        while row_increment <= 1:

            #ensures col_increment restarts at -1 whenever row_increment += 1
            col_increment = -1

            while col_increment <= 1:

                if row_increment == 0 and col_increment == 0:
                    pass

                else:

                    if self.find_single_move(row, col, row_increment, col_increment):
                        
                        #changes pieces in a single direction
                        self.change_linear_pieces(row, col, row_increment, col_increment)
                   
                col_increment += 1


            row_increment += 1

                
                
                    



    def change_linear_pieces(self, row: int, col: int, row_increment: int, col_increment: int) -> None:        
        '''
        Changes all of the board pieces in a certain direction to the current
        player's color
        '''
        row_num = row + row_increment
        col_num = col + col_increment

        opposite_color = self.change_color()

        #changes color of pieces while they are the opposite color
        while self._board[row_num][col_num] == opposite_color:
            self._board[row_num][col_num] = self._turn

            row_num += row_increment
            col_num += col_increment
        


        


    def winner(self) -> str:
        '''
        Checks if there is a winner when there are no more valid moves that
        can be made on the board and returns the winner (BLACK or WHITE or NONE)
        '''
        winner = ''

        #no more valid moves for the current player
        if not self.valid_move_left():

            self._turn = self.change_color()
            
            #no more valid moves for the other player
            if not self.valid_move_left():

                winner = self.calculate_winner()

        return winner


            


    def valid_move_left(self) -> bool:
        '''
        Checks if there are any valid moves left for the current player's turn,
        returns True or False accordingly
        '''
        for row in range(self._rows):
            
            for col in range(self._cols):

                #attempts to find possible moves at board positions with a '.'
                if self._board[row][col] == NONE:
                    if self.find_all_moves(row, col):
                        return True
                    
        return False





    def find_all_moves(self, row: int, col: int) -> bool:
        '''
        Checks if there are any valid moves left for the current player in
        every direction and returns True or False accordingly
        '''
        return self.find_single_move(row, col, 0, 1) \
            or self.find_single_move(row, col, 1, 1) \
            or self.find_single_move(row, col, 1, 0) \
            or self.find_single_move(row, col, 1, -1) \
            or self.find_single_move(row, col, 0, -1) \
            or self.find_single_move(row, col, -1, -1) \
            or self.find_single_move(row, col, -1, 0) \
            or self.find_single_move(row, col, -1, 1)





    def find_single_move(self, row: int, col: int, row_increment: int, col_increment: int) -> bool:
        '''
        Checks if there is a move available in a certain direction for the
        current player and returns True or False accordingly.
        A valid available move occurs when the opposing player's piece (or
        a series of pieces) is adjacent to the current player's piece followed
        by the current player's piece all in a single direction
        '''
        row_num = row + row_increment
        col_num = col + col_increment
        
        opposite_piece = False

        while self._is_valid_row_number(row_num) and self._is_valid_column_number(col_num):
            adjacent_pos = self._board[row_num][col_num]

            
            if adjacent_pos == self._turn:
                #determines whether the opposing player's piece is sandwiched
                #between two of the current player's pieces
                if opposite_piece:
                    return True
                
                return False


            elif adjacent_pos == self.change_color():
                opposite_piece = True


            #indicates the adjacent_pos is equal to '.' and a valid move is not possible 
            else:
                return False

            row_num += row_increment
            col_num += col_increment

        # indicates the last valid piece in the direction was the opposite color
        # if while loop ends without returning anything, no move could be made
        # as the piece could not be sandwiched
        return False
        




    def _is_valid_row_number(self, row: int) -> bool:
        '''
        Returns True if the given row number is valid; returns False otherwise
        '''
        return (0 <= row < self._rows)





    def _is_valid_column_number(self, col: int) -> bool:
        '''
        Returns True if the given column number is valid; returns False otherwise
        '''
        return (0 <= col < self._cols)

                    
    



            
        
