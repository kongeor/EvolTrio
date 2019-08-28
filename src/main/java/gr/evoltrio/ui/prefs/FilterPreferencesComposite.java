package gr.evoltrio.ui.prefs;

import gr.evoltrio.fitness.FiltersFactory;
import gr.evoltrio.fitness.FiltersFactory.Filter;
import gr.evoltrio.fitness.SoloFitnessEvol;
import gr.evoltrio.ui.EvolTrioUI;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.1.0
 *
 */
public class FilterPreferencesComposite extends Composite {

	private final static int FIRST_COLUMN_WIDTH = 120; // TODO create an

	private EvolTrioUI evolTrioUI;

	private Map<Button, Slider> controls;
	private Map<Slider, Label> labels;

	public FilterPreferencesComposite(EvolTrioUI evolTrioUI, Composite parent) {
		super(parent, SWT.NONE);
		this.evolTrioUI = evolTrioUI;

		controls = new HashMap<Button, Slider>();
		labels = new HashMap<Slider, Label>();

		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		setLayout(layout);

		setupControls();
	}

	private void setupControls() {

		Composite composite = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		final Button selectAllButton = new Button(composite, SWT.CHECK);
		selectAllButton.setText("Select/Deselect All");
		selectAllButton.setSelection(true);
		// button.setToolTipText("Select/Deselect All Filters");

		selectAllButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean state = selectAllButton.getSelection();
				for (Button b : controls.keySet()) {
					b.setSelection(state);
				}
			}

		});

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = FIRST_COLUMN_WIDTH;
		selectAllButton.setLayoutData(data);

		Button resetWeights = new Button(composite, SWT.PUSH);
		resetWeights.setText("Reset Weights");
		resetWeights.setToolTipText("Reset All Weights to 100%");

		resetWeights.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				for (Slider s : controls.values())
					s.setSelection(100);
				for (Label l : labels.values()) {
					l.setText("100%");
					l.pack();
				}
			}
		});

		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout(1, false);
		composite.setLayout(layout);

		// Create a horizontal separator
		Label separator = new Label(composite, SWT.HORIZONTAL | SWT.SEPARATOR);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = 300; // TODO correct this
		separator.setLayoutData(data);

		for (Filter filter : FiltersFactory.Filter.values()) {

			composite = new Composite(this, SWT.NONE);
			layout = new GridLayout();
			layout.numColumns = 3;
			composite.setLayout(layout);

			Button button = new Button(composite, SWT.CHECK);
			button.setText(filter.name());
			button.setSelection(true);

			data = new GridData(SWT.FILL, SWT.FILL, true, true);
			data.widthHint = FIRST_COLUMN_WIDTH;
			button.setLayoutData(data);

			final Slider slider = new Slider(composite, SWT.NONE);
			slider.setMinimum(0);
			slider.setMaximum(110);
			slider.setSelection(100);

			final Label label = new Label(composite, SWT.NONE);
			label.setText("" + slider.getSelection() + "%");

			slider.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					label.setText("" + slider.getSelection() + "%");
					label.pack();
				}
			});

			controls.put(button, slider);
			labels.put(slider, label);

		}
	} // setupControls()

	public void initControlValues() {
		SoloFitnessEvol fitness = evolTrioUI.getEvolution().getEvolConf()
				.getSoloFitnessEvol();
		
		for (Entry<Button, Slider> control : controls.entrySet()) {
			Button button = control.getKey();
			button.setSelection(fitness.isEnabled(button.getText()));
			
			double weight = fitness.getWeight(button.getText());
			
			control.getValue().setSelection((int) (weight*100));
			labels.get(control.getValue()).setText("" + (int)(weight*100) + "%");
			labels.get(control.getValue()).pack();
		}

	}

	// TODO create an interface for that
	public void updateConfigurationValues() {

		SoloFitnessEvol fitness = evolTrioUI.getEvolution().getEvolConf()
				.getSoloFitnessEvol();
		
		for (Entry<Button, Slider> control : controls.entrySet()) {
			fitness.setEnabled(control.getKey().getText(), control.getKey().getSelection());
			fitness.setWeight(control.getKey().getText(), control.getValue().getSelection()/100.0);
		}

	}

}
