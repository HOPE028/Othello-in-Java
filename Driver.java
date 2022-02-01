package Proven;
import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		//Game Setup
		Board BOARD = new Board();
		Players playerOne = new Players(1);
		Players playerTwo = new Players(-1);
		int[][] board = BOARD.boardSetup();
		boolean nextMove = true;
		BOARD.print(board);
		
		//Game starts
		
		//While the loop has not broken because of the game ending, the game will carry on.
		while (true) {
			//Player one moves if the 'nextMove' variable is equal to true
			if (nextMove) playerOne.move(BOARD, board);
			
			nextMove = true;
			
			//Checks if the second player has a possible move
			if (BOARD.gameOver(board, playerTwo.getPerson())) {
				//Checks if all the spots are gone
				if (BOARD.spotsGone(board)) {
					//Game over
					BOARD.winner(board, true);
					return;
				} else {
					//Next move will be skipped because there is no possible move.
					System.out.println("\n**Next Move Skipped Because No Move Is Possible**\n");
					nextMove = false;
				}
			}
			
			//Player two moves if the 'nextMove' variable is equal to true
			if (nextMove) playerTwo.move(BOARD, board);
			
			nextMove = true;
			
			//Checks if the first player has a possible move
			if (BOARD.gameOver(board, playerOne.getPerson())) {
				//Checks if all the spots are gone
				if (BOARD.spotsGone(board)) {
					//Game over
					BOARD.winner(board, true);
					return;
				} else {
					//Next move will be skipped because there is no possible move.
					System.out.println("\n**Next Move Skipped Because No Move Is Possible**\n");
					nextMove = false;
				}
			}
		}
	}
}

