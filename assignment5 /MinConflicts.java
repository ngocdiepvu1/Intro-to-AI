import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implements the min-conflict local search CSP solver 
 */
public class MinConflicts implements CSPSolver {

	@Override
	public <E> CSPResult<E> solve(CSP<E> csp) {
		/* TODO
		 * find reasonable value for max iterations.
		 */
		return minConflicts(csp, 500);
	}
	
	/**
	 * Implements the min-conflicts algorithm.
	 * @param csp The csp to solve
	 * @param maxSteps The max number of steps
	 * @return A solution to the csp, or null if none was found.
	 */
	private static <E> CSPResult<E> minConflicts(CSP<E> csp, int maxSteps) {
		/* TODO
		 * The implementation can pretty much follow the pseudo code
		 * in the text book:
		 * 
		 * current <- an initial complete assignment for csp
		 * for i = 1 to max_steps do
		 *   if current is a solution for csp then return current
		 *   var <- a randomly chosen conflicted variable from csp.VARIABLES
		 *   value <- the value v for var that minimizes CONFLICTS(var, v, current, csp)
		 *   set var = value in current
		 * return failure
		 * 
		 * Most of it is straight forward, because there is a separate
		 * function for it. Only finding the value that minimizes the
		 * number of conflicts is a little more complicated. However,
		 * you should use the function conflicts() for this purpose.
		 * Also, please note that "failure" is "null" in this implementation.
		 * You should return the result like "new CSPResult(current, i);"
		 */
		Assignment<E> current = new Assignment<>();

		current = createCompleteAssignment(csp);
		int i = 1;

		for (i = 1; i < maxSteps; i++) {
			E chosenVal = null;
			int conflict = 0;

			// if current is a solution for csp then return current
			for (Constraint constraint: csp.constraints) {
				if (!constraint.isConsistent(current))
					conflict++;
			}
			if (conflict == 0) 
				return new CSPResult<E>(current, i);

			String randomVar = getRandomConflictedVariable(current, csp);
			List<E> randomVarDomain = csp.domains.get(randomVar);
			chosenVal = randomVarDomain.get(0);
			int minConflicts = conflicts(randomVar, chosenVal , current, csp);

			for (int j = 1; j < randomVarDomain.size(); j++) {
				E value = randomVarDomain.get(j);
				conflict = conflicts(randomVar, value , current, csp);
				if (conflict < minConflicts) {
					minConflicts = conflict;
					chosenVal = value;
				}
			}
			E temp = current.get(randomVar);
			current.replace(randomVar, temp, chosenVal);
		}
		return null;
	}
	
	/**
	 * Randomly selects a conflicted variable.
	 * @param current The current assignment
	 * @param csp The csp
	 * @return A randomly chosen conflicted variable.
	 */
	private static <E> String getRandomConflictedVariable(Assignment<E> current, CSP<E> csp) {
		/* TODO
		 * First, you should create an initially empty set of conflicted variables.
		 * Then, iterate over all constraints, and if it is not consistent, add
		 * all the variables from its scope to the set of conflicted variables.
		 * Afterwards, just return a randomly selected one of them. (Hint: make
		 * sure that variables that appear in multiple constraints are not
		 * selected with a higher probability, they should be selected unbiased).
		 */
		Set<String> conflictedVarSet = new HashSet<>();
		for (Constraint constraint: csp.constraints) {
			if (!constraint.isConsistent(current)) {
				for (String val: constraint.getScope())
					conflictedVarSet.add(val);
			}
		}
		String[] conflictedVarArray = conflictedVarSet.toArray(new String[conflictedVarSet.size()]);

		return conflictedVarArray[(int)(Math.random() * conflictedVarArray.length)];
	}
	
	/**
	 * Creates an assignment in which every varibale is assigned a value from its domain.
	 * @param csp The underlying csp that defines the domains and the variables
	 * @return A complete assignment
	 */
	private static <E> Assignment<E> createCompleteAssignment(CSP<E> csp) {
		/* TODO
		 * create a new assignment and randomly assign a value to every
		 * variable from its domain.
		 */
		
		Assignment<E> temp= new Assignment<>();
		for (String variable: csp.variables) {
			temp.put(variable, csp.domains.get(variable).get((int)(Math.random() * csp.domains.get(variable).size())));
		}
		return temp;
	}
	
	/**
	 * Computes the number of conflict based on an assignment, but with one variable
	 * set to a specific value.
	 * @param var The variable to be checked
	 * @param value The value to be checked
	 * @param current The current assignment used as basis
	 * @param csp The csp problem
	 * @return The number of conflict given the current assignment, but with var=value
	 */
	private static <E> int conflicts(String var, E value, Assignment<E> current, CSP<E> csp) {
		/* TODO
		 * You might want to temporarily modify the assignment
		 * to set var = value (undo this afterwards!). Then
		 * iterate over all constraints and count the number of
		 * insonsistent constraints.
		 */
		
		E temp = current.get(var);
		current.replace(var, temp, value);
		int conflict = 0;

		for (Constraint constraint: csp.constraints) {
			if (!constraint.isConsistent(current))
				conflict++;
		}

		current.replace(var, value, temp);

		return conflict;
	}
}