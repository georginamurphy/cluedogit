import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player implements BoardPiece {

	private Game game;
	private Location location;
	private Character character;
	private ArrayList<Card> cards;
	private boolean used;

	public Player(Character character, boolean used) {
		this.character = character;
		this.location = character.getStartLoc();
		this.used = used;
		this.cards = new ArrayList<Card>();
	}

	public Location getLocation() {
		return location;
	}

	public boolean getUsed() {
		return used;
	}

	public boolean hasCard(Card card) {
		if (cards.contains(card))
			return true;
		else
			return false;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void dealCard(Card card) {
		this.cards.add(card);
	}

	public void printCards() {
		System.out.println("----------------------------");
		System.out.println(this.character.name + "'s cards are: ");
		for (Card c : cards) {
			System.out.println(c.toString());
		}
		System.out.println("----------------------------");
	}

	/**
	 * A simple method that will generate two values between 1 and 6, inclusive
	 * and return their sum.
	 */
	public int rollDice() {
		int roll = (int) (Math.random() * 6) + 1;
		int roll2 = (int) (Math.random() * 6) + 1;
		return roll + roll2;
	}

	/**
	 * Small method to initiate a player's turn Will print the cards they have
	 * and show the board's current state
	 * 
	 */
	public void startTurn() {
		System.out.println(game.getBoard().toString());
		printCards();
		int roll = rollDice();
		System.out.println("You rolled a " + roll + "!");
		makeMovementDecisions(roll);
	}

	/**
	 * A method to handle how a player move's during their turn A player does
	 * this by entering in each direction they wish to move one by one, until
	 * they either run out of moves or move into a room.
	 * 
	 * If a player moves into a room, they forfeit any remaining moves they have
	 * left.
	 * 
	 */
	public void makeMovementDecisions(int roll) {
		Scanner input; 
		System.out.println("It is time to move your character on the board.");
		System.out.println("You will have four options for each of your " + roll + " moves.");
		System.out.println("1. Up");
		System.out.println("2. Down");
		System.out.println("3. Left");
		System.out.println("4. Right");
		int movesRemaining = roll;
		boolean enteredRoom = false;

		while (movesRemaining != 0 && !enteredRoom) {
			input =  new Scanner(System.in);
			System.out.println("You have " + movesRemaining + " moves remaining.\n");
			System.out.println("Where would you like to move? (enter a number)");
			int option = 0;
			Game.Direction direction;

			try {
				option = input.nextInt();
			} catch (InputMismatchException e) { // if an int was not entered
				System.out.println("Only numbers are accepatble input. Please try again");
				continue;
			}

			// Check they have entered a valid direction
			boolean validMove = false;
			if (option == 1) {
				direction = Game.Direction.UP;
				validMove = this.game.checkValidMove(this, direction);
			} else if (option == 2) {
				direction = Game.Direction.DOWN;
				validMove = this.game.checkValidMove(this, direction);
			} else if (option == 3) {
				direction = Game.Direction.LEFT;
				validMove = this.game.checkValidMove(this, direction);
			} else if (option == 4) {
				direction = Game.Direction.RIGHT;
				validMove = this.game.checkValidMove(this, direction);
			} else {
				System.out.println("That was not a valid expression, please select an option from 1 to 4.");
				continue;
			}

			// IF the move was invalid, continue to the next iteration of the
			// while loop
			// Otherwise, apply the move and increment movesMade
			if (validMove) {
				this.game.applyMove(this, direction);
				movesRemaining--;
			}
			
			 input.close();
		}



	}

	/**
	 * A simple method to update the Location for this player
	 */
	public void updateLocation(Location location) {
		this.location = location;
	}

	public String toString() {
		switch (this.character.colour) {
		case WHITE:
			return "1";
		case GREEN:
			return "2";
		case BLUE:
			return "3";
		case PURPLE:
			return "4";
		case RED:
			return "5";
		case YELLOW:
			return "6";
		}
		return "";
	}
}
