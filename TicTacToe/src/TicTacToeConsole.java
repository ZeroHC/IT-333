public class TicTacToeConsole
{
    private static TicTacToe ticTacToe;
    private static int player = TicTacToe.PLAYER_2;

    public static void main(String[] args)
    {
        //create a new game
        ticTacToe = new TicTacToe();

        //welcome the user
        System.out.println("Welcome to Tic Tac Toe");

        //primary game loop
        while (ticTacToe.getWinner() == -1 && !ticTacToe.isBoardFull())
        {
            printTicTacToe();
            System.out.println();

            //change the player
            player = (player == TicTacToe.PLAYER_1) ? TicTacToe.PLAYER_2 : TicTacToe.PLAYER_1;
            getPlayerChoice(player);
        }

        //print the board, then announce the results
        printTicTacToe();
        System.out.println();

        int winner = ticTacToe.getWinner();
        if (winner != -1)
        {
            System.out.println("Player #" + winner + " wins the game!");
        }
        else
        {
            System.out.println("The game is a draw.");
        }
    }

    private static void printTicTacToe()
    {
        int[][] board = ticTacToe.getBoard();
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                String display = "_ ";

                if (board[i][j] != 0)
                {
                    display = board[i][j] + " ";
                }
                System.out.print(display);
            }
            System.out.println();
        }
    }

    private static void getPlayerChoice(int player)
    {
        int row = Console.getInt("Player #" + player + ": enter a row");
        int col = Console.getInt("Player #" + player + ": enter a col");

        boolean result = ticTacToe.makeMove(player, row, col);

        if (result)
        {
            System.out.println("Move successful");
        }
        else
        {
            System.out.println("Move unsuccessful");
        }
        System.out.println();
    }
}
