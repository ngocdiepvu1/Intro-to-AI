public class Individual {
	private final int NUM_COLORS = 4;
	private Map map; // the map
	private double fitness; // fitness is cached and only updated on request whenever necessary
	// fitness shows how good a state is, a higher number for a better state
	//private String[] colors = new String[]{"Orange", "Yellow", "Green", "Blue"};
	// TODO Representation of the genom of the individual, 10 numbers for 10 states
	private int[] genom = new int[51];
	//private int stateSize = map.states.size();

	/**
	 * Updates the fitness value based on the genom and the map.
	 * the number of neighboring state pairs that have different colors
	 */
	public void updateFitness() {
		// TODO implement fitness function
		fitness = 0;
		for (Border border: map.borders ) {
			if (genom[border.index1] != genom[border.index2])
				fitness++;
		}
	}

	/**
	 * Default ctor. Creates a (valid) random individual.
	 * @param map The US states map.
	 */
	public Individual(Map map) {
		this.map = map;
	
		// TODO implement random generation of an individual
		for (int i = 0; i < map.states.size(); i++) {
			genom[i] = (int)(Math.random() * NUM_COLORS);
		} 

		updateFitness();				
	}

	/**
	 * Reproduces a child randomly from two individuals (see textbook).
	 * crossover point is randomly chosen
	 * @param x The first parent.
	 * @param y The second parent.
	 * @return The child created from the two individuals.
	 */
	public static Individual reproduce(Individual x, Individual y) {
		Individual child = new Individual(x.map);

		// TODO reproduce child from individuals x and y

		// Copy from x
		int randomCrossoverPoint = (int)(Math.random() * (x.map.states.size()));
		for (int i = 0; i < randomCrossoverPoint; i++) {
			child.genom[i] = x.genom[i];
		}

		// Copy from y
		for (int i = randomCrossoverPoint; i < x.map.states.size(); i++) {
			child.genom[i] = y.genom[i];
		}

		child.updateFitness();

		Individual child1 = new Individual(x.map);

		// Copy from x
		int randomCrossoverPoint1 = (int)(Math.random() * (x.map.states.size() - 1));
		for (int i = 0; i < randomCrossoverPoint1; i++) {
			child1.genom[i] = x.genom[i];
		}

		// Copy from y
		for (int i = randomCrossoverPoint1; i < x.map.states.size(); i++) {
			child1.genom[i] = y.genom[i];
		}

		child1.updateFitness();
		if (child1.fitness > child.fitness)
			return child1;

		return child;
	}

	/**
	 * Returns the current fitness value of the individual.
	 * @return The current fitness value.
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * Randomly mutates the individual with a small independent probability
	 */
	public void mutate() {
		// TODO implement random mutation of the individual
		// Change a random state to a random color
		int randomStateInd = (int)(Math.random() * (map.states.size()));
		int randomColor = (int)(Math.random() * NUM_COLORS);
		genom[randomStateInd] = randomColor;

		updateFitness();
	}

	/**
	 * Checks whether the individual represents a valid goal state.
	 * @return Whether the individual represents a valid goal state.
	 */
	public boolean isGoal() {
		return fitness == map.borders.size();
	}
	
	/**
	 * Prints out the individual to the console.
	 */
	void print() {
		// TODO implement printing the individual in the following format:
		// fitness: 15
		// North Carolina: 0
		// South Carolina: 2
		// ...
		System.out.println("fitness: " + fitness);
		for (int i = 0; i < map.states.size(); i++) {
			System.out.println(map.states.get(i) + ": " + genom[i]);
			//System.out.println(map.states.get(i) + ": " + colors[genom[i]]);
		}
	}
}
