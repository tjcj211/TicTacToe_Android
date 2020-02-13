/*
 * Timothy Carta
 * 2/14/2020
 * Used to create and update board for TicTacToe game.
 */

package edu.quinnipiac.ser210.tictactoe;

public class TicTacToe implements ITicTacToe {

    // The game board and the game status
    private static final int ROWS = 3, COLS = 3; // number of rows and columns
    private int[][] board = new int[ROWS][COLS]; // game board in 2D array
    private int currentPlayer;

    /**
     * clear board and set current player
     */
    public TicTacToe(){
        clearBoard();
        currentPlayer = 1;
    }

    //Makes all values on board equal to EMPTY
    @Override
    public void clearBoard() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c] = EMPTY;
            }
        }
    }

    @Override
    public void setMove(int player, int location) {
        System.out.println("Player: " + player + " Location: " + location);
        int row = location / 3; //Finds which row based upon location given
        int col = location % 3; //Finds which column based upon location given
        if (player == 0) {
            if (isValidMove(location)) {
                board[row][col] = NOUGHT; //Place an O
                currentPlayer = 1;
            }
        }
        else if (player == 1){
            if (isValidMove(location)) {
                board[row][col] = CROSS; //Place an X
                currentPlayer = 0;
            }
        }
    }

    //Check to see if the board is full by looking at all rows and columns
    public boolean isBoardFull() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == EMPTY) {
                    return false; //Empty space found
                }
            }
        }
        return true; //Board is full
    }

    //Returns the constant for the symbol at the given location
    public int getSymbolAtLocation(int location) {
        int row = location / 3;
        int col = location % 3;

        return board[row][col];
    }

    public int getPlayerAtLocation(int location) {
        int row = location / 3;
        int col = location % 3;

        switch (board[row][col]) {
            case 1: //X - Player 1
                return 1;
            case 2: //O - Player 0
                return 0;
        }
        return -1; //No player at location
    }


    @Override
    public int getComputerMove() { //Return the best move for the computer to make

        /**
         * This will check the available spaces to see if the computer can
         * block a winning move for the human player
         */
        if ((board[1][1] == CROSS && board[2][2] == CROSS ||
                board[0][1] == CROSS && board[0][2] == CROSS ||
                board[1][0] == CROSS && board[2][0] == CROSS) &&
                board[0][0] == EMPTY) {
            return 0; //Place the O at location 0
        } else if ((board[0][0] == CROSS && board[0][2] == CROSS ||
                board[1][1] == CROSS && board[2][1] == CROSS) &&
                board[0][1] == EMPTY) {
            return 1; //Place the O at location 1
        } else if ((board[0][0] == CROSS && board[0][1] == CROSS ||
                board[2][0] == CROSS && board[1][1] == CROSS ||
                board[1][2] == CROSS && board[2][2] == CROSS) &&
                board[0][2] == EMPTY) {
            return 2; //Place the O at location 2
        } else if ((board[0][0] == CROSS && board[2][0] == CROSS ||
                board[1][1] == CROSS && board[1][2] == CROSS) &&
                board[1][0] == EMPTY) {
            return 3; //Place the O at location 3
        } else if ((board[0][0] == CROSS && board[2][2] == CROSS ||
                board[0][1] == CROSS && board[2][1] == CROSS ||
                board[0][2] == CROSS && board[2][0] == CROSS ||
                board[1][0] == CROSS && board[1][2] == CROSS) &&
                board[1][1] == EMPTY) {
            return 4; //Place the O at location 4
        } else if ((board[0][2] == CROSS && board[2][2] == CROSS ||
                board[1][0] == CROSS && board[1][1] == CROSS) &&
                board[1][2] == EMPTY) {
            return 5; //Place the O at location 5
        } else if ((board[0][0] == CROSS && board[1][0] == CROSS ||
                board[1][1] == CROSS && board[0][2] == CROSS ||
                board[2][1] == CROSS && board[2][2] == CROSS) &&
                board[2][0] == EMPTY) {
            return 6; //Place the O at location 6
        } else if ((board[0][1] == CROSS && board[1][1] == CROSS ||
                board[2][0] == CROSS && board[2][2] == CROSS) &&
                board[2][1] == EMPTY) {
            return 7; //Place the O at location 7
        } else if ((board[0][0] == CROSS && board[1][1] == CROSS ||
                board[2][0] == CROSS && board[2][1] == CROSS ||
                board[0][2] == CROSS && board[1][2] == CROSS) &&
                board[2][2] == EMPTY) {
            return 8; //Place the O at location 8
        } else {
            int number = (int)(Math.random() * 9); //Pick a random spot on the board
            int r = number /3; //Calculate the row
            int c = number %3; //Calculate the column

            while (board[r][c] != EMPTY && !isBoardFull()) { //Make sure that the piece can be placed
                number = (int)(Math.random() * 8);
                r = number /3; //Calculate the row again
                c = number %3; //Calculate the column again
            }
            return number; //Place the O at a random location
        }
    }

    public int getCurrentPlayer() { //Return the current player
        return currentPlayer;
    }

    public void setCurrentPlayer(int player) { //Sets the current player
        currentPlayer = player;
    }

    /*
     * Returns if move at given location is valid
     */
    public boolean isValidMove(int location) {
        int row = location / 3; //Which row
        int col = location % 3; //Which column
        if (board[row][col] != NOUGHT && board[row][col] != CROSS) {
            return true;
        }
        return false;
    }

    @Override
    public int checkForWinner() {
        if (board[0][0] == NOUGHT && board[1][1] == NOUGHT && board[2][2] == NOUGHT || //DiagonalRight
                board[0][2] == NOUGHT && board[1][1] == NOUGHT && board[2][0] == NOUGHT || //DiagonalLeft
                board[0][0] == NOUGHT && board[0][1] == NOUGHT && board[0][2] == NOUGHT || //TopAcross
                board[1][0] == NOUGHT && board[1][1] == NOUGHT && board[1][2] == NOUGHT || //MidAcross
                board[2][0] == NOUGHT && board[2][1] == NOUGHT && board[2][2] == NOUGHT || //BotAcross
                board[0][0] == NOUGHT && board[1][0] == NOUGHT && board[2][0] == NOUGHT || //LeftDown
                board[0][1] == NOUGHT && board[1][1] == NOUGHT && board[2][1] == NOUGHT || //MidDown
                board[0][2] == NOUGHT && board[1][2] == NOUGHT && board[2][2] == NOUGHT) { //RightDown
            return NOUGHT_WON;
        }
        else if (board[0][0] == CROSS && board[1][1] == CROSS && board[2][2] == CROSS || //DiagonalRight
                board[0][2] == CROSS && board[1][1] == CROSS && board[2][0] == CROSS || //DiagonalLeft
                board[0][0] == CROSS && board[0][1] == CROSS && board[0][2] == CROSS || //TopAcross
                board[1][0] == CROSS && board[1][1] == CROSS && board[1][2] == CROSS || //MidAcross
                board[2][0] == CROSS && board[2][1] == CROSS && board[2][2] == CROSS || //BotAcross
                board[0][0] == CROSS && board[1][0] == CROSS && board[2][0] == CROSS || //LeftDown
                board[0][1] == CROSS && board[1][1] == CROSS && board[2][1] == CROSS || //MidDown
                board[0][2] == CROSS && board[1][2] == CROSS && board[2][2] == CROSS) { //RightDown
            return CROSS_WON;
        }
        else if (isBoardFull()) {
            return TIE; //Game is a tie
        }
        else{
            return PLAYING; //Nobody has won and the game continues
        }

    }
}
