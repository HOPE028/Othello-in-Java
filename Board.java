package Proven;
import java.util.ArrayList;

public class Board {
	/*
	 * Prints a board with column and row numbers. All zeroes turn into empty spaces, 
	 * 1s into white circles, and -1s into black circles
	 */
	
	public void print(int[][] board) {
		System.out.print("    ");
		
		for (int i = 1; i <= board.length; i ++) {
			System.out.print(i + "   ");
		}
		
		System.out.println("\n   --- --- --- --- --- --- --- ---");
		
		for (int i = 0; i < board.length; i++) {
			System.out.print(i + 1 + " | ");
			for (int j = 0; j < board[0].length; j++) {
				if (board[j][i] == 0) {
					System.out.print(" " + " | ");
				} else {
					char temp = 's';
					if (board[j][i] == 1) {
						temp = '○';
					} else if (board[j][i] == -1) {
						temp = '●';
					}
					System.out.print(temp + " | ");
				}
			}
			System.out.println("\n   --- --- --- --- --- --- --- ---");
		}
	}
	
	/*
	 * Checks if game is over --> It receives who's turn is next. It then runs an 'isValid'
	 * function on every position of the board as the person's who's turn it is. If it finds 
	 * a valid spot, then the game will return false. If it never finds a possible spot,
	 * it will return true;
	 */
	
	public boolean gameOver(int[][] board, int person) {
		int height = 0;
		int width = 0;
		boolean found = false; 
		
		while (!found && height < 8) {
			width = 0;
			while (!found && width < 8) {
				if (isValid(board, height, width, person)) found = true;
				width++;
			}
			height++;
		}
		if (!found) return true;
		
		return false;
	}
	
	
	/* Returns whether all the open spots are gone --> Goes through all the indices and see's
	 * if there are any open spots (0). Returns true if there are no spots left.
	 */
	
	public boolean spotsGone(int[][] board) {
		int amountLeft = 0;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[j][i] == 0) amountLeft++;
			}
		}
		return amountLeft == 0 ? true : false;
	}
	
	/* Prints out a message telling the user who won and how they won --> The spotsGone
	 * parameter is for it to know what to check to figure out who won. If spotsGone is true,
	 * then it knows to check to see who has more pieces as that means all the possible spots
	 * have been filled. If false, then it means that one of the players doesn't have any moves left. 
	 * It hence checks if white has any possible moves. If it does, than white wins. 
	 * If it doesn't, then black wins.
	 */
	
	public void winner(int[][] board, boolean spotsGone) {
		if (spotsGone) {
			int white = 0;
			int black = 0;
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[j][i] == 1) white++;
					
					else if (board[j][i] == -1) black++;
				}
			}
			System.out.println();
			
			if (white == black) System.out.println("It is a TIE!");
			
			else if (white > black) System.out.println("WHITE WON!");
			
			else System.out.println("BLACK WON");
		} else {
			int height = 0;
			int width = 0;
			boolean found = false; 
			
			while (!found && height < 8) {
				width = 0;
				while (!found && width < 8) {
					if (isValid(board, height, width, 1)) found = true;
					width++;
				}
				height++;
			}
			System.out.println((found ? "Black WON! White" : "White WON, Black") + " ran out of possible moves");
		}
	}
	
	/*
	 * Sets up the board: Creates an 8 by 8 int array and fills it with 0. Then changes the values of
	 * center four to reflect how it would look like in an Othello game. (two black pieces diagonal of
	 * each other, two white pieces diagonal of each other)
	 */
	
	public static int[][] boardSetup() {
		int[][] board = new int[8][8]; // 0 => empty , -1 => black , 1 => white
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = 0;
			}
		}
		board[4][3] = 1;
		board[3][4] = 1;
		board[3][3] = -1;
		board[4][4] = -1;
		
		return board;
	}
	
	// Returns if a move is valid from the user.
	
	public boolean isValid(int[][] board, int x, int y, int person) {
		//out of scope of array
		if (x >= 8 || x < 0 || y >= 8 || y < 0) return false;
		
		//if it's not empty
		if (board[y][x] != 0) return false;
		
		//will it cause at least on swap?
		return isSwitch(board, x, y, person);
	}
	
	/*
	 *  Returns whether the move being done by the user flips at least one of the opponents pieces -->
	 *  Receives as parameters the location of the potential move. It then checks the 8 directions of 
	 *  that location. If it see's that one of the opponents pieces will be flipped in one of the
	 *  eight directions, it will return true. If it never finds a case where it does, it will 
	 *  return false.
	 */
	
	public boolean isSwitch(int[][] board, int x, int y, int person) {
		int[][] directions = {{-1,-1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
		
		int indexY, indexX;
		int opposite = person * -1;
		
		for (int i = 0; i < directions.length; i++) {
			indexY = y;
			indexX = x;
			int length = 0;
			boolean stop = false; 
			
			while(!stop) {
				length++;
				indexY += directions[i][0];
				indexX += directions[i][1];
				
				if (indexX >= 8 || indexX < 0 || indexY >= 8 || indexY < 0) break;
				
				if (board[indexY][indexX] != opposite) {
					if (board[indexY][indexX] != person) stop = true;
					else {
						if (length > 1) return true;
						stop = true;  
					}
				}
			}
		}
		return false;
	}
	
	/* 
	 * Places a piece on the board and flips whatever pieces need to be flipped -->
	 * Recieves a list of directions that it should flip from the switchDirections function.
	 * It then sends the directions values to the switchValues function.
	 */
	
	public void move(int[][] board, int x, int y, int person) {
		ArrayList<Integer> switchDirections = isSwitchDirections(board, x, y, person);
		
		switchValues(board, x, y, person, switchDirections);
	}
	
	/* 
	 * Flips all pieces that need to be flipped --> Receives a list of directions that need to be flipped.
	 * Loops over it. If the piece is the opponents piece, it will turn it into the players piece. If the 
	 * piece is the users, then it will stop going in that direction and will go to the next direction.
	 */
	
	public void switchValues(int[][] board, int x, int y, int person, ArrayList<Integer> listOfDirections) {
		int[][] directions = {{-1,-1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
		
		board[y][x] = person;
	
		int indexY, indexX;
		int opposite = person * -1;
		
		for (int i = 0; i < listOfDirections.size(); i++) {
			indexY = y;
			indexX = x;
			boolean stop = false; 
			int direction = listOfDirections.get(i);
			
			while(!stop) {
				indexY += directions[direction][0];
				indexX += directions[direction][1];
				
				if (indexX >= 8 || indexX < 0 || indexY >= 8 || indexY < 0) break;
				
				if (board[indexY][indexX] != opposite) {
					stop = true;
				}
				
				board[indexY][indexX] = person;
			}
		}
	}
	
	/* 
	 * Returns a list of the directions that need to be flipped --> Goes through the 8
	 * directions possible. For it to add a direction index to 'listOfDirections', it must fit
	 * the following criteria: There can't be any spaces between the initial location, the opponents
	 * pieces, and player piece. The order in which the pieces should be is the users original 
	 * piece, at least one of the opponents pieces, and finally your piece again. If both criteria
	 * evaluate to true, it will add that direction to the 'listOfDirections'.
	 */
	
	public ArrayList<Integer> isSwitchDirections(int[][] board, int x, int y, int person) {
		int[][] directions = {{-1,-1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}};
		ArrayList<Integer> listOfDirections = new ArrayList<Integer>();
		
		int indexY, indexX;
		int opposite = person * -1;
		
		for (int i = 0; i < directions.length; i++) {
			indexY = y;
			indexX = x;
			int length = 0;
			boolean stop = false; 
			
			while(!stop) {
				length++;
				indexY += directions[i][0];
				indexX += directions[i][1];
				
				if (indexX >= 8 || indexX < 0 || indexY >= 8 || indexY < 0) break;
				
				if (board[indexY][indexX] != opposite) {
					if (board[indexY][indexX] != person) stop = true;
					else {
						if (length > 1) listOfDirections.add(i);
						stop = true;  
					}
				}
			}
		}
		return listOfDirections;
	}
}