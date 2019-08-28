package gr.evoltrio.fitness;

import org.jgap.IChromosome;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.1.0
 */
public abstract class BaseFilter implements IFitnessFilter {

	private double weigth;
	private boolean enabled;
	
	public BaseFilter() {
		this(1.0);
	}
	
	public BaseFilter(double weigth) {
		this.weigth = weigth;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public double getWeigth() {
		return weigth;
	}

	public void setWeigth(double weigth) {
		this.weigth = weigth;
	}
	
	@Override
	public abstract double evaluate(IChromosome chromo);

}
