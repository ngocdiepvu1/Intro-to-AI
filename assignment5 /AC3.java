import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class AC3 {
	/**
	 * This class represents a single arc for the AC-3 algorithm.
	 */
	public static class Arc {
		private String value1, value2;

		public Arc(String value1, String value2) {
			this.value1 = value1;
			this.value2 = value2;
		}
	}
	
	/**
	 * Implements the AC-3 algorithm to make a csp arc consistent.
	 * @param csp The csp
	 * @return Whether an inconsistency was found (false) or not (true)
	 * @throws Exception
	 */
	public static <E> boolean ac3(CSP<E> csp) throws Exception {
		/* TODO
		 * First, set up a queue of all arcs. For each constraint (you can assume that
		 * all constraints are binary constraints) add two arcs, one forward, and one
		 * backwards. Then implement the following (taken from text book):
		 * 
		 * while queue is not empty do
		 *   (Xi, Xj) <- REMOVE-FIRST(queue)
		 *   if REVISE(csp, Xi, Xj) then
		 *     if size of Di = 0 then return false
		 *     for each Xk in Xi.NEIGHBORS - {Xj} do
		 *       add (Xk, Xi) to queue
		 * return true
		 * 
		 * Note that Xi and Xj correspond to Arc.value1 and Arc.value2
		 * after some arc has been polled from the queue.
		 */
		Queue<Arc> q = new LinkedList<>();
		for (Constraint constraint: csp.constraints) {
			List<String> scope = constraint.getScope();
			q.add(new Arc(scope.get(0), scope.get(1)));
			q.add(new Arc(scope.get(1), scope.get(0)));
		}
		while (!q.isEmpty()) {
			Arc head = q.remove();

			String var1 = head.value1;
			String var2 = head.value2;

			Set<String> temp = neighbors(csp, var1);
			temp.remove(var2);

			if (revise(csp, var1, var2)) {
				if (csp.domains.get(var1).size() == 0) {
					return false;
				}
				for (String var3: temp) 
					q.add(new Arc(var3, var1));
			}
		}
		return true;
	}
	
	/**
	 * Implements the revise-routine of the AC-3 algorithm. Effectively iterates
	 * over all domain values of var1 and checks if there is at least 1 possible value
	 * for var2 remaining. If not, removes that value from the domain of var1.
	 * @param csp
	 * @param var1
	 * @param var2
	 * @return
	 */
	private static <E> boolean revise(CSP<E> csp, String var1, String var2) {
		/* TODO
		 * You may want to use a temporary Assignment to check whether a constraint
		 * is violated by any values for var1 and var2. Iterate over all domain values
		 * of var1. Then iterate over all domain values of var2 and prepare the
		 * temporary assignment accordingly. If all values for var2 produce an
		 * inconsistent assignment, remove the current value from the domain of
		 * var1. Hint: You cannot modify the domain as long as you are iterating over
		 * it, therefore I recommend to temporarily store the values to be deleted in
		 * a list or something, and then delete them all together after you iterated
		 * over all domain values. Also, don't forget to return whether you actually
		 * modified the domain of var1. 
		 *
		 * function REVISE(csp, Xi, Xj) returns true iff we revise the domain of Xi 
		    revised ← false
			for each x in Di do
			if no value y in Dj allows (x,y) to satisfy the constraint between Xi and Xj then delete x from Di
			revised ← true
			return revised
		 */

		boolean revised = false;
		boolean satisfied = false;

		Assignment<E> temp = new Assignment<>();
		List<E> removedVals = new ArrayList<>();
		
		// for each x in Di do
		// 0 1 2 3
		for (E x: csp.domains.get(var1)) {
			temp.put(var1, x);
			// for each y in Dj
			// 1
			for (E y: csp.domains.get(var2)) {
				temp.put(var2, y);
				// check if (x,y) to satisfy the constraint between Xi and Xj
				if (!x.equals(y))
					satisfied = true;
				temp.remove(var2, y);
			}
			temp.remove(var1, x);

			if (!satisfied) {
				removedVals.add(x);
				revised = true;
			}
			satisfied = false;
		}

		for (E x: removedVals)
			csp.domains.get(var1).remove(x);
	
		return revised;
	}
	
	/**
	 * Computes the "neighbors" of a variable in a CSP. A variable is
	 * a neighbor if it is coupled to another variable by a constraint.
	 * @param csp The csp
	 * @param var The variable the neighbors of which are to be found.
	 * @return The neighbors of the given variable.
	 */
	private static Set<String> neighbors(CSP<?> csp, String var) {
		/* TODO
		 * Iterate over all constraints and check if var is contained
		 * in the constraint's scope. If so, all _other_ variables of
		 * the constraint's scope are neighbors.
		 */
		Set<String> neighbors = new HashSet<String>();
		// public List<Constraint> constraints;
		for (Constraint constraint: csp.constraints) {
			List<String> scope = constraint.getScope();
			if (scope.indexOf(var) == 0)
				neighbors.add(scope.get(1));
			else if (scope.indexOf(var) == 1)
				neighbors.add(scope.get(0));
		}
		return neighbors;
	}
}
