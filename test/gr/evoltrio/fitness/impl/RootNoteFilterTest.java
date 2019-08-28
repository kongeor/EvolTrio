package gr.evoltrio.fitness.impl;

import static org.junit.Assert.assertEquals;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;
import org.junit.Before;
import org.junit.Test;

public class RootNoteFilterTest {
	
	private Configuration conf;
	
	@Before
	public void initialize() {
		conf = new Configuration();
	}

	@Test
	public void testEvaluate() throws InvalidConfigurationException {
		//change to the key of D
		//MusicConfiguration.getInstance().setRootNote("D");
		
		
		RootNoteFilter filter = new RootNoteFilter();
		Gene[] genes = new Gene[14];
		
		int[] notes = { 0, 2, 10, 0, -2, -10, 0 }; //5 root notes, 3 same and 2 one octave up
		int evaluation = 80;
		int[] durations = { 0, 0, 0, 0, 0, 0, 0}; //duration are irrelevant
		
		for (int i = 0; i < genes.length/2; i++) {
			genes[i*2] = new IntegerGene(conf);
			genes[i*2].setAllele(new Integer(notes[i]));
			
			genes[(i*2)+1] = new IntegerGene(conf);
			genes[(i*2)+1].setAllele(new Integer(durations[i]));
		}
		
		IChromosome chromosome = new Chromosome(conf, genes);
		assertEquals("Result", evaluation, filter.evaluate(chromosome), 0);
	}
}
