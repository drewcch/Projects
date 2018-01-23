#Andrew Chien



import tkinter
import othello_game_logic
import othello_game_rules



DEFAULT_FONT = ('Georgia', 14)


    
class OthelloApplication:

    def __init__(self):

        self._root_window = tkinter.Tk()

        self._root_window.configure(background='black')

        #used to determine whether a game has begun --> will say 'Start'
        self._begin_game = ''

        #used to determine whether the initial board is set up --> will become True
        self._set_board = False

        ## used to determine how many initial piece colors have been set up;
        ## the max value is 2 to indicate green and white pieces have been placed
        self._set_board_colors = 0
        



        self._canvas = tkinter.Canvas(
            master = self._root_window,
            width = 600, height = 600,
            relief = 'flat', highlightthickness = 0,
            background = '#AA00FF')

        self._canvas.grid(
            row = 2, column = 0, padx = 10, pady = 10,
            sticky = tkinter.N + tkinter.S + tkinter.W + tkinter.E)




        title_frame = tkinter.Frame(
            master = self._root_window,
            background = '#00AAFF')

        title_frame.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)
        
        title_header = tkinter.Label(
            master = title_frame, text = 'FULL',
            font = DEFAULT_FONT, relief = 'sunken',
            background = 'yellow')

        title_header.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)





        self._frame_header = tkinter.Frame(
            master = self._root_window,
            background = '#00FF00')

        self._frame_header.grid(
            row = 3, column = 0, padx = 5, pady = 5,
            sticky = tkinter.S)
            
        self._start_game_button = tkinter.Button(
            master = self._frame_header, text = 'Start New Game',
            font = DEFAULT_FONT, command = self._start_button_pressed)

        self._start_game_button.grid(
            row = 3, column = 0, padx = 5, pady = 5,
            sticky = tkinter.S)
        


        

            

        self._canvas.bind('<Configure>', self._on_canvas_resized)
        self._canvas.bind('<Button-1>', self._on_canvas_pressed)


        self._root_window.rowconfigure(0, weight = 1)
        self._root_window.columnconfigure(0, weight = 1)










        
##### Methods below are involved with setting up buttons and dealing with
##### user interaction with buttons 

    def _start_button_pressed(self) -> None:
        '''
        Begins a new game by summoning a dialog box for the user to select
        the rules. Once a game has started, the _start_button is replaced by
        a _end_button.
        '''
        
        self._new_game_options()

        if self._begin_game != '':

            ## removes the _start_game_button while keeping _frame_header;
            ## this then is replaced by the _end_game_button to indicate the
            ## game has begun and the user can end the current game
            
            self._start_game_button.grid_remove()
            
            self._end_game_button = tkinter.Button(
                master = self._frame_header, text = 'End Game',
                font = DEFAULT_FONT, command = self._end_button_pressed)

            self._end_game_button.grid(
                row = 3, column = 0, padx = 5, pady = 5,
                sticky = tkinter.S)




    def _on_canvas_pressed(self, event: tkinter.Event) -> None:
        '''
        Handles the user interaction with the canvas when it is clicked on.
        The canvas is divided up into cells and a piece of the current
        user's color will be placed depending on whether the game has begun.
        '''
        
        canvas_width = self._canvas.winfo_width()
        canvas_height = self._canvas.winfo_height()

        board_cell_width = canvas_width/self._cols
        board_cell_height = canvas_height/self._rows

        cell_row = int(event.y/board_cell_height)
        cell_col = int(event.x/board_cell_width)


        #indicates whether the initial pieces have been set or not
        if not self._set_board:

            if self._game_state._board[cell_row][cell_col] == othello_game_logic.NONE:

                self._game_state._board[cell_row][cell_col] = self._game_state._turn

                self._draw_board()

        else:

            self._run_turn(cell_row, cell_col)

 
        
            

    def _selection_button_pressed(self) -> None:
        '''
        Handles the user interaction of changing the colors of the initial
        pieces placed on the board (i.e. from selecting green initial pieces
        to white pieces). If both colors have been placed, it will proceed
        to the game_set_up method.
        '''
        
        self._selection_button.grid_remove()
        
        self._set_board_colors += 1

        #indicates whether both colors have had their initial pieces placed
        if self._set_board_colors < 2:

            self._game_state._turn = self._game_state.change_color()

            self._set_up_selection_button()

        else:

            self._game_state._turn = self._game_state.change_color()

            self._game_set_up()





    def _set_up_selection_button(self) -> None:
        '''
        Sets up the selection button, which the user can press to transition
        from selecting the initial pieces for one color to another.
        '''
        
        self._selection_frame = tkinter.Frame(
            master = self._root_window,
            background = '#990000')

        self._selection_frame.grid(
            row = 1, column = 0, padx = 2, pady = 2,
            sticky = tkinter.N)

        
        self._selection_button = tkinter.Button(
            master = self._selection_frame,
            text = 'SELECT INITIAL PIECES FOR ' +
                self._colors_dict[self._game_state._turn].upper() + ': CLICK HERE TO FINISH',
            font = ('Georgia', 11), command = self._selection_button_pressed)


        self._selection_button.grid(
            row = 1, column = 0, padx = 2, pady = 2,
            sticky = tkinter.N)

           
            
                  
##### _end_button_pressed, _yes_button_pressed, and _no_button_pressed
##### deal with the situation when the user wants to end the current game


        
    def _end_button_pressed(self) -> None:
        '''
        Creates a end_window where the user will be given the
        option to end the current game.
        '''
        self._end_window = tkinter.Toplevel()



        end_header = tkinter.Label(
            master = self._end_window, text = 'End Game?',
            font = DEFAULT_FONT, relief = 'sunken')

        end_header.grid(
            row = 0, column = 0, padx = 10, pady = 10,
            sticky = tkinter.N)

        

        yes_button = tkinter.Button(
            master = self._end_window, text = 'YES',
            font = DEFAULT_FONT, command = self._yes_button_pressed)

        yes_button.grid(
            row = 1, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)



        no_button = tkinter.Button(
            master = self._end_window, text = 'NO',
            font = DEFAULT_FONT, command = self._no_button_pressed)

        no_button.grid(
            row = 1, column = 1, padx = 5, pady = 5,
            sticky = tkinter.N)





        self._end_window.rowconfigure(0, weight = 1)
        self._end_window.columnconfigure(0, weight = 1)
        self._end_window.grab_set()
        self._end_window.wait_window()



        

    def _yes_button_pressed(self) -> None:
        '''
        Closes the end_window while immediately ending the game in
        the root_window, which the user can then dismiss.
        '''
        self._end_window.destroy()
        self._root_window.destroy()





    def _no_button_pressed(self) -> None:
        '''
        Closes the end_window, but does not end the game in root_window
        '''
        self._end_window.destroy()



##### _click_to_end_button_pressed exists in the _winner_window after a winner has been
##### determined or there are no longer any valid moves left in the game


    def _click_to_end_button_pressed(self) -> None:
        '''
        Closes the winner_window and ends the game in root_window,
        which the user can then dismiss.
        '''
        self._winner_window.destroy()
        self._root_window.destroy()
        














##### Methods below are involved with the creation of the Othello Board and Rules #####


    def _game_set_up(self) -> None:
        '''
        Handles the preparation of the Othello game after the user has selected
        all the pieces for both colors on the board. This is done in case there are
        no valid moves for either player with the initial board and the winner_window
        must be called.
        '''
        self._set_board = True

        #displays the score according to the initial pieces placed
        self._display_score()

        #displays the first turn
        self._display_turn()

        if self._game_state.winner() == '':
            
            #will display the other player if the first turn player did not have a valid move
            self._display_turn()

            self._move_label = tkinter.Label(
                master = self._selection_frame,
                text = 'CLICK ON THE BOARD TO MAKE A MOVE',
                font = ('Georgia', 11), relief = 'sunken')

            self._move_label.grid(
                row = 1, column = 0, padx = 2, pady = 2,
                sticky = tkinter.N)

            

        else:

            ## immediately find a winner (if there is one) if no valid moves can
            ## be made from the initial board
            self._no_more_moves_label()
            
            self._find_winner()






    def _run_turn(self, row: int, col: int) -> None:
        '''
        Runs a turn of the Othello game to determine whether the location
        the user desires to place a piece is valid. A valid move will place
        the piece and an invalid move will let the user have an option to
        place another piece.
        '''

        valid_move = self._game_state.make_move(row, col)

        # invalid move made, current user still has valid moves available, will
        # be given another chance to place another move
        if not valid_move:

            self._move_label.grid_remove()

            self._move_label = tkinter.Label(
                master = self._selection_frame,
                text = 'INVALID MOVE: CLICK ON BOARD AGAIN',
                font = ('Georgia', 11), relief = 'sunken')

            self._move_label.grid(
                row = 1, column = 0, padx = 2, pady = 2,
                sticky = tkinter.N)


        #user made a valid move 
        else:
            
            self._move_label.grid_remove()

            self._move_label = tkinter.Label(
                master = self._selection_frame,
                text = 'VALID MOVE: CLICK ON BOARD TO MAKE A MOVE',
                font = ('Georgia', 11), relief = 'sunken')

            self._move_label.grid(
                row = 1, column = 0, padx = 2, pady = 2,
                sticky = tkinter.N)

            self._draw_board()

            self._display_score()

            #checks if there is a winner after the move was made
            if self._game_state.winner() == '':
                
                # used in the case a player is skipped over due to having no
                # valid moves available
                self._display_turn()
        


            else:

                self._move_label.grid_remove()

                self._no_more_moves_label()

                self._find_winner()





    def _no_more_moves_label(self) -> None:
        '''
        Displays the NO MOVES LEFT label once there are no more valid
        moves left for either player and the game ends.
        '''
        
        self._move_label = tkinter.Label(
            master = self._selection_frame,
            text = 'NO MOVES LEFT FOR EITHER PLAYER',
            font = ('Georgia', 11), relief = 'sunken')

        self._move_label.grid(
            row = 1, column = 0, padx = 2, pady = 2,
            sticky = tkinter.N)
                




    def _find_winner(self) -> None:
        '''
        Creates the winner window that will pop-up once the game has
        ended.
        '''

        self._winner_window = tkinter.Toplevel()




        winner_frame = tkinter.Frame(
            master = self._winner_window,
            background = 'gold')

        winner_frame.grid(
            row = 0, column = 0, padx = 10, pady = 10,
            sticky = tkinter.N)
            
        winner_header = tkinter.Label(
            master = winner_frame, text = 'WINNER: ' + self._game_state.winner(),
            font = DEFAULT_FONT, relief = 'raised')

        winner_header.grid(
            row = 0, column = 0, padx = 10, pady = 10,
            sticky = tkinter.N)




        click_to_end_button = tkinter.Button(
            master = self._winner_window, text = 'CLICK HERE TO END GAME',
            font = DEFAULT_FONT, command = self._click_to_end_button_pressed)

        click_to_end_button.grid(
            row = 1, column = 0, padx = 10, pady = 10,
            sticky = tkinter.S)





        self._winner_window.rowconfigure(0, weight = 1)
        self._winner_window.columnconfigure(0, weight = 1)
        self._winner_window.grab_set()
        self._winner_window.wait_window()

        
        


    def _new_game_options(self) -> None:
        '''
        Handles the creation of a new Othello Game by popping up the
        game_rules dialog window. If the user decides to begin a game using
        the choices selected, attributes will be set for the rows, cols, turn,
        and win type according to what was selected and a new game_state will be
        created.
        '''
        
        self._game_rules = othello_game_rules.OthelloGameRules()

        self._begin_game = self._game_rules.begin_game()

        if self._begin_game != '':

            #sets attributes according to user selection from dialog window
            self._rows = self._game_rules.num_rows()
            self._cols = self._game_rules.num_cols()
            self._first_turn = self._game_rules.first_turn()
            self._win_type = self._game_rules.win_condition()

            #creates a game_state attribute            
            self._game_state = othello_game_logic.OthelloGameState(self._rows, self._cols, self._first_turn[0], self._win_type)
            self._colors_dict = {othello_game_logic.GREEN: 'green', othello_game_logic.WHITE: 'white'} 

            self._draw_board()

            #sets up selection button so the initial pieces can be placed
            self._set_up_selection_button()

            

      
 
    def _display_turn(self) -> None:
        '''
        Displays the current player's turn in the upper left corner
        '''
        
        turn_frame = tkinter.Frame(
            master = self._root_window,
            background = '#F442AD')

        turn_frame.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N + tkinter.W)
        

        turn_header = tkinter.Label(
            master = turn_frame,
            text = 'TURN: ' + self._colors_dict[self._game_state._turn].upper(),
            font = DEFAULT_FONT, relief = 'sunken',
            background = '#41CAF4')


        turn_header.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N + tkinter.W)





    def _display_score(self) -> None:
        '''
        Displays the current updated score of the game between the
        green and white pieces
        '''
        
        #updates the score attribute of game_state
        self._game_state.update_score()

        
        score_frame = tkinter.Frame(
            master = self._root_window,
            background = '#A0F441')

        score_frame.grid(
            row = 0, column = 0, rowspan = 2, padx = 3, pady = 3,
            sticky = tkinter.N + tkinter.E)
        

        score_header_green = tkinter.Label(
            master = score_frame, text = 'GREEN SCORE: ' + str(self._game_state._score['G']),
            font = DEFAULT_FONT, relief = 'sunken',
            background = '#00F2CD')


        score_header_green.grid(
            row = 0, column = 0, padx = 3, pady = 3,
            sticky = tkinter.N + tkinter.E)


        score_header_white = tkinter.Label(
            master = score_frame, text = 'WHITE SCORE: ' + str(self._game_state._score['W']),
            font = DEFAULT_FONT, relief = 'sunken',
            background = '#FFFFFF')


        score_header_white.grid(
            row = 1, column = 0, padx = 3, pady = 3,
            sticky = tkinter.N + tkinter.E)

        


       
        
    def _draw_board(self) -> None:
        '''
        Draws the board onto the canvas by drawing each cell according to
        how many rows/cols the user initially selected
        '''
        
        self._canvas.delete(tkinter.ALL)

        canvas_width = self._canvas.winfo_width()
        canvas_height = self._canvas.winfo_height()

        row = 0
        col = 0

        while row < self._rows:
            
            col = 0

            while col < self._cols:

                self._draw_board_cell(canvas_width, canvas_height, row, col)

                col += 1

            row += 1
                    




    def _draw_board_cell(self, canvas_width: int, canvas_height: int, row: int, col: int) -> None:
        '''
        Draws a board cell by calculating the upper left coordinate and lower
        right coordinate of each cell so a rectangle indicating the board cell
        can be created.
        '''
        
        width = canvas_width/self._cols
        height = canvas_height/self._rows

        x_upper_left = col * width
        y_upper_left = row * height

        x_lower_right = (col+1) * width
        y_lower_right = (row+1) * height

        self._canvas.create_rectangle(x_upper_left, y_upper_left, x_lower_right, y_lower_right, outline = 'black')

        if self._game_state._board[row][col] != othello_game_logic.NONE:

            self._draw_board_piece(row, col, x_upper_left, y_upper_left, x_lower_right, y_lower_right)






    def _draw_board_piece(self, row: int, col: int, x_upper_left: int, y_upper_left: int, x_lower_right: int, y_lower_right: int) -> None:
        '''
        Draws a board cell pieces by creating an oval using the upper left
        coordinate and lower right coordinate found earlier when creating
        a board cell.
        '''
        
        self._canvas.create_oval(x_upper_left, y_upper_left, x_lower_right, y_lower_right,
                                 fill = self._colors_dict[self._game_state._board[row][col]])
    

        


        
    def _on_canvas_resized(self, event: tkinter.Event) -> None:
        '''
        Handles the resizing of the canvas
        '''
        if self._begin_game != '':
            self._draw_board()




            
    def run(self) -> None:
        '''
        Runs the main loop of the OthelloApplication()
        '''
        self._root_window.mainloop()       
        
        




if __name__ == '__main__':

    app = OthelloApplication()
    app.run()


