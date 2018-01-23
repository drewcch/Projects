# Andrew Chien

import tkinter


DEFAULT_FONT = ('Georgia', 14)


class OthelloGameRules:

    def __init__(self):

        self._dialog_window = tkinter.Toplevel()

        #initializes three lists to be used to set variables in OptionMenu
        self._row_col_list = [4, 6, 8, 10, 12, 14, 16]
        
        self._first_turn_list = ['GREEN PLAYER', 'WHITE PLAYER']
 
        self._win_type_list = ['MOST PIECES WIN', 'FEWEST PIECES WIN']

        #determines whether the game has begun; will equal 'Start' if it has
        self._begin_game = ''




        title_frame = tkinter.Frame(
            master = self._dialog_window,
            background = 'cyan')

        title_frame.grid(
            row = 0, column = 2, padx = 5, pady = 5,
            sticky = tkinter.N + tkinter.W)

        title = tkinter.Label(
            master = title_frame, text = 'Game Rules',
            font = DEFAULT_FONT, relief = 'raised')

        title.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N + tkinter.W)





        # handles the creation of the rows label and row OptionMenu
        # default value is set at 4 rows
        row_frame = tkinter.Frame(
            master = self._dialog_window,
            background = 'magenta')

        row_frame.grid(
            row = 1, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)
        
        row_num = tkinter.Label(
            master = row_frame, text = 'Number of Rows',
            font = DEFAULT_FONT, relief = 'raised')

        row_num.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)

        self._row_choices = tkinter.IntVar()
        self._row_choices.set(self._row_col_list[0])

        row_selection = tkinter.OptionMenu(
            self._dialog_window, self._row_choices, *self._row_col_list)

        row_selection.grid(
            row = 2, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)





        # handles the creation of the cols label and col OptionMenu
        # default value is set at 4 cols
        col_frame = tkinter.Frame(
            master = self._dialog_window,
            background = 'orange')

        col_frame.grid(
            row = 1, column = 1, padx = 5, pady = 5,
            sticky = tkinter.N)
        
        col_num = tkinter.Label(
            master = col_frame, text = 'Number of Columns',
            font = DEFAULT_FONT, relief = 'raised')

        col_num.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)

        self._col_choices = tkinter.IntVar()
        self._col_choices.set(self._row_col_list[0])

        col_selection = tkinter.OptionMenu(
            self._dialog_window, self._col_choices, *self._row_col_list)

        col_selection.grid(
            row = 2, column = 1, padx = 5, pady = 5,
            sticky = tkinter.N)
            




        # handles the creation of the first_turn label and first_turn OptionMenu
        # default value is set for the Green Player to go first
        first_turn_frame = tkinter.Frame(
            master = self._dialog_window,
            background = 'lime')

        first_turn_frame.grid(
            row = 1, column = 2, padx = 5, pady = 5,
            sticky = tkinter.N)
        
        first_turn = tkinter.Label(
            master = first_turn_frame, text = 'First Turn',
            font = DEFAULT_FONT, relief = 'raised')

        first_turn.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)

        self._first_turn_choices = tkinter.StringVar()
        self._first_turn_choices.set(self._first_turn_list[0])

        first_turn_selection = tkinter.OptionMenu(
            self._dialog_window, self._first_turn_choices, *self._first_turn_list)

        first_turn_selection.grid(
            row = 2, column = 2, padx = 5, pady = 5,
            sticky = tkinter.N)
            



        # handles the creation of the win_type label and win_type OptionMenu
        # default value is set for the Most pieces to win
        win_type_frame = tkinter.Frame(
            master = self._dialog_window,
            background = 'yellow')

        win_type_frame.grid(
            row = 1, column = 3, padx = 5, pady = 5,
            sticky = tkinter.N)

        win_type = tkinter.Label(
            master = win_type_frame, text = 'Win Type',
            font = DEFAULT_FONT, relief = 'raised')

        win_type.grid(
            row = 0, column = 0, padx = 5, pady = 5,
            sticky = tkinter.N)

        self._win_type_choices = tkinter.StringVar()
        self._win_type_choices.set(self._win_type_list[0])

        win_type_selection = tkinter.OptionMenu(
            self._dialog_window, self._win_type_choices, *self._win_type_list)

        win_type_selection.grid(
            row = 2, column = 3, padx = 5, pady = 5,
            sticky = tkinter.N)
        



        #creates the Begin/Cancel game buttons to begin or cancel a game accordingly
        self._button_frame = tkinter.Frame(
            master = self._dialog_window)

        self._button_frame.grid(
            row = 3, column = 0, columnspan = 2, padx = 5, pady = 5,
            sticky = tkinter.W + tkinter.S)
            
        begin_game_button = tkinter.Button(
            master = self._button_frame, text = 'Begin Game',
            font = DEFAULT_FONT, relief = 'ridge',
            command = self._begin_game_button_pressed)
        
        begin_game_button.grid(
            row = 0, column = 0, padx = 5, pady = 5)
        
        cancel_game_button = tkinter.Button(
            master = self._button_frame, text = 'Cancel Game',
            font = DEFAULT_FONT, relief = 'ridge',
            command = self._cancel_game_button_pressed)

        cancel_game_button.grid(
            row = 0, column = 1, padx = 5, pady = 5)
        



        #configures all the rows/cols for resizing
        self._dialog_window.rowconfigure(0, weight = 1)
        self._dialog_window.rowconfigure(1, weight = 1)
        self._dialog_window.rowconfigure(2, weight = 1)
        self._dialog_window.columnconfigure(0, weight = 1)
        self._dialog_window.columnconfigure(1, weight = 1)
        self._dialog_window.columnconfigure(2, weight = 1)
        self._dialog_window.columnconfigure(3, weight = 1)



        #turns user control over to the dialog window
        self._dialog_window.grab_set()
        self._dialog_window.wait_window()
        

    def num_rows(self) -> int:
        '''
        Returns the number of rows set by the user
        '''
        return self._rows

    

    def num_cols(self) -> int:
        '''
        Returns the number of cols set by the user
        '''
        return self._cols

    

    def first_turn(self) -> str:
        '''
        Returns the player with the first turn as set by the user
        '''

        return self._first_turn

    

    def win_condition(self) -> str:
        '''
        Returns the win condition set by the user
        '''

        return self._win_type

    


    def begin_game(self) -> str:
        '''
        Returns the value of begin_game to indicate if the game has started
        '''
        return self._begin_game

        
        

    def _begin_game_button_pressed(self) -> None:
        '''
        Sets the choices made by user into variables that will be passed
        onto the main game
        '''
        self._rows = self._row_choices.get()
        self._cols = self._col_choices.get()
        self._win_type = self._win_type_choices.get()
        self._first_turn = self._first_turn_choices.get()
        self._begin_game = 'Start'
        self._dialog_window.destroy()




    def _cancel_game_button_pressed(self) -> None:
        '''Closes the dialog window'''
        self._dialog_window.destroy()


        
