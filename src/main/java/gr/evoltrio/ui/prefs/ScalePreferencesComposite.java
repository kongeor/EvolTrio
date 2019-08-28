package gr.evoltrio.ui.prefs;

import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.midi.Scale;
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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class ScalePreferencesComposite extends Composite {
	
	private final static int SCALE_COLUMN_WIDTH = 120;
	
	private final static String CUSTOM_SCALE_LABEL = "CUSTOM";

    private Combo scaleCombo;	
    
	private Button[] selectedNotes = new Button[12];

	public ScalePreferencesComposite(EvolTrioUI evolTrioUI, Composite parent) {
		super(parent, SWT.NONE);
		RowLayout
		layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		setLayout(layout);

		setupControls();
	}

	private void setupControls() {
		
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label begginingDurationLabel = new Label(composite, SWT.NONE);
		begginingDurationLabel.setText("Scale");
		
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    //data.widthHint = FIRST_COLUMN_WIDTH;
	    begginingDurationLabel.setLayoutData(data);
		
	    scaleCombo = new Combo(composite, SWT.DROP_DOWN  | SWT.READ_ONLY);
	    scaleCombo.setItems(Scale.stringValues());
	    scaleCombo.add("CUSTOM");
	    scaleCombo.select(0);
	    
	    scaleCombo.addSelectionListener(new SelectionAdapter() {

			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!scaleCombo.getText().equals(CUSTOM_SCALE_LABEL))
					setupScaleIntervals(Scale.fromString(scaleCombo.getText()).getIntervals());
			}
			
		});
	    
	    data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = SCALE_COLUMN_WIDTH;
	    scaleCombo.setLayoutData(data);
		
		composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		
		Group scaleTable = new Group(composite, SWT.NONE);
		scaleTable.setText("Scale Intervals");
		layout = new GridLayout();
		layout.numColumns = 12;
		
		scaleTable.setLayout(layout);

		for (int i = 0; i < 12; i++) {
			final Label label = new Label(scaleTable, SWT.NONE);
			label.setText(MusicConfiguration.NOTES[i]);
			//label.setLayoutData(gridData);
		}

		
		for (int i = 0; i < 12; i++) {
			selectedNotes[i] = new Button(scaleTable, SWT.CHECK);

			selectedNotes[i].addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event arg0) {
					//select CUSTOM
					scaleCombo.select(scaleCombo.getItemCount()-1); 
				}
			});
		}

	}

	public void initControlValues() {
		scaleCombo.select(getScaleNameIndex(MusicConfiguration.getInstance().getScaleName()));
		setupScaleIntervals(MusicConfiguration.getInstance().getScaleIntervals());
	}

	public void updateConfigurationValues() {
		
		int count = 0;
		
		for (int i = 0; i < 12; i++) {
			if(selectedNotes[i].getSelection()) 
				count++;
		}
		
		int[] scaleIntervals = new int[count];
		
		int index = 0;
		
		for (int i = 0; i < 12; i++) {
			if(selectedNotes[i].getSelection()) 
				scaleIntervals[index++] = i;
		}
		
		MusicConfiguration.getInstance().setScaleName(scaleCombo.getText());
		MusicConfiguration.getInstance().setScaleIntervals(scaleIntervals);
		
	}
	
	private void setupScaleIntervals(int[] intervals) {
		for (int i = 0; i < 12; i++) {
			selectedNotes[i].setSelection(false);
		}
		
		for (int i = 0; i < intervals.length ; i++) {
			selectedNotes[intervals[i]].setSelection(true);
		}
	}
	
	private int getScaleNameIndex(String name) {
		int index = -1;
		
		for (int i = 0; i < Scale.values().length; i++) {
			if(Scale.values()[i].name().equals(name))
				index = i;
		}
		
		if(index == -1)
			index = scaleCombo.getItemCount() - 1; // Custom
			
		return index;
	}

}
