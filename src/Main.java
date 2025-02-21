import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        char[] boardLayout =
                {'1', '2', '3',
                 '4', '5', '6',
                 '7', '8', '9'}; //as a visual guide at the start of the game

        char[] board =
                {' ', ' ', ' ',
                 ' ', ' ', ' ',
                 ' ', ' ', ' '}; //actual board

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
            int choice;

            //handling illegal player moves
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
                choice = AI(computer,human,board);
                System.out.println("Computer chose: "+choice);
            }

            board[choice-1] = playerTile;
            turnsPlayed++;

            if (winCondition(board) == playerTile){
                printBoard(board);
                System.out.println("Congratulations Player '"+playerTile+"', you win!");
                break;
            }
            else if(winCondition(board) == 't') {
                printBoard(board);
                System.out.println("It's a tie!");
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

    static boolean matchThree(char a, char b, char c) {
        return a == b && b == c && a != ' ';
    }

    static char winCondition(char[] board) {
        if(
            matchThree(board[0], board[1], board[2]) ||
            matchThree(board[0], board[3], board[6]) ||
            matchThree(board[0], board[4], board[8])
        ){
            return board[0];
        }

        if(
            matchThree(board[3], board[4], board[5]) ||
            matchThree(board[1], board[4], board[7]) ||
            matchThree(board[2], board[4], board[6])
        ){
            return board[4];
        }

        if(
            matchThree(board[6], board[7], board[8]) ||
            matchThree(board[2], board[5], board[8])
        ){
            return board[8];
        }

        boolean hasOpenSlot = false;
        for (char c : board) {
            if (c == ' ') {
                hasOpenSlot = true;
                break;
            }
        }
        if(!hasOpenSlot){
            return 't'; //t for tie
        }

        return 'a'; //a for active
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

    static int counter = 0;
    //for counting total number of searches performed with each move the computer makes
    //and also to help in showcasing the optimisation of alpha-beta pruning

    static int AI(char computer, char human, char[] board) {

        int bestScore = Integer.MIN_VALUE;
        int move = 4;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = computer;
                int score = minMax(board, computer, human, 0, false);
                board[i] = ' '; //resetting the actual value to not override our board
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }

        System.out.println("***MinMax performed "+counter+" searches!***");
        counter = 0;
        return move+1;
    }

    static int minMax(char[] board, char computer, char human, int depth, boolean isMaximising) {
        counter++;
        char result = winCondition(board);
        if(result != 'a'){
            if(result == computer){
                return 1;
            }else if(result == human){
                return -1;
            }else if(result == 't'){
                return 0;
            }
        }

        if(isMaximising){ //maximiser (i.e. Computer)
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++) {
                if(board[i] == ' ') {
                    board[i] = computer;
                    int score = minMax(board,computer,human,depth+1,false);
                    board[i] = ' ';
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        }
        else{ //minimiser (i.e. Human)
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++) {
                if(board[i] == ' ') {
                    board[i] = human;
                    int score = minMax(board,computer,human,depth+1,true);
                    board[i] = ' ';
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }
}