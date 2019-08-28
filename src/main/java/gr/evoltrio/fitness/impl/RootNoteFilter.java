package gr.evoltrio.fitness.impl;

import gr.evoltrio.fitness.BaseFilter;
import gr.evoltrio.midi.MusicConfiguration;

import org.jgap.IChromosome;

public class RootNoteFilter extends BaseFilter {

	public final static int rootBonus = 20;
	
	public RootNoteFilter(double weigth) {
		super(weigth);
	}

	public RootNoteFilter() {}

	@Override
	public double evaluate(IChromosome chromo) {	
		int keyNote = MusicConfiguration.getInstance().getKeyNote();
		int currentNote = keyNote;
		// offset is needed for the cross-scale check
		int evaluation = 0;

		for (int i = 0; i < chromo.size(); i += 2) {
			currentNote += ((Integer) (chromo.getGene(i).getAllele()))
					.intValue();
		
			if ( keyNote == currentNote )
				evaluation += rootBonus;
			else if ((keyNote%12) == (currentNote%12))
				evaluation += rootBonus/2;	

		}

		return evaluation * getWeigth();
	}

}
