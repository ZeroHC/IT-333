import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TicTacToeTest
{
    private TicTacToe ticTacToe;

    @Before
    public void setup()
    {
        ticTacToe = new TicTacToe();
    }

    @Test
    public void testGetBoard()
    {
        int[][] board = ticTacToe.getBoard();

        //verify board not null
        Assert.assertNotNull("the board retrieved is null", board);

        //verify board dimensions
        Assert.assertEquals("board grid is not 3x3",
                TicTacToe.GRID_SIZE, board.length);

        for (int i = 0; i < board.length; i++)
        {
            Assert.assertEquals("board grid is not 3x3",
                    TicTacToe.GRID_SIZE, board[i].length);
        }

        //verify the board is a copy
        board[0][0] = TicTacToe.PLAYER_1; //make a change
        int[][] innerBoard = ticTacToe.getBoard(); //retrieve the board again

        //make sure these are not equal
        Assert.assertNotEquals("The getBoard() method is returning" +
                " a reference to the actual board", board[0][0], innerBoard[0][0]);
    }

    @Test
    public void testPlayerMoveBadInputs()
    {
        //check for a bad player number
        try
        {
            ticTacToe.makeMove(-1, 0,0);

            //we should not reach this position
            Assert.fail("A bad player does not result in an IllegalArgumentException");
        }
        catch (IllegalArgumentException ex)
        {
            //do nothing...
        }

        //check for a bad row/col
        try
        {
            ticTacToe.makeMove(TicTacToe.PLAYER_1, -1,0);

            //we should not reach this position
            Assert.fail("A bad row does not result in an IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException ex)
        {
            //do nothing...
        }

        try
        {
            ticTacToe.makeMove(TicTacToe.PLAYER_1, 0,-1);

            //we should not reach this position
            Assert.fail("A bad col does not result in an IndexOutOfBoundsException");
        }
        catch (IndexOutOfBoundsException ex)
        {
            //do nothing...
        }
    }

    @Test
    public void testPlayerMove()
    {
        //make a few moves
        ticTacToe.makeMove(TicTacToe.PLAYER_1, 0, 0);
        ticTacToe.makeMove(TicTacToe.PLAYER_2, 1, 1);

        //test whether the moves were registered
        int[][] board = ticTacToe.getBoard();

        Assert.assertEquals("Player 1 choice is not seen at 0, 0",
                TicTacToe.PLAYER_1, board[0][0]);
        Assert.assertEquals("Player 2 choice is not seen at 1, 1",
                TicTacToe.PLAYER_2, board[1][1]);
    }

    @Test
    public void testPlayerMoveOverlap()
    {
        //make an initial move
        ticTacToe.makeMove(TicTacToe.PLAYER_1, 0, 0);
        boolean result = ticTacToe.makeMove(TicTacToe.PLAYER_2, 0, 0);

        //make sure nothing changed and this was reported as unsuccessful
        Assert.assertFalse("Success was reported in an overlapping play",
                result);

        Assert.assertEquals("A player move was replaced in an overlapping play",
                TicTacToe.PLAYER_1, ticTacToe.getBoard()[0][0]);

        Assert.assertTrue("A valid move was reported as unsuccessful",
                ticTacToe.makeMove(TicTacToe.PLAYER_1, 1, 1));
    }

    @Test
    public void testPlayerTurns()
    {

    }

    @Test
    public void testGetWinner()
    {
        ticTacToe.makeMove(TicTacToe.PLAYER_1, 0, 0);
        ticTacToe.makeMove(TicTacToe.PLAYER_2, 1, 0);
        ticTacToe.makeMove(TicTacToe.PLAYER_1, 0, 1);
        ticTacToe.makeMove(TicTacToe.PLAYER_2, 1, 1);
        ticTacToe.makeMove(TicTacToe.PLAYER_1, 0, 2);
        ticTacToe.makeMove(TicTacToe.PLAYER_2, 1, 2);

        int winner = ticTacToe.getWinner();

        Assert.assertEquals("Winner is not player 1", TicTacToe.PLAYER_1, winner);
        Assert.assertNotEquals("Wrong winner", TicTacToe.PLAYER_2, winner);
    }
}