import java.lang.Math;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class that implements the MiniMax algorithm.
 */
public class MiniMax {
	private static int numberOfStates; /**< counter to measure the number of iterations / states. */
	private static boolean usePruning;
	
	/**
	 * Start procedure of the MiniMax algorithm.
	 * @param state The state where the MiniMax algorithm starts searching
	 * @param usePruning Whether to use alpha-beta-pruning
	 * @return An optimal action to be taken at this point.
	 */
	public static TicTacToeAction MinimaxDecision(State state, boolean usePruning) {
		MiniMax.usePruning = usePruning;
		numberOfStates = 0;
		
		/* TODO
		 * Implement the minimax decision routine. Iterate over all possible actions
		 * and evaluate their utilities invoking MinValue(). Return the action that
		 * generates the highest utility.
		 * You can just return the first or the last best action, however it makes
		 * the algorithm way more interesting if you determine all best actions
		 * and then select one of them randomly.
		 */

		HashMap<Integer, Float> utilities = new HashMap<>();
		List<TicTacToeAction> actions = state.getActions();
		
		for (TicTacToeAction action: actions) {
			float v = MinValue(state.getResult(action), Integer.MAX_VALUE, Integer.MIN_VALUE);
			System.out.print(action.position + " ");
			System.out.println(v);
			utilities.put(action.position, v);
		}
		
		float maxUtility = Integer.MIN_VALUE;

		for (int key: utilities.keySet()) {
			if (utilities.get(key) > maxUtility)
				maxUtility = utilities.get(key);
		}

		System.out.println("Max Utility: " + maxUtility);

		List<Integer> bestActions = new ArrayList<>();
		for (int key: utilities.keySet()) {
			if (utilities.get(key) == maxUtility)
				bestActions.add(key);	
		}
		

		System.out.println("State space size: " + numberOfStates);
		int index = (int)(Math.random() * bestActions.size());
		
		//System.out.println("State space size: " + numberOfStates);
		//int index = (int)(Math.random() * bestPositions.size());

		return new TicTacToeAction(Square.X, bestActions.get(index));
	}
	
	/**
	 * @param state The current state to be evaluated
	 * @param alpha The current value for alpha
	 * @param beta The current value for beta
	 * @return The maximum of the utilites invoking MinValue, or the utility of the state if it is a leaf.
	 */
	private static float MaxValue(State state, float alpha, float beta) {
		++numberOfStates;
		
		/*
		 * TODO implement the MaxValue procedure according to the textbook:
		 * 
		 * function Max-Value(state, alpha, beta) return a utility value
		 *   if TERMINAL-TEST(state) then return UTILITY(state)
		 *   v <- -infinity
		 *   for each a in ACTIONS(State) do
		 *     v <- max(v, MIN-VALUE(RESULT(state, a), alpha, beta))
		 *     if MiniMax.usePruning then
		 *       if v >= beta then return v
		 *       alpha <- max(alpha, v)
		 *   return v
		 *   
		 *   The pseudo code is slightly changed to be able to reuse the
		 *   code for alpha-beta-pruning.
		 */

		if (state.isTerminal())
			return state.getUtility();
		
		float v = Integer.MIN_VALUE;
		List<TicTacToeAction> actions = state.getActions();
		for (TicTacToeAction action: actions) {	
			v = Math.max(v, MinValue(state.getResult(action), alpha, beta));
			if (usePruning) {
				if (v >= beta)
					return v;
				alpha = Math.max(alpha, v);
			}
		}

		return v;
	}
	
	/**
	 * @param state The current state to be evaluated
	 * @param alpha The current value for alpha
	 * @param beta The current value for beta
	 * @return The minimum of the utilites invoking MaxValue, or the utility of the state if it is a leaf.
	 */
	private static float MinValue(State state, float alpha, float beta) {
		++numberOfStates;
		
		/*
		 * TODO implement the MaxValue procedure according to the textbook:
		 * 
		 * function Min-Value(state, alpha, beta) return a utility value
		 *   if TERMINAL-TEST(state) then return UTILITY(state)
		 *   v <- +infinity
		 *   for each a in ACTIONS(State) do
		 *     v <- min(v, MAX-VALUE(RESULT(state, a), alpha, beta))
		 *     if MiniMax.usePruning then
		 *       if v <= alpha then return v
		 *       beta <- min(beta, v)
		 *   return v
		 *   
		 *   The pseudo code is slightly changed to be able to reuse the
		 *   code for alpha-beta-pruning.
		 */

		if (state.isTerminal())
			return state.getUtility();
		
		float v = Integer.MAX_VALUE;
		for (TicTacToeAction action: state.getActions()) {	
			v = Math.min(v, MaxValue(state.getResult(action), alpha, beta));
			if (usePruning) {
				if (v <= alpha)
					return v;
				beta = Math.min(beta, v);
			}
		}

		return v;
	}
}
