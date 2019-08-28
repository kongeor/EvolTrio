package gr.evoltrio.ui.prefs;

import gr.evoltrio.core.EvolConfiguration;
import gr.evoltrio.ui.EvolTrioUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

/**
 * 
 * @author Konsntantinos Georgiadis
 * @since 0.1.0
 *
 */
public class EvolutionaryPreferencesComposite extends Composite {
	
	private final static int FIRST_COLUMN_WIDTH = 120;
	private final static int COMBO_COLUMN_WIDTH = 80;
	
	private Slider fromPrevGenSlider;
	private Label fromPrevGenValueLabel;
	
	private Slider crossoverSlider;
	private Label crossoverValueLabel;
	
	private Slider mutationSlider;
	private Label mutationValueLabel;
	
	private Slider populationSlider;
	private Label populationValueLabel;
	
	private Slider iterationsSlider;
	private Label iterationsValueLabel;
	
	private Combo randomGenCombo;
	private Combo naturalSelCombo;
	
	private Button execBeforeButton;
	
	private Slider minPopSlider;
	private Label minPopValueLabel;
	
	private Button keepPopSizeConstantButton;
	
	private EvolTrioUI evolTrioUI;
	
	public EvolutionaryPreferencesComposite(EvolTrioUI evolTrioUI, Composite parent) {
		super(parent, SWT.NONE);
		
		this.evolTrioUI = evolTrioUI;
		
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		setLayout(layout);
		
		setupControls();
	}
	
	private void setupControls() {
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label fromPrevGenLabel = new Label(composite, SWT.NONE);
		fromPrevGenLabel.setText("Select From Prev Gen");
		
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    fromPrevGenLabel.setLayoutData(data);
		
	    fromPrevGenSlider = new Slider(composite, SWT.NONE);
		fromPrevGenSlider.setMinimum(0);
		fromPrevGenSlider.setSelection(80);
		fromPrevGenSlider.setMaximum(110);

		fromPrevGenValueLabel = new Label(composite, SWT.NONE);
		updateFromPrevGenValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label crossoverLabel = new Label(composite, SWT.NONE);
		crossoverLabel.setText("Crossover");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    crossoverLabel.setLayoutData(data);
		
	    crossoverSlider = new Slider(composite, SWT.NONE);
		crossoverSlider.setMinimum(0);
		crossoverSlider.setSelection(10);
		crossoverSlider.setMaximum(110);
		
		crossoverValueLabel = new Label(composite, SWT.NONE);
		updateCrossoverValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label mutationLabel = new Label(composite, SWT.NONE);
		mutationLabel.setText("Mutation");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    mutationLabel.setLayoutData(data);
		
	    mutationSlider = new Slider(composite, SWT.NONE);
		mutationSlider.setMinimum(0);
		mutationSlider.setSelection(10);
		mutationSlider.setMaximum(110);
		
		mutationValueLabel = new Label(composite, SWT.NONE);
		updateMutationValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label populationLabel = new Label(composite, SWT.NONE);
		populationLabel.setText("Population");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    populationLabel.setLayoutData(data);
		
	    populationSlider = new Slider(composite, SWT.NONE);
		populationSlider.setMinimum(4);
		populationSlider.setSelection(30);
		populationSlider.setMaximum(1000);
		
		populationValueLabel = new Label(composite, SWT.NONE);
		updatePopulationValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label iterationsLabel = new Label(composite, SWT.NONE);
		iterationsLabel.setText("Iterations");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    iterationsLabel.setLayoutData(data);
		
	    iterationsSlider = new Slider(composite, SWT.NONE);
	    iterationsSlider.setMinimum(1);
	    iterationsSlider.setSelection(110);
	    iterationsSlider.setMaximum(10000);
		
		iterationsValueLabel = new Label(composite, SWT.NONE);
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = 40; // otherwise the 2 last digits are not visible
	    iterationsValueLabel.setLayoutData(data);
	    
		updateIterationsValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label randomGenLabel = new Label(composite, SWT.NONE);
		randomGenLabel.setText("Random Generator");
		
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    randomGenLabel.setLayoutData(data);
		
	    randomGenCombo = new Combo(composite, SWT.DROP_DOWN  | SWT.READ_ONLY);
		randomGenCombo.setItems(EvolConfiguration.RANDOMGENERATORS);
		randomGenCombo.select(0);
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = COMBO_COLUMN_WIDTH;
	    randomGenCombo.setLayoutData(data);
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label naturalSelLabel = new Label(composite, SWT.NONE);
		naturalSelLabel.setText("Natural Selector");
		
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    naturalSelLabel.setLayoutData(data);
		
	    naturalSelCombo = new Combo(composite, SWT.DROP_DOWN  | SWT.READ_ONLY);
		naturalSelCombo.setItems(EvolConfiguration.NATURALSELECTORS);
		naturalSelCombo.select(0);
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = COMBO_COLUMN_WIDTH;
	    naturalSelCombo.setLayoutData(data);
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		
		execBeforeButton = new Button(composite, SWT.CHECK);
		execBeforeButton.setText("Execute GO before Natural Selector");
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label minPopLabel = new Label(composite, SWT.NONE);
		minPopLabel.setText("Minimum Population");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    minPopLabel.setLayoutData(data);
		
	    minPopSlider = new Slider(composite, SWT.NONE);
		minPopSlider.setMinimum(0);
		minPopSlider.setSelection(10);
		minPopSlider.setMaximum(110);
		
		minPopValueLabel = new Label(composite, SWT.NONE);
		updateMinPopValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		
		keepPopSizeConstantButton = new Button(composite, SWT.CHECK);
		keepPopSizeConstantButton.setText("Keep Population Size Constant");
		
		/*
		 * SETUP LISTENERS
		 */
		fromPrevGenSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				fromPrevGenValueLabel.setText("" + fromPrevGenSlider.getSelection() + "%");		
				fromPrevGenValueLabel.pack();
			}
			
		});
		
		crossoverSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				crossoverValueLabel.setText("" + crossoverSlider.getSelection() + "%");		
				crossoverValueLabel.pack();
			}
			
		});
		
		mutationSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				mutationValueLabel.setText("" + mutationSlider.getSelection() + "%");		
				mutationValueLabel.pack();
			}
			
		});
		
		populationSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				populationValueLabel.setText("" + populationSlider.getSelection());		
				populationValueLabel.pack();
			}
			
		});
		
		iterationsSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				iterationsValueLabel.setText("" + iterationsSlider.getSelection());		
				iterationsValueLabel.pack();
			}
			
		});
		
		minPopSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				minPopValueLabel.setText("" + minPopSlider.getSelection() + "%");
				minPopValueLabel.pack();
			}
			
		});
		
	} // end of setupControl()

	public void initControlValues() {
		fromPrevGenSlider.setSelection((int) (evolTrioUI.getEvolution().getEvolConf().getSelectFromPrevGen()*100.));
		updateFromPrevGenValueLabel();
		
		crossoverSlider.setSelection((int) (evolTrioUI.getEvolution().getEvolConf().getCrossoverRate()*100.));
		updateCrossoverValueLabel();
		
		mutationSlider.setSelection(evolTrioUI.getEvolution().getEvolConf().getMutationRate());
		updateMutationValueLabel();
		
		populationSlider.setSelection(evolTrioUI.getEvolution().getEvolConf().getConf().getPopulationSize());
		updatePopulationValueLabel();
		
		iterationsSlider.setSelection(evolTrioUI.getEvolution().getEvolConf().getIterations());
		updateIterationsValueLabel();
		
		randomGenCombo.select(evolTrioUI.getEvolution().getEvolConf().getRandomGen(evolTrioUI.getEvolution().getEvolConf().getRandomGen()));
		
		naturalSelCombo.select(evolTrioUI.getEvolution().getEvolConf().getNaturalSel(evolTrioUI.getEvolution().getEvolConf().getNaturalSel()));
		
		execBeforeButton.setSelection(evolTrioUI.getEvolution().getEvolConf().isExecuteNaturalBefore());
		
		minPopSlider.setSelection(evolTrioUI.getEvolution().getEvolConf().getMinPopSizePercent());
		updateMinPopValueLabel();
		
		keepPopSizeConstantButton.setSelection(evolTrioUI.getEvolution().getEvolConf().isKeepPopSizeConstant());
	}
	
	public void updateConfigurationValues() {
		evolTrioUI.getEvolution().getEvolConf().setSelectFromPrevGen(((double)fromPrevGenSlider.getSelection())/100d);
		evolTrioUI.getEvolution().getEvolConf().setCrossoverRate(((double)crossoverSlider.getSelection())/100d);
		evolTrioUI.getEvolution().getEvolConf().setMutationRate(mutationSlider.getSelection());
		evolTrioUI.getEvolution().getEvolConf().setPopulationSize(populationSlider.getSelection());
		evolTrioUI.getEvolution().getEvolConf().setIterations(iterationsSlider.getSelection());
		evolTrioUI.getEvolution().getEvolConf().setRandomGen(randomGenCombo.getText());
		evolTrioUI.getEvolution().getEvolConf().setNaturalSel(naturalSelCombo.getText());
		evolTrioUI.getEvolution().getEvolConf().setExecuteNaturalBefore(execBeforeButton.getSelection());
		
		evolTrioUI.getEvolution().getEvolConf().setMinPopSizePercent(minPopSlider.getSelection());
		
		evolTrioUI.getEvolution().getEvolConf().setKeepPopSizeConstant(keepPopSizeConstantButton.getSelection());
		
	}


	private void updateFromPrevGenValueLabel() {
		fromPrevGenValueLabel.setText("" + fromPrevGenSlider.getSelection() + "%");
	}
	

	private void updateCrossoverValueLabel() {
		crossoverValueLabel.setText("" + crossoverSlider.getSelection() + "%");
	}
	
	private void updateMutationValueLabel() {
		mutationValueLabel.setText("" + mutationSlider.getSelection() + "%");
	}
	
	private void updateMinPopValueLabel() {
		minPopValueLabel.setText("" + minPopSlider.getSelection() + "%");
	}
	
	private void updatePopulationValueLabel() {
		populationValueLabel.setText("" + populationSlider.getSelection());
	}
	
	private void updateIterationsValueLabel() {
		iterationsValueLabel.setText("" + iterationsSlider.getSelection());
	}

}
