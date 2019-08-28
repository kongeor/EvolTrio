package gr.evoltrio.fitness.impl;

import gr.evoltrio.fitness.BaseFilter;

import org.jgap.IChromosome;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class DiversityFilter extends BaseFilter {

	public DiversityFilter(double weigth) {
		super(weigth);
	}

	public DiversityFilter() {}

	public static final int bonusFactor = 3;
	
	@Override
	public double evaluate(IChromosome chromo) {		
		int evaluation = 0;
		
		for (int i = 0; i < chromo.size(); i += 2) {
			evaluation += Math.abs(((Integer) (chromo.getGene(i).getAllele()))
					.intValue()) * bonusFactor;
			evaluation += Math.abs(((Integer) (chromo.getGene(i+1).getAllele()))
					.intValue()) * bonusFactor;
		}

		return evaluation * getWeigth();
	}

}
