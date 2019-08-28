package gr.evoltrio.fitness.impl;

import gr.evoltrio.fitness.BaseFilter;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.midi.MusicFactory;

import org.jgap.IChromosome;

public class TimeFilter extends BaseFilter {

	public static int timeBonus = 10;
	
	public TimeFilter(double weigth) {
		super(weigth);
		// TODO Auto-generated constructor stub
	}

	public TimeFilter() {}

	@Override
	public double evaluate(IChromosome chromo) {

		int evaluation = 0;
		int currentDuration = MusicConfiguration.getInstance()
				.getBeginningDuration();
		double totalDuration = MusicFactory
				.convertToDoubleDuration(currentDuration);

		for (int i = 1; i < chromo.size(); i += 2) {
			if (totalDuration % 4 == 0)
				evaluation += 50;

			currentDuration = MusicFactory.calcDurationAtIndex(chromo, i,
					currentDuration);
			totalDuration += MusicFactory
					.convertToDoubleDuration(currentDuration);
		}

		return evaluation * getWeigth();
	}

}
