package gr.evoltrio.core;

import gr.evoltrio.fitness.SoloFitnessEvol;

import org.jgap.Configuration;
import org.jgap.DefaultFitnessEvaluator;
import org.jgap.InvalidConfigurationException;
import org.jgap.event.EventManager;
import org.jgap.impl.BestChromosomesSelector;
import org.jgap.impl.CauchyRandomGenerator;
import org.jgap.impl.ChromosomePool;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.GABreeder;
import org.jgap.impl.GaussianRandomGenerator;
import org.jgap.impl.MutationOperator;
import org.jgap.impl.StockRandomGenerator;
import org.jgap.impl.ThresholdSelector;
import org.jgap.impl.TournamentSelector;
import org.jgap.impl.WeightedRouletteSelector;

/**
 *
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class EvolConfiguration {

	private Configuration conf;

	public static final int DEFAULT_POP_SIZE = 30;

//	public static final String[] NATURALSELECTORS = { "best", "threshold",
//			"tournament", "weighted" };
	public static final String[] NATURALSELECTORS = { "best", "threshold",
		"tournament" };

	public static final String[] RANDOMGENERATORS = { "stock", "cauchy",
			"gaussian" };

	private String randomGen = "stock";
	private String naturalSel = "best";
	// TODO change this with allow doublette
	private boolean executeNaturalBefore = false;
	private int minPopSizePercent = 0;
	private double selectFromPrevGen = 1.0d;
	private boolean keepPopSizeConstant = false;
	private double crossoverRate = 0.65d;
	private int mutationRate = 42;
	
	private int iterations = 100;

	private SoloFitnessEvol soloFitnessEvol;

	private int population = DEFAULT_POP_SIZE;

	public EvolConfiguration() {
		soloFitnessEvol = new SoloFitnessEvol();
	}
	
	public Configuration getConf() {
		return conf;
	}
	
	public int getPopulationSize() {
		return population;
	}
	
	public void setPopulationSize(int population) {
		this.population = population;
	}
	
	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	/**
	 * @return the randomGen
	 */
	public String getRandomGen() {
		return randomGen;
	}

	// TODO use ENUMs or smt
	public String getRandomGen(int index) {
		return RANDOMGENERATORS[index];
	}

	public int getRandomGen(String name) {
		for (int i = 0; i < RANDOMGENERATORS.length; i++) {
			if (RANDOMGENERATORS[i].equalsIgnoreCase(name))
				return i;
		}

		return -1;
	}

	/**
	 * @param randomGen
	 *            the randomGen to set
	 */
	public void setRandomGen(String randomGen) {
		this.randomGen = randomGen;
	}
	
	public String getNaturalSel(int index) {
		return NATURALSELECTORS[index];
	}

	public int getNaturalSel(String name) {
		for (int i = 0; i < NATURALSELECTORS.length; i++) {
			if (NATURALSELECTORS[i].equalsIgnoreCase(name))
				return i;
		}

		return -1;
	}

	/**
	 * @return the naturalSel
	 */
	public String getNaturalSel() {
		return naturalSel;
	}

	/**
	 * @param naturalSel
	 *            the naturalSel to set
	 */
	public void setNaturalSel(String naturalSel) {
		this.naturalSel = naturalSel.trim();
	}

	/**
	 * @return the executeNaturalBefore
	 */
	public boolean isExecuteNaturalBefore() {
		return executeNaturalBefore;
	}

	/**
	 * @param executeNaturalBefore
	 *            the executeNaturalBefore to set
	 */
	public void setExecuteNaturalBefore(boolean executeNaturalBefore) {
		this.executeNaturalBefore = executeNaturalBefore;
	}

	/**
	 * @return the minPopSizePercent
	 */
	public int getMinPopSizePercent() {
		return minPopSizePercent;
	}

	/**
	 * @param minPopSizePercent
	 *            the minPopSizePercent to set
	 */
	public void setMinPopSizePercent(int minPopSizePercent) {
		this.minPopSizePercent = minPopSizePercent;
	}

	/**
	 * @return the selectFromPrevGen
	 */
	public double getSelectFromPrevGen() {
		return selectFromPrevGen;
	}

	/**
	 * @param selectFromPrevGen
	 *            the selectFromPrevGen to set
	 */
	public void setSelectFromPrevGen(double selectFromPrevGen) {
		this.selectFromPrevGen = selectFromPrevGen;
	}

	/**
	 * @return the keepPopSizeConstant
	 */
	public boolean isKeepPopSizeConstant() {
		return keepPopSizeConstant;
	}

	/**
	 * @param keepPopSizeConstant
	 *            the keepPopSizeConstant to set
	 */
	public void setKeepPopSizeConstant(boolean keepPopSizeConstant) {
		this.keepPopSizeConstant = keepPopSizeConstant;
	}

	/**
	 * @return the crossoverRate
	 */
	public double getCrossoverRate() {
		return crossoverRate;
	}

	/**
	 * @param crossoverRate
	 *            the crossoverRate to set
	 */
	public void setCrossoverRate(double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	/**
	 * @return the mutationRate
	 */
	public int getMutationRate() {
		return mutationRate;
	}

	/**
	 * @param mutationRate
	 *            the mutationRate to set
	 */
	public void setMutationRate(int mutationRate) {
		this.mutationRate = mutationRate;
	}
	
	public SoloFitnessEvol getSoloFitnessEvol() {
		return soloFitnessEvol;
	}

	public Configuration buildConfiguration() {
		conf = new Configuration();
		
		try {
			// set the ones according to DefaultConfiguration in jgap
			conf.setBreeder(new GABreeder());
			conf.setEventManager(new EventManager());
			conf.setFitnessEvaluator(new DefaultFitnessEvaluator());
			conf.setChromosomePool(new ChromosomePool());

			// set the Random Generator
			if (randomGen.equalsIgnoreCase("Stock")) {
				conf.setRandomGenerator(new StockRandomGenerator());
			} else if (randomGen.equalsIgnoreCase("Cauchy")) {
				// setting a Cauchy Random Gen, with default values TODO, add
				// parameters
				conf.setRandomGenerator(new CauchyRandomGenerator());
			} else if (randomGen.equalsIgnoreCase("Gaussian")) {
				// the same as before, TODO ... the same as before
				conf.setRandomGenerator(new GaussianRandomGenerator());

			}
			else {
				System.out.println("Setting default Random Generator -- This should never happer!");
				conf.setRandomGenerator(new StockRandomGenerator());
			}

			// set the Natural Selector
			if (naturalSel.equalsIgnoreCase("Best")) {
				// TODO ... defaults defaults ...
				conf.addNaturalSelector(new BestChromosomesSelector(conf),
						executeNaturalBefore);
			} else if (naturalSel.equalsIgnoreCase("Threshold")) {
				// TODO let's say ... why 0.5d and not 0.7d ..... ? Well,
				// because there are no defaults ..
				conf.addNaturalSelector(new ThresholdSelector(conf, 0.5d),
						executeNaturalBefore);
			} else if (naturalSel.equalsIgnoreCase("Tournament")) {
				// TODO .... guess what ? Hm let's say 30 and 0.5 are the
				// optimal values. Just a guess
				conf.addNaturalSelector(new TournamentSelector(conf, 30, 0.5d),
						executeNaturalBefore);
			} else if (naturalSel.equalsIgnoreCase("Weighted")) {
				// TODO yeah, you guessed right. Although this one is ok
				conf.addNaturalSelector(new WeightedRouletteSelector(conf),
						executeNaturalBefore);
			}
			else {
				System.out.println("Setting default Best Chromosome Selector -- This should never happer!");
				conf.addNaturalSelector(new BestChromosomesSelector(conf),
						executeNaturalBefore);
			}
			
			// set the rest
			conf.setMinimumPopSizePercent(minPopSizePercent);
			conf.setSelectFromPrevGen(selectFromPrevGen);
			conf.setKeepPopulationSizeConstant(keepPopSizeConstant);
			conf.setPopulationSize(population);

			if (crossoverRate != 0)
				conf.addGeneticOperator(new CrossoverOperator(conf, crossoverRate));

			if (mutationRate != 0)
				conf.addGeneticOperator(new MutationOperator(conf, mutationRate));

		} catch (InvalidConfigurationException e) {
			throw new RuntimeException(
					"Building Configuration Error ...");
		}
		
		try {
			conf.setFitnessFunction(soloFitnessEvol);
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conf;
	}

	public String toString() {
		String str = "---------------------------------------------------\n"
				+ "Evolutionary Settings\n"
				+ "---------------------------------------------------\n\n"
				+ "Random Generator : "
				+ randomGen
				+ "\n"
				+ "Natural Selector : "
				+ naturalSel
				+ "\n"
				+ "Execute Natural Selector before GO : "
				+ executeNaturalBefore
				+ "\n"
				+ "Min Population Size Percent : "
				+ minPopSizePercent
				+ "\n"
				+ "Select From previous Generation "
				+ selectFromPrevGen
				+ "\n"
				+ "Keep Population Size Constant : "
				+ keepPopSizeConstant
				+ "\n"
				+ "Crossover Rate : "
				+ crossoverRate
				+ "\n"
				+ "Mutation Rate : "
				+ mutationRate
				+ "\n"
				+ "Population Size : "
				+ conf.getPopulationSize() + "\n";

		return str;
	}

}
