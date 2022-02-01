package Proven;
import java.util.Scanner;

public class Players {
	private int inputX, inputY, person;
	private Scanner in = new Scanner(System.in);
	
	//Constructor
	public Players(int person) {
		this.person = person;
	}
	
	/* Receives inputs from the user --> Takes input from the user telling it where 
	 * it wants to go. It will then check if that move is valid through the 'isValid' 
	 * function. If it is not, then it will force the user to enter new inputs. Once
	 * the inputs are valid, it will then call the move and print functions
	 * in the Board class.
	 */
	
	public void move(Board BOARD, int[][] board) {
		boolean succesful = false;
		while (!succesful) {
			//Gives Directions
			System.out.println("\nEnter Y and X: " + (this.person == 1 ? "White" : "Black"));
			
			//Receives input
			inputX = in.nextInt() - 1;
			inputY = in.nextInt() - 1;
			
			//Checks if inputs are valid
			succesful = BOARD.isValid(board, this.inputX, this.inputY, this.person);
			
			//Tells user if input were succesful.
			System.out.println(succesful);
		}
		//Makes the move and prints out the new board
		BOARD.move(board, this.inputX, this.inputY, this.person);
		BOARD.print(board);
	}
	
	public int getPerson() {
		return person;
	}
}
