package gr.evoltrio.fitness;

import org.jgap.IChromosome;

/**
 * The base interface for every Fitness Filter.
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public interface IFitnessFilter {

	public double getWeigth();
	public void setWeigth(double weigth);
	
	public boolean isEnabled();
	public void setEnabled(boolean enabled);

	/**
	 * Evaluate a music chromosome.
	 * 
	 * @param chromo The chromosome to evaluate
	 * @return The fitness value for the implemented fitness filter.
	 */
	public double evaluate(IChromosome chromo);

}
