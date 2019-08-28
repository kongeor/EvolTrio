package gr.evoltrio.ui;

import gr.evoltrio.core.Evolution;
import gr.evoltrio.fitness.IFitnessFilter;
import gr.evoltrio.fitness.SoloFitnessEvol;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.midi.MusicFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jgap.Configuration;
import org.jgap.IChromosome;

public class EvolutionRunner {
	
	private Shell shell;
	private Display display;
	
	private EvolTrioUI evolTrioUI;
	private Evolution evolution;

	private List<Double> fitnessList;
	private int totalIterationCount;
	
	private IChromosome fittestChromosome;
	
	private Runnable runnable;
	
	private StringBuilder logString;
	
	public EvolutionRunner(Shell shell, Display display, EvolTrioUI evolTrioUI,
			Evolution evolution) {
		super();
		this.shell = shell;
		this.display = display;
		this.evolTrioUI = evolTrioUI;
		this.evolution = evolution;
	}

	public void init() {
		Configuration.reset();
		evolution.setup();
		fitnessList = new ArrayList<Double>();
		totalIterationCount = 0;
		logString = new StringBuilder();
		
		evolTrioUI.getInfoText().append(""
				+ evolution.getEvolConf());
		evolTrioUI.getInfoText().append(""
				+ MusicConfiguration.getInstance());
		evolTrioUI.getInfoText().append(""
				+ evolution.getEvolConf().getSoloFitnessEvol());
	}
	
	public void evolve(final int n) {
		ProgressMonitorDialog progressMonitor = new ProgressMonitorDialog(shell);

		try {
			IRunnableWithProgress runnable = new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					

					monitor.beginTask("Evolutionary Process is Running", IProgressMonitor.UNKNOWN);

					int count = 0;

					while (count++ < n && !monitor.isCanceled()) {
						
						monitor.subTask("Evolving generation " + count + " of " + n);
						fittestChromosome = evolution.evolveOnce();
						fitnessList.add(fittestChromosome.getFitnessValue());
						totalIterationCount++;
						logString.append("Iteration :" + totalIterationCount  + " -- Fitness: " + (int)fittestChromosome.getFitnessValue() + " -- Best Chromosome :" + MusicFactory.chromosomeToJFuguePattern(fittestChromosome) + "\n");
						
					}
					
					display.asyncExec(new Runnable() {
						
						@Override
						public void run() {
							evolTrioUI.getInfoText().append(logString.toString());
						}
					});
					
					fittestChromosome = evolution.getFittestChromosome();

					setupGuiData();
					
				}
			};

			progressMonitor.run(true, true, runnable);

		} catch (Exception exc) {
			exc.printStackTrace();
		}

	}
	
	public void setupGuiData() {
		
		if (runnable == null) // Lazy initialization
			runnable = new Runnable() {

				@Override
				public void run() {
					evolTrioUI.getEvolutionChart().getSeriesSet().getSeries("fitness")
							.setYSeries(toPrimitiveDouble(fitnessList));
					// System.out.println(fitnessList);

					evolTrioUI.getEvolutionChart().getAxisSet().adjustRange();
					evolTrioUI.getEvolutionChart().redraw();
					
					SoloFitnessEvol fitnessEvol = (SoloFitnessEvol) evolution.getEvolConf().getSoloFitnessEvol();
					List<Double> filterValues = new ArrayList<Double>();

					for (IFitnessFilter filter : fitnessEvol.getFilters()) {
						if(filter.isEnabled())
							filterValues.add(filter.evaluate(fittestChromosome));
						else
							filterValues.add(new Double(0));
						
					}
					
					evolTrioUI.getChromosomeChart().getSeriesSet().getSeries("Music Chromosome").setYSeries(toPrimitiveDouble(filterValues));

					evolTrioUI.getChromosomeChart().getAxisSet().adjustRange();
					evolTrioUI.getChromosomeChart().redraw();

					evolTrioUI.getChromosomeStatusItem().setText("Best Chromosome: " +
							MusicFactory.chromosomeToJFuguePattern(fittestChromosome));
					evolTrioUI.getFitnessStatusItem().setText("Fitness: " + (int)evolution.getFittestChromosome().getFitnessValue());
					evolTrioUI.getIterationStatusItem().setText(
							"Iteration: " + totalIterationCount);

				}

			};
		display.asyncExec(runnable);
	}
	
	// TODO check the lib
	private double[] toPrimitiveDouble(List<Double> list) {
		double[] temp = new double[list.size()];

		for (int i = 0; i < list.size(); i++)
			temp[i] = list.get(i);

		return temp;
	}

}
