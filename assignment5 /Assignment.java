import java.util.HashMap;

/**
 * An assignment for the variables (or a subset of them) of a csp.
 *
 * @param <E> The type of the values of the variables.
 */
@SuppressWarnings("serial")
public class Assignment<E> extends HashMap<String, E> {
	
	/**
	 * Returns whether all variables of the csp have a value assigned.
	 * @param csp The underlying csp
	 * @return Whether the assignment is complete.
	 */
	public boolean isComplete(CSP<E> csp) {
		/* TODO
		 * Check if all variables in the csp are actually contained
		 * in the assignment.
		 */
		// List<String> variables
		for (String var: csp.variables) {
			if (!this.containsKey(var)) 
				return false;
		}
		return true;
	}
}
