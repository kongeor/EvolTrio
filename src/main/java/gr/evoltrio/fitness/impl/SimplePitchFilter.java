package gr.evoltrio.fitness.impl;

import gr.evoltrio.fitness.BaseFilter;

import org.jgap.IChromosome;

public class SimplePitchFilter extends BaseFilter {

	public static final int pitchBonus = 10;
	
	public SimplePitchFilter(double weigth) {
		super(weigth);
	}

	public SimplePitchFilter() {}

	@Override
	public double evaluate(IChromosome chromo) {
		int zeroCount = 0;
		int evaluation = 0;

		for (int i = 0; i < chromo.size(); i += 2) {
			if (((Integer) (chromo.getGene(i).getAllele())).intValue() == 0)
				zeroCount += 2;
			else {
				evaluation += pitchBonus * zeroCount;
				zeroCount = 0;
			}

			
		}
		return evaluation;
	}

}
