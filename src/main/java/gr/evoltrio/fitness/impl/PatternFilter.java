package gr.evoltrio.fitness.impl;

import gr.evoltrio.fitness.BaseFilter;

import org.jgap.IChromosome;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class PatternFilter extends BaseFilter {

	public static final double baseBonus = 2;
	
	public PatternFilter(double weigth) {
		super(weigth);
	}

	public PatternFilter() {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gr.evomusic.evoltrio.fitness.IFitnessFilter#evaluate(org.jgap.IChromosome
	 * , int[], int, int)
	 */
	@Override
	public double evaluate(IChromosome chromo) {
		double evaluation = 0;

		evaluation = evaluatePattern(chromo, 1) + evaluatePattern(chromo, 2) +
				evaluatePattern(chromo, 3) + evaluatePattern(chromo, 4);
		return evaluation * getWeigth();
	}
	
	private double evaluatePattern(IChromosome chromo, int degree) {
		double evaluation = 0;
		int size = chromo.size();
		int pattern = 0 ;
		double value = 0;
		for (int i = 0; i < size; i += 2) {
			for(int j=i; j< ((i+degree*2)<size ? i+degree*2 : size); j+=2) {
				value =  ((Integer) (chromo.getGene(i+1).getAllele())).intValue();
				if(value != 0)
					pattern += value;
			}
			
			if(pattern == 0) {
				evaluation += Math.pow(baseBonus, degree);
				pattern = 0;
			}
			
		}
		
		return evaluation;
	}

}
