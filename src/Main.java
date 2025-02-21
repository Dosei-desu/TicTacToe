import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[] boardLayout =
                {'1', '2', '3',
                 '4', '5', '6',
                 '7', '8', '9'};
        char[] board =
                {' ', ' ', ' ',
                 ' ', ' ', ' ',
                 ' ', ' ', ' '};
        int turnsPlayed = 0;
        char playerTile = 'x';

        char human = 'x';
        char computer = 'o';

        int whoGoesFirst = 0;
        while (whoGoesFirst < 1 || whoGoesFirst > 2) {
            System.out.println("Who goes first? 1 = Human, 2 = Computer");
            whoGoesFirst = scanner.nextInt();
            if (whoGoesFirst == 2) {
                human = 'o';
                computer = 'x';
            }
        }

        printBoard(boardLayout);
        while(turnsPlayed < 9) {

            System.out.println("Select a tile number Player '"+playerTile+"':");
            int choice = 0;

            //handling illegal moves
            if(human == playerTile) {
                choice = scanner.nextInt();

                while (choice < 1 || choice > 9 ||
                        board[choice - 1] == 'x' || board[choice - 1] == 'o') {
                    System.out.println(
                            "Invalid input!" +
                            "The number has to be between 1 and 9, " +
                            "and you cannot pick a previously selected tile!");
                    choice = scanner.nextInt();
                }
            }else{
                choice = AI(turnsPlayed,computer,human,board);
                System.out.println("Computer chose: "+choice);
            }

            board[choice-1] = playerTile;
            if(winCondition(board, playerTile)){
                System.out.println("Congratulations Player '"+playerTile+"', you win!");
                printBoard(board);
                break;
            }

            turnsPlayed++;
            if(turnsPlayed == 9) {
                System.out.println("It's a tie!");
                printBoard(board);
                break;
            }

            if(playerTile == 'x') {
                playerTile = 'o';
            }else{
                playerTile = 'x';
            }

            printBoard(board);
        }

    }

    static boolean winCondition(char[] b, char player) {
        return b[0] == player && b[1] == player && b[2] == player ||
               b[3] == player && b[4] == player && b[5] == player ||
               b[6] == player && b[7] == player && b[8] == player ||
               b[0] == player && b[3] == player && b[6] == player ||
               b[1] == player && b[4] == player && b[7] == player ||
               b[2] == player && b[5] == player && b[8] == player ||
               b[0] == player && b[4] == player && b[8] == player ||
               b[2] == player && b[4] == player && b[6] == player;
    }

    static void printBoard(char[] b) {
        System.out.print(
                       b[0] + " | " + b[1] + " | " + b[2] +
                "\n- + - + -" +
                "\n" + b[3] + " | " + b[4] + " | " + b[5] +
                "\n- + - + -" +
                "\n" + b[6] + " | " + b[7] + " | " + b[8] +"\n"
        );
    }

    static int AI(int turnsPlayed, char computer, char human, char[] board) {

        double bestScore = Double.NEGATIVE_INFINITY;
        int bestMove = 5;

        for (int i = 0; i < 9; i++) {
            char dontOverride = board[i];
            if(board[i] == ' ') {
                board[i] = computer;
                double score = minMax(board);
                board[i] = dontOverride;
                if(score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }

        return bestMove+1;
    }

    static double minMax(char[] board) {
        return 1;
    }
}
