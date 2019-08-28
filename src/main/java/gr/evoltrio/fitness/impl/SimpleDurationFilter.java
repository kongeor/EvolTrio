package gr.evoltrio.fitness.impl;

import gr.evoltrio.fitness.BaseFilter;

import org.jgap.IChromosome;

public class SimpleDurationFilter extends BaseFilter {

	public static final double durationBonus = 1.2;
	
	public SimpleDurationFilter(double weigth) {
		super(weigth);
		// TODO Auto-generated constructor stub
	}

	public SimpleDurationFilter() {}

	@Override
	public double evaluate(IChromosome chromo) {

		int zeroCount = 1;
		int evaluation = 0;

		for (int i = 1; i < chromo.size(); i += 2) {
			if (((Integer) (chromo.getGene(i).getAllele())).intValue() == 0)
				zeroCount ++;
			else {
				evaluation += Math.pow(durationBonus,zeroCount);
				zeroCount = 1;
			}
		}
		return evaluation * getWeigth();
	}
}
