package gr.evoltrio.fitness.impl;

import gr.evoltrio.fitness.BaseFilter;
import gr.evoltrio.midi.MusicConfiguration;
import org.jgap.IChromosome;

public class ScaleFilter extends BaseFilter {
	
	public static final int inScaleBonus = 20;
	
	public ScaleFilter(double weigth) {
		super(weigth);
	}

	public ScaleFilter() {}

	@Override
	public double evaluate(IChromosome chromo) {
		
		int currentNote = MusicConfiguration.getInstance().getKeyNote();
		int[] scale = MusicConfiguration.getInstance().getScaleIntervals();
		// offset is needed for the cross-scale check
		int offset = MusicConfiguration.getInstance().getKeyNote() % 12;
		int evaluation = 0;

		for (int i = 0; i < chromo.size(); i += 2) {
			currentNote += ((Integer) (chromo.getGene(i).getAllele()))
					.intValue();
			
			for(int note: scale)
				if ( note == ((currentNote%12) - offset))
					evaluation += inScaleBonus;
					

		}

		return evaluation * getWeigth();
	}

}
