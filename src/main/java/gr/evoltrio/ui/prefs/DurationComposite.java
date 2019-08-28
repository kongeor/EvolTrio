package gr.evoltrio.ui.prefs;

import gr.evoltrio.midi.Duration;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.ui.EvolTrioUI;

import java.util.ArrayList;
import java.util.List;

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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Slider;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.1.0
 *
 */
public class DurationComposite extends Composite {
	
	private final static int FIRST_COLUMN_WIDTH = 120;
	public static final int DURATION_COMBO_COLUMN_WIDTH = 150;
	
	
	private Combo begginingDurationCombo;
	
	private Slider maxDurationJumpSlider;
	private Label maxDurationJumpValueLabel;
	
	private List<Button> buttons;
	
	
	private Composite parent;
	
	public DurationComposite(EvolTrioUI evolTrioUI, Composite parent) {
		super(parent, SWT.NONE);
		this.parent = parent;
		
		RowLayout layout = new RowLayout();
		layout.type = SWT.VERTICAL;
		setLayout(layout);
		

		setupControls();
	}

	private void setupControls() {
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		
		Group durationGroup = new Group(composite, SWT.NONE);
		durationGroup.setText("Duration Selection");
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		durationGroup.setLayout(layout);
		
		
		buttons = new ArrayList<Button>();
		
		Button button;
		
		
		for(Duration d: Duration.values()) {
			button = new Button(durationGroup, SWT.CHECK);
			button.setText(d.getCharacter());
			
			//GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		    //data.widthHint = FIRST_COLUMN_WIDTH;
		    //button.setLayoutData(data);
			
			button.addListener(SWT.Selection, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					List<Duration> enabledButtons = new ArrayList<Duration>();
					
					for(Button b: buttons)
						if(b.getSelection())
							enabledButtons.add(Duration.fromString(b.getText()));
					
					if(enabledButtons.size() < 1) {
						openMessageDialog();
						((Button)event.widget).setSelection(true);
					}
					else {
						String[] durationList = new String[enabledButtons.size()];
						for (int i = 0; i < durationList.length; i++) {
							durationList[i] = enabledButtons.get(i).name();
						}
							
						begginingDurationCombo.setItems(durationList);
						begginingDurationCombo.select(0);
						
						maxDurationJumpSlider.setMaximum(enabledButtons.size());
						maxDurationJumpSlider.setSelection(enabledButtons.size());
						updateMaxDurationJumpValueLabel();
					}
						
					
				}

				private void openMessageDialog() {
					int style = SWT.ICON_WARNING | SWT.OK;
					MessageBox dialog = new MessageBox(parent.getShell(), style);
					dialog.setMessage("At least one duration must be selected");
					dialog.setText("Warning");
					dialog.open();
				}
			});
			
			buttons.add(button); // add to buttons List
		}
		
		composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);
		
		Label begginingDurationLabel = new Label(composite, SWT.NONE);
		begginingDurationLabel.setText("Beggining Duration");
		
		GridData data = new GridData(SWT.FILL, SWT.CENTER, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    begginingDurationLabel.setLayoutData(data);
		
	    begginingDurationCombo = new Combo(composite, SWT.DROP_DOWN  | SWT.READ_ONLY);
	    begginingDurationCombo.setItems(MusicConfiguration.getInstance().getActiveDurationStringArray());
	    begginingDurationCombo.select(0); // TODO this one is not working
	    
	    data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = DURATION_COMBO_COLUMN_WIDTH;
	    begginingDurationCombo.setLayoutData(data);
	    
	    composite = new Composite(this, SWT.NONE);
		layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		Label maxDurationJumpLabel = new Label(composite, SWT.NONE);
		maxDurationJumpLabel.setText("Max Duration Jump");
		
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
	    data.widthHint = FIRST_COLUMN_WIDTH;
	    maxDurationJumpLabel.setLayoutData(data);
		
	    maxDurationJumpSlider = new Slider(composite, SWT.NONE);
	    maxDurationJumpSlider.setMinimum(0);
	    maxDurationJumpSlider.setSelection(2);
	    maxDurationJumpSlider.setMaximum(MusicConfiguration.getInstance().getActiveDurationList().size());
	    maxDurationJumpSlider.setThumb(1);
	    
	    maxDurationJumpSlider.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateMaxDurationJumpValueLabel();
				maxDurationJumpValueLabel.pack();
			}
			
		});
		
		maxDurationJumpValueLabel = new Label(composite, SWT.NONE);
		updateMaxDurationJumpValueLabel();
		
	}
	
	public void initControlValues() {
		for(Duration d: MusicConfiguration.getInstance().getActiveDurationList())
			for(Button b: buttons)
				if(d.getCharacter().equals(b.getText()))
					b.setSelection(true);
		
		begginingDurationCombo.select(MusicConfiguration.getInstance().getBeginningDuration());
		maxDurationJumpSlider.setSelection(MusicConfiguration.getInstance().getMaxDurationJump());
		updateMaxDurationJumpValueLabel();
		
	}
	
	public void updateConfigurationValues() {
		String enabled = "";
		
		for (Button b: buttons)
			if(b.getSelection())
				enabled += b.getText() + ",";
		
		MusicConfiguration.getInstance().setActiveDurationList(enabled);
		MusicConfiguration.getInstance().setBeginningDuration(begginingDurationCombo.getSelectionIndex());
		MusicConfiguration.getInstance().setMaxDurationJump(maxDurationJumpSlider.getSelection());
		
	}
	
	private void updateMaxDurationJumpValueLabel() {
		maxDurationJumpValueLabel.setText("" + maxDurationJumpSlider.getSelection());
	}

}
