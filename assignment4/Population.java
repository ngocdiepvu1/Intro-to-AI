import java.util.Vector;
/**
 * Class representing a population of individuals
 */
public class Population extends Vector<Individual> {
	private Map map;
	//private Vector<Individual> individuals = new Vector<>(51);
		
	/**
	 * Actual standard ctor.
	 * @param map The map.
	 * @param initialSize The initial size of the population.
	 */
	Population(Map map, int initialSize) {
		for(int i = 0; i < initialSize; ++i)
		{
			add(new Individual(map));
		}
	}
	
	/**
	 * Standard ctor.
	 * @param map The map.
	 */
	public Population(Map map) {
		this(map, 0);
	}
	
	/**
	 * Randomly selects an individual out of the population
	 * proportionally to its fitness.
	 * @return The selected individual.
	 */
	Individual randomSelection() {
		// TODO implement random selection
		// an individual should be selected with a probability
		// proportional to its fitness
		double totalFitness = 0;
		for (Individual individual: this) {
			totalFitness += individual.getFitness();
		}

		double rand = Math.random() * totalFitness;

		double count = 0;
		for (Individual individual: this) {
			count += individual.getFitness();
			if (count >= rand) {
				return individual;
			}
		}

		// Placeholder, should never get here
		//return firstElement();
		return firstElement();
	}
}
