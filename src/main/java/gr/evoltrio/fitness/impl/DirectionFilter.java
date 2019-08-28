package gr.evoltrio.fitness.impl;

import gr.evoltrio.fitness.BaseFilter;

import org.jgap.IChromosome;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class DirectionFilter extends BaseFilter {

	DirectionFilter(double weigth) {
		super(weigth);
	}

	public DirectionFilter() {}

	private static final double directionBonus = 1.2;

	@Override
	public double evaluate(IChromosome chromo) {
		double evaluation = 0;
		int direction = 0;

		int currentDirection = 0;

		double directionFactor = directionBonus;

		for (int i = 0; i < chromo.size(); i += 2) {
			currentDirection = ((Integer) (chromo.getGene(i).getAllele()))
					.intValue();

			if ((currentDirection > 0 && direction > 0)
					|| (currentDirection < 0 && direction < 0))
				directionFactor *= directionBonus;
			// direction changed or stayed the same
			else if ((direction == 0 && (direction > 0 || direction < 0))) {
				// nothing
			} else {
				evaluation += directionFactor;
				directionFactor = directionBonus;
			}

			direction = currentDirection;
		}

		return evaluation * getWeigth();
	}

}
