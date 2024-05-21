import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements a state and the playing logic of the TicTacToe game. 
 */
public class TicTacToeState implements State {
	private Square[] field; /**< The field, consisting of nine squares. First three values correspond to first row, and so on. */
	public Square player; /**< The player, either X or O. */
	public Square playerToMove; /**< The player that is about to move. */
	private float utility; /**< The utility value of this state. Can be 0, 1 (won) or -1 (lost).*/

	/**
	 * Updates the utility value.
	 */
	private void updateUtility() {
		/** TODO
		 * The utility value for the TicTacToe game is defined as follows:
		 * - if player has three marks in a row, it is 1
		 * - if the other player has three marks in a row, it is -1
		 * - otherwise it is 0
		 * Note that "three marks in a row" can actually be a row, a column
		 * or a diagonal. So basically, first find out if there are three
		 * identical values in a row, and if so, check whether the marks belong
		 * to player or not. 
		 */
		
		// row
		for (int i = 0; i < 9; i += 3) {
			if (field[i] == field[i + 1] && field[i + 1] == field[i + 2] && field[i] != Square.EMPTY) {
				if (playerToMove == Square.O) utility = 1;
				else utility = -1;
			}
		}

		// column
		for (int i = 0; i < 3; i++) {
			if (field[i] == field[i + 3] && field[i + 3] == field[i + 6] && field[i] != Square.EMPTY) {
				if (playerToMove == Square.O) utility = 1;
				else utility = -1;
			}
		}

		// diagonal
		if (field[0] == field[4] && field[4] == field[8] && field[4] != Square.EMPTY) {
			if (playerToMove == Square.O) utility = 1;
			else utility = -1;
		}
			
		
		if (field[2] == field[4] && field[4] == field[6] && field[4] != Square.EMPTY) {
			if (playerToMove == Square.O) utility = 1;
			else utility = -1;
		} 
	}
	
	/**
	 * Default constructor.
	 */
	public TicTacToeState() {
		field = new Square[9];
		for(int i = 0; i < 9; ++i) {
			field[i] = Square.EMPTY;
		}
		player = Square.X;
		playerToMove = Square.X;
		utility = 0;
	}
	
	@Override
	public List<TicTacToeAction> getActions() {
		/** TODO
		 * For the TicTacToe game, there is one valid action
		 * for each empty square. The action would then consist
		 * of the position of the empty square and the "color" of
		 * the player to move.
		 */

		List<TicTacToeAction> actions = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			if (playerToMove == Square.X) {
				if (field[i] == Square.EMPTY) 
					actions.add(new TicTacToeAction(Square.X, i));
			} else {
				if (field[i] == Square.EMPTY) 
					actions.add(new TicTacToeAction(Square.O, i));
			}
		}
		
		return actions;
	}

	@Override
	public float getUtility() {
		return utility;
	}

	@Override
	public State getResult(TicTacToeAction action) {
		/** TODO
		 * Create a new state and copy all the contents of the current state
		 * to the new one (in particular the field and the player). The
		 * player to move must be switched. Then incorporate the action into
		 * the field of the new state. Finally, compute the utility of the new
		 * state using updateUtility().
		 */

		TicTacToeState newState = new TicTacToeState();

		for(int i = 0; i < 9; i++){
			if ( i == action.position )
				newState.field[i] = action.player;
			else 
				newState.field[i] = this.field[i];
		}

		if (action.player == Square.X)
			newState.playerToMove = Square.O;
		else 
			newState.playerToMove = Square.X;

		newState.updateUtility();

		return newState;
	}

	@Override
	public boolean isTerminal() {
		/** TODO
		 * Hint: the utility value has specific values if one of
		 * the players has won, which is a terminal state. However,
		 * you will also have to check for terminal states in which
		 * no player has won, which can not be inferred immediately
		 * from the utility value.
		 */

		if (utility == 1 || utility == -1)
			return true;
		
		boolean allFilled = true;
		for (int i = 0; i < 9; i++) {
			if (field[i] == Square.EMPTY)
				allFilled = false;
		}
		if (allFilled) return true;

		return false;
	}

	@Override
	public void print() {
		String s = "" + field[0] + "|" + field[1] + "|" + field[2] + "\n";
		s += "-+-+-\n";
		s += field[3] + "|" + field[4] + "|" + field[5] + "\n";
		s += "-+-+-\n";
		s += field[6] + "|" + field[7] + "|" + field[8] + "\n";
		System.out.println(s);
	}
}
