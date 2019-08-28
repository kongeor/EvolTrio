package gr.evoltrio.ui.prefs;

import gr.evoltrio.midi.MusicConfiguration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.1.0
 *
 */
public class PlaybackSettingsComposite extends Composite {
	
	private final static int FIRST_COLUMN_WIDTH = 120;
	private final static int INSTRUMENT_COLUMN_WIDTH = 110;
	
	private Combo instrumentCategoryCombo;
	private Combo instrumentCombo;
	private Combo tempoCombo;
	
	private Composite parent;
	
	public PlaybackSettingsComposite(Composite parent) {
		super(parent, SWT.NONE);
		this.parent = parent;
		
//		RowLayout layout = new RowLayout();
//		layout.type = SWT.VERTICAL;
//		setLayout(layout);
		
		setLayout(new GridLayout(1, true));
		
		setupControls();
		
		initControlValues();
		
		parent.pack();
	}

	private void setupControls() {
		Composite composite = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label instumentCategoryLabel = new Label(composite, SWT.NONE);
		instumentCategoryLabel.setText("Instrument Category");
		
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    instumentCategoryLabel.setLayoutData(data);
		
	    instrumentCategoryCombo = new Combo(composite, SWT.DROP_DOWN  | SWT.READ_ONLY);
	    instrumentCategoryCombo.setItems((String[]) MusicConfiguration.INSTRUMENT_CATEGORIES.toArray());
	    instrumentCategoryCombo.select(0);
	    
	    data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = INSTRUMENT_COLUMN_WIDTH;
	    instrumentCategoryCombo.setLayoutData(data);
	    
	    composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label instrumentLabel = new Label(composite, SWT.NONE);
		instrumentLabel.setText("Instrument");
		
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    instrumentLabel.setLayoutData(data);
		
	    instrumentCombo = new Combo(composite, SWT.DROP_DOWN  | SWT.READ_ONLY);
	    instrumentCombo.setItems(MusicConfiguration.getInstance().getCategory(instrumentCategoryCombo.getSelectionIndex()));
	    instrumentCombo.select(0);
	    
	    data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = INSTRUMENT_COLUMN_WIDTH;
	    instrumentCombo.setLayoutData(data);
	    
	    composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
	    
	    Label tempoLabel = new Label(composite, SWT.NONE);
	    tempoLabel.setText("Tempo");
	    
	    data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    tempoLabel.setLayoutData(data);
	    
	    tempoCombo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
	    tempoCombo.setItems(MusicConfiguration.TEMPOS);
	    tempoCombo.select(10); //Allegro
	    
	    data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = INSTRUMENT_COLUMN_WIDTH;
	    tempoCombo.setLayoutData(data);
	    
	    
	    instrumentCategoryCombo.addSelectionListener(new SelectionAdapter() {

			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateInstruments();
			}
			
		});
	    
	    composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FormLayout());
	    
	    final Button okButton = new Button(composite, SWT.PUSH);
	    okButton.setText("OK");
	    
	    FormData formData = new FormData();
	    formData.left = new FormAttachment(64);
	    formData.right = new FormAttachment(82);
	    okButton.setLayoutData(formData);
	    
	    okButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				updateConfigurationValues();
				parent.dispose();
			}
	    	
		});
	    
	    final Button cancelButton = new Button(composite, SWT.PUSH);
	    cancelButton.setText("Cancel");
	    
	    formData = new FormData();
	    formData.left = new FormAttachment(okButton);
	    formData.right = new FormAttachment(100);
	    cancelButton.setLayoutData(formData);
	    
	    cancelButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				parent.dispose();
			}
	    	
		});

	}
	
	public void initControlValues() {
		instrumentCategoryCombo.select(MusicConfiguration.getInstance().getSoloOrgan() / 8);
		updateInstruments();
		
		instrumentCombo.select(MusicConfiguration.getInstance().getSoloOrgan() % 8);
		tempoCombo.select(MusicConfiguration.getInstance().getSelectedTempoIndex());
	}
	
	public void updateConfigurationValues() {
		MusicConfiguration.getInstance().setSoloOrgan((instrumentCategoryCombo.getSelectionIndex()*8) + instrumentCombo.getSelectionIndex());
		MusicConfiguration.getInstance().setTempo(tempoCombo.getText());
	}
	
	private void updateInstruments() {
		instrumentCombo.setItems(MusicConfiguration.getInstance().getCategory(instrumentCategoryCombo.getSelectionIndex()));
		instrumentCombo.select(0);
	}

}
