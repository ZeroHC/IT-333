/**
 * Represents a TicTacToe match between two players.
 *
 * @author Josh Archer
 * @version 1.0
 */
public class TicTacToe
{
    public static final int GRID_SIZE = 3;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;

    private int[][] board;
    private int previousPlayer = -1;

    /**
     * Creates a new TicTacToe object.
     */
    public TicTacToe()
    {
        board = new int[GRID_SIZE][GRID_SIZE];
    }

    /**
     * Allows a player to place a token on the board. The toke is only placed
     * if there is not a token already located at the row/col. The method also
     * makes sure that a player does not act twice in a row.
     *
     * @param player a number, 1 or 2, that represents the players
     *               in the match. Any other input will result in
     *               an IllegalArgumentException
     * @param row a row to add a token to. Throws an IndexOutOfBoundsException
     *            if the index is invalid.
     * @param col a col to add a token to. Throws an IndexOutOfBoundsException
     *            if the index is invalid.
     * @return returns true if the token was placed, otherwise returns false
     */
    public boolean makeMove(int player, int row, int col)
    {
        //make sure we have a valid player and row/col
        if (player != PLAYER_1 && player != PLAYER_2)
        {
            throw new IllegalArgumentException(
                    "Invalid player number given");
        }
        else if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE)
        {
            throw new IndexOutOfBoundsException(
                    "Bad index, r:" + row + ", c: " + col);
        }

        //make sure player turns alternate
        if (previousPlayer == -1)
        {
            previousPlayer = player;
        }
        else if (previousPlayer == player)
        {
            return false;
        }
        else
        {
            previousPlayer = player;
        }

        //make the move if there is not already a piece in that location
        if (board[row][col] == 0)
        {
            board[row][col] = player;
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Returns a copy of the internal board.
     * @return an integer 2d array, where a zero represents an empty space
     *         and a 1 or 2 represents a player move.
     */
    public int[][] getBoard()
    {
        int[][] result = new int[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < result.length; i++)
        {
            for (int j = 0; j < result[i].length; j++)
            {
                result[i][j] = board[i][j];
            }
        }
        return result;
    }

    /**
     * Returns 1 or 2 if a player has won the match. Otherwise the method
     * returns -1 if no winner can be determined.
     *
     * @return 1 or 2 if player 1 or two has won, otherwise the method returns
     * -1.
     */
    public int getWinner()
    {
        if (playerWins(PLAYER_1))
        {
            return PLAYER_1;
        }
        else if (playerWins(PLAYER_2))
        {
            return PLAYER_2;
        }
        return -1;
    }

    private boolean playerWins(int player)
    {
        //check rows/cols
        for (int i = 0; i < GRID_SIZE; i++)
        {
            if (ownsRow(player, i) || ownsCol(player, i))
            {
                return true;
            }
        }

        //check diagonals
        return ownsDiagonal(player);
    }

    private boolean ownsDiagonal(int player)
    {
        return (board[0][0] == player &&
                board[1][1] == player &&
                board[2][2] == player) ||
               (board[0][2] == player &&
                board[1][1] == player &&
                board[2][0] == player);
    }

    private boolean ownsRow(int player, int row)
    {
        return board[row][0] == player &&
               board[row][1] == player &&
               board[row][2] == player;
    }

    private boolean ownsCol(int player, int col)
    {
        return board[0][col] == player &&
               board[1][col] == player &&
               board[2][col] == player;
    }

    /**
     * Returns true if the board is entirely full of tokens.
     *
     * @return true if the board is full, otherwise returns false
     */
    public boolean isBoardFull()
    {
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
