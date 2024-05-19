import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implements the backtracking search with forward checking. 
 */
public class ForwardCheckingCSPSolver extends BacktrackingCSPSolver {
	
	/**
	 * Implements the actual forward checking. Infers the values to be deleted
	 * from the domains of some variables based on the given variable and value.
	 */
	@Override
	protected <E> Inference<E> inference(CSP<E> csp, Assignment<E> assignment, String var, E value) {
		/* TODO
		 * Implement the forward checking. You may want to iterate over all
		 * constraints to identify those who involve the given variable. Then,
		 * iterate over the variables of the scope of the constraint and check
		 * if the variable is not yet assigned. If it is not assigned, check all
		 * the values of the domain of that variable, and identify those values
		 * that are inconsistent with the constraint (therefore, you might temporarily
		 * modify the assignment with the value to test, and restore the assignment
		 * later on). The inconsistent values should be added to the inference that
		 * will be returned. If no value was found at all, then return failure (null in this
		 * case).
	
		 */
		Inference<E> inferences = new Inference<>();

		ArrayList<Constraint> constraintArr = new ArrayList<>();
		//temporarily modify the assignment with the value to test
		assignment.put(var, value);

		// iterate over all constraints
		for (Constraint constraint: csp.constraints) {
			List<String> scope = constraint.getScope();
			// identify the constraints that involve the given variable
			if (scope.contains(var))
				constraintArr.add(constraint);
		}

		// iterate over the variables of the scope of the constraint
		for (Constraint constraint: constraintArr) {
			List<String> scope = constraint.getScope();
			String otherVar;
			if (scope.get(0).equals(var)) {
				otherVar = scope.get(1);
			} else {
				otherVar = scope.get(0);
			}

			// if the variable of the scope is unassigned
			if (assignment.get(otherVar) == null) {
				Set<E> inconsistentVals = new HashSet<>();
				// check all the values in the domain
				for (E x: csp.domains.get(otherVar)) {
					assignment.put(otherVar, x);
					// identify those values that are inconsistent with the constraint
					if (!constraint.isConsistent(assignment))
						inconsistentVals.add(x);
				}
				if (!inconsistentVals.isEmpty())
					inferences.put(otherVar, inconsistentVals);
			}
		}
		// restore the assignment
		assignment.remove(var, value);

		if (!inferences.isEmpty()) 
			return inferences;

		return null;
	}
}
