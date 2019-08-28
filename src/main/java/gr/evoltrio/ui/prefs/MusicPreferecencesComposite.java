package gr.evoltrio.ui.prefs;

import gr.evoltrio.midi.MusicConfiguration;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
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
public class MusicPreferecencesComposite extends Composite {
	
	//TODO share those statics, interface maybe ?
	private final static int FIRST_COLUMN_WIDTH = 120;
	private final static int COMBO_COLUMN_WIDTH = 80;
	private final static int INSTRUMENT_COLUMN_WIDTH = 140;

	private Combo rootNoteCombo;
	
	private Slider octaveSlider;
	private Label octaveValueLabel;
	
	private Slider phraseNotesSlider;
	private Label phraseNotesValueLabel;
	
	private Slider maxIntervalJumpSlider;
	private Label maxIntervalJumpValueLabel;

	
	private Combo instrumentCategoryCombo;
	private Combo instrumentCombo;
	private Combo tempoCombo;
	
	
	public MusicPreferecencesComposite(Composite parent) {
		super(parent, SWT.NONE);

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
		
		Label rootNoteLabel = new Label(composite, SWT.NONE);
		rootNoteLabel.setText("Root Note");
		
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    rootNoteLabel.setLayoutData(data);
		
	    rootNoteCombo = new Combo(composite, SWT.DROP_DOWN  | SWT.READ_ONLY);
	    rootNoteCombo.setItems(MusicConfiguration.NOTES);
	    rootNoteCombo.select(0);
	    
	    data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = COMBO_COLUMN_WIDTH;
	    rootNoteCombo.setLayoutData(data);
	    
	    composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label octaveLabel = new Label(composite, SWT.NONE);
		octaveLabel.setText("Octave");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    octaveLabel.setLayoutData(data);
		
	    octaveSlider = new Slider(composite, SWT.NONE);
	    octaveSlider.setMinimum(2);
	    octaveSlider.setSelection(5);
	    octaveSlider.setMaximum(8);
	    octaveSlider.setThumb(1);
		
		octaveValueLabel = new Label(composite, SWT.NONE);
		updateOctaveValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label phraseNotesLabel = new Label(composite, SWT.NONE);
		phraseNotesLabel.setText("Phrase Notes");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    phraseNotesLabel.setLayoutData(data);
		
	    phraseNotesSlider = new Slider(composite, SWT.NONE);
	    phraseNotesSlider.setMinimum(4);
	    phraseNotesSlider.setSelection(8);
	    phraseNotesSlider.setMaximum(129);
	    phraseNotesSlider.setThumb(1);
		
	    phraseNotesValueLabel = new Label(composite, SWT.NONE);
		updatePhraseNotesValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label intervalJumpLabel = new Label(composite, SWT.NONE);
		intervalJumpLabel.setText("Max Interval Jump");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    intervalJumpLabel.setLayoutData(data);
		
	    maxIntervalJumpSlider = new Slider(composite, SWT.NONE);
	    maxIntervalJumpSlider.setMinimum(1);
	    maxIntervalJumpSlider.setSelection(5);
	    maxIntervalJumpSlider.setMaximum(13);
	    maxIntervalJumpSlider.setThumb(1);
		
	    maxIntervalJumpValueLabel = new Label(composite, SWT.NONE);
		updateMaxIntervalJumpValueLabel();
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label instumentCategoryLabel = new Label(composite, SWT.NONE);
		instumentCategoryLabel.setText("Instrument Category");
		
		data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    instumentCategoryLabel.setLayoutData(data);
		
	    instrumentCategoryCombo = new Combo(composite, SWT.DROP_DOWN  | SWT.READ_ONLY);
	    instrumentCategoryCombo.setItems(MusicConfiguration.INSTRUMENT_CATEGORIES.toArray(new String[]{}));
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
		
		
		/*
		 * SETUP LISTENERS
		 */
		octaveSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateOctaveValueLabel();
				octaveValueLabel.pack();
			}
			
		});
		
		phraseNotesSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				updatePhraseNotesValueLabel();
				phraseNotesValueLabel.pack();
			}
			
		});
		
		maxIntervalJumpSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateMaxIntervalJumpValueLabel();
				maxIntervalJumpValueLabel.pack();
			}
			
		});
		
		instrumentCategoryCombo.addSelectionListener(new SelectionAdapter() {

			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateInstruments();
			}
			
		});
		
	}
	
	public void initControlValues() {
		
		rootNoteCombo.select(MusicConfiguration.getInstance().getRootNoteIndex());
		
		octaveSlider.setSelection(MusicConfiguration.getInstance().getOctave());
		updateOctaveValueLabel();
		
		phraseNotesSlider.setSelection(MusicConfiguration.getInstance().getPhraseNotes());
		updatePhraseNotesValueLabel();
		
		maxIntervalJumpSlider.setSelection(MusicConfiguration.getInstance().getMaxIntervalJump());
		updateMaxIntervalJumpValueLabel();
		
		//TODO change 8 to constant
		instrumentCategoryCombo.select(MusicConfiguration.getInstance().getSoloOrgan() / 8);
		updateInstruments();
		
		instrumentCombo.select(MusicConfiguration.getInstance().getSoloOrgan() % 8);
		
		tempoCombo.select(MusicConfiguration.getInstance().getSelectedTempoIndex());
	}
	
	public void updateConfigurationValues() {
		MusicConfiguration.getInstance().setRootNote(rootNoteCombo.getText());
		MusicConfiguration.getInstance().setOctave(octaveSlider.getSelection());
		MusicConfiguration.getInstance().setPhraseNotes(phraseNotesSlider.getSelection());
		MusicConfiguration.getInstance().setMaxIntervalJump(maxIntervalJumpSlider.getSelection());
		MusicConfiguration.getInstance().setSoloOrgan((instrumentCategoryCombo.getSelectionIndex()*8) + instrumentCombo.getSelectionIndex());
		MusicConfiguration.getInstance().setTempo(tempoCombo.getText());
		
	}

	private void updateOctaveValueLabel() {
		octaveValueLabel.setText("" + octaveSlider.getSelection());
	}

	private void updatePhraseNotesValueLabel() {
		phraseNotesValueLabel.setText("" + phraseNotesSlider.getSelection());
	}
	
	private void updateMaxIntervalJumpValueLabel() {
		maxIntervalJumpValueLabel.setText("" + maxIntervalJumpSlider.getSelection());
	}
	
	private void updateInstruments() {
		instrumentCombo.setItems(MusicConfiguration.getInstance().getCategory(instrumentCategoryCombo.getSelectionIndex()));
		instrumentCombo.select(0);
	}
}
