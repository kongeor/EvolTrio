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

public class DiversityFilterTest {
	
	private Configuration conf;
	
	@Before
	public void initialize() {
		conf = new Configuration();
	}

	@Test
	public void testEvaluate() throws InvalidConfigurationException {
		DiversityFilter filter = new DiversityFilter();
		Gene[] genes = new Gene[14];
		
		int[] notes = { 0, 2, 2, 1, -2, -2, 0 };
		int evaluation = 0;
		
		for(int note: notes)
			evaluation += Math.abs(note);
		
		int[] durations = { 0, 0, 0, 0, 0, 0, 0}; //duration are irrelevant
		
		for (int i = 0; i < genes.length/2; i++) {
			genes[i*2] = new IntegerGene(conf);
			genes[i*2].setAllele(new Integer(notes[i]));
			
			genes[(i*2)+1] = new IntegerGene(conf);
			genes[(i*2)+1].setAllele(new Integer(durations[i]));
		}
		
		
		IChromosome chromosome = new Chromosome(conf, genes);
		assertEquals("Result", DiversityFilter.bonusFactor*evaluation, filter.evaluate(chromosome), 0);
	}

}
