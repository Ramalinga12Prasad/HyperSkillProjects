package tictactoe;
import java.util.Scanner;

public class TicTacToe {
  public static void printBoard(char[][] board) {
        System.out.println("---------");
        System.out.printf("| %s %s %s |%n", board[0][0], board[0][1], board[0][2]);
        System.out.printf("| %s %s %s |%n", board[1][0], board[1][1], board[1][2]);
        System.out.printf("| %s %s %s |%n", board[2][0], board[2][1], board[2][2]);
        System.out.println("---------");
        
    }
    public static void initializeBoard(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public static char getWinning(char[][] board) {
        //checking row wise 
        for (int i = 0; i < 3; i++) {
            if(board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        } 
        //checking column wise
        for (int i = 0; i < 3; i++) {
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return board[0][i];
            }
        } 

        //checking main diagonal
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        //checking other diagonal
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        return '0';
    }

   

    
    public static void main(String[] args) {
        // write your code here
        Scanner in = new Scanner(System.in);
        char[][] board = new char[3][3];
        initializeBoard(board);
        printBoard(board);
        boolean isPlayerX = true;
        int count = 0;
        boolean isGameOver = false;
        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                int a = in.nextInt();
                int b = in.nextInt();
                if (a < 1 || a > 3 || b < 1 || b > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                } else {
                    a--;
                    b = b == 1 ? 2 :  b == 3 ? 0 : 1; 
                    if (board[b][a] != ' ') {
                        System.out.println("This cell is occupied! Choose another one!");
                    } else {
                        if (isPlayerX) {
                            board[b][a] = 'X';
                            isPlayerX  = !isPlayerX;
                            count++;
                        } else {
                            board[b][a] = 'O';
                            isPlayerX  = !isPlayerX;
                            count++;
                        }
                         printBoard(board);
                    }
                }
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                in.nextLine();
            }
            char winningChar = getWinning(board);
            if (winningChar == 'X') {
                System.out.println("X wins");
                isGameOver = true;
            } else if (winningChar == 'O') {
                System.out.println("O wins");
                isGameOver = true;
            } else if(count == 9) {
                System.out.println("Draw");
                isGameOver = true;
            }
         if (isGameOver) {
             break;   
        }
        }
    }
}
