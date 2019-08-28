package gr.evoltrio.ui.prefs;

import gr.evoltrio.conf.ConfigurationManager;
import gr.evoltrio.ui.EvolTrioUI;

import java.io.File;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.1.0
 *
 */
public class PreferencesMainComposite extends Composite {
	
	public static final String CONF_PATH = "conf" + System.getProperty("file.separator");

	final private Composite parent;
	
	private EvolutionaryPreferencesComposite evolComp;
	private MusicPreferecencesComposite musicComp;
	private FilterPreferencesComposite filterComp;
	private ScalePreferencesComposite scaleComp;
	private DurationComposite durationComp;
	
	private EvolTrioUI evolTrioUI;

	public PreferencesMainComposite(EvolTrioUI evolTrioUI, Composite parent) {
		super(parent, SWT.NONE);
		this.parent = parent;
		
		this.evolTrioUI = evolTrioUI;
		setupControls();
	}
	
	private void setupControls() {
		TabFolder tf = new TabFolder(this, SWT.NONE);
		setLayout(new FormLayout());
		
		FormData data = new FormData();
	    data.top = new FormAttachment(0, 5);
	    data.left = new FormAttachment(0, 5);
	    data.bottom = new FormAttachment(85, -5);
	    data.right = new FormAttachment(100, -5);
	    tf.setLayoutData(data);
	    
	    Button restorePresetButton = new Button(this, SWT.PUSH);
	    restorePresetButton.setText("Load Preset");
	    
	    int buttonHeight = restorePresetButton.getBounds().height;
	    int buttonWidth = restorePresetButton.getBounds().width;
	    
	    data = new FormData();
	    data.top = new FormAttachment(tf, 5);
	    data.left = new FormAttachment(60, 5);
	    data.right = new FormAttachment(80);
	    restorePresetButton.setLayoutData(data);
	    
	    restorePresetButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openPresetComposite();
				initControlValues();
			}

		});
	    
	    Button savePresetButton = new Button(this, SWT.PUSH);
	    savePresetButton.setSize(buttonWidth, buttonHeight);
	    savePresetButton.setText("Save Preset");
	    
	    savePresetButton.addSelectionListener(new SelectionAdapter() {
	    	
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				IInputValidator validator = new IInputValidator() {

					@Override
					public String isValid(String filename) {
						File file = new File(CONF_PATH + filename);
						
						if(file.exists())
							return "Configuration exists, enter a different name";

						return null;
					}
				};

				InputDialog inputDialog = new InputDialog(parent.getShell(),
						"Save Preset", "Enter a name",
						"myconfname", validator);
				inputDialog.open();

				if (inputDialog.getReturnCode() == Window.OK) {
					updateAllConfigurationValues();
					ConfigurationManager.save(CONF_PATH + inputDialog.getValue(), evolTrioUI.getEvolution().getEvolConf());
				}
				
			}
		});
	    
	    data = new FormData();
	    data.top = new FormAttachment(tf, 5);
	    data.left = new FormAttachment(80, 5);
	    data.right = new FormAttachment(100, -5);
	    savePresetButton.setLayoutData(data);
	    
	    Button okButton = new Button(this, SWT.PUSH);
	    okButton.setText("Ok");
	    
	    data = new FormData();
	    data.top = new FormAttachment(savePresetButton, 5);
	    data.left = new FormAttachment(60, 5);
	    data.right = new FormAttachment(80);
//	    data.bottom = new FormAttachment(100, -5);
//	    data.right = new FormAttachment(100, -5);
	    okButton.setLayoutData(data);
	    
	    okButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				updateAllConfigurationValues();
				
				//TODO check if these two methods should be combined
				evolTrioUI.resetGui();
				evolTrioUI.getEvolutionRunner().init();
				
				parent.dispose();
			}

		});
	    
	    Button cancelButton = new Button(this, SWT.PUSH);
	    cancelButton.setSize(buttonWidth, buttonHeight);
	    cancelButton.setText("Cancel");
	    
	    data = new FormData();
	    data.top = new FormAttachment(restorePresetButton, 5);
	    data.left = new FormAttachment(80, 5);
	    data.right = new FormAttachment(100, -5);
//	    data.bottom = new FormAttachment(100, -5);
//	    data.right = new FormAttachment(100, -5);
	    cancelButton.setLayoutData(data);
	    
	    cancelButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.dispose();
			}
	    	
		});


	    TabItem evolutionaryItem = new TabItem(tf, SWT.NONE);
		evolutionaryItem.setText("Evoltionary");
		evolComp = new EvolutionaryPreferencesComposite(evolTrioUI, tf);
		evolutionaryItem.setControl(evolComp);
		
		TabItem musicItem = new TabItem(tf, SWT.NONE);
		musicItem.setText("Music");
		musicComp = new MusicPreferecencesComposite(tf);
		musicItem.setControl(musicComp);
		
		TabItem filterItem = new TabItem(tf, SWT.NONE);
		filterItem.setText("Filter");
		filterComp = new FilterPreferencesComposite(evolTrioUI, tf);
		filterItem.setControl(filterComp);
		
		TabItem scaleItem = new TabItem(tf, SWT.NONE);
		scaleItem.setText("Scale");
		scaleComp = new ScalePreferencesComposite(evolTrioUI, tf);
		scaleItem.setControl(scaleComp);
		
		TabItem durationItem = new TabItem(tf, SWT.NONE);
		durationItem.setText("Duration");
		durationComp = new DurationComposite(evolTrioUI, tf);
		durationItem.setControl(durationComp);
		
		initControlValues();
	}
	
	private void updateAllConfigurationValues() {
		evolComp.updateConfigurationValues();
		musicComp.updateConfigurationValues();
		filterComp.updateConfigurationValues();
		scaleComp.updateConfigurationValues();
		durationComp.updateConfigurationValues();
	}
	
	private void initControlValues() {
		evolComp.initControlValues();
		musicComp.initControlValues();
		filterComp.initControlValues();
		scaleComp.initControlValues();
		durationComp.initControlValues();
		
	}
	
	protected void openPresetComposite() {
		Shell prefShell = new Shell(parent.getShell());
		parent.getShell().setEnabled(false);
		new PresetComposite(
				evolTrioUI, prefShell);
		prefShell.setLayout(new FillLayout());
		prefShell.setMinimumSize(100, 200);
		prefShell.pack();
		prefShell.setText("Edit Presets");

		// Center
		Rectangle childRec = prefShell.getBounds();
		Rectangle parentRec = parent.getShell().getBounds();

		Point point = parent.getShell().toDisplay(
				(parentRec.width - childRec.width) / 2,
				(parentRec.height - childRec.height) / 3);
		prefShell.setLocation(point);

		prefShell.open();

		while (!prefShell.isDisposed()) {
			if (!getShell().getDisplay().readAndDispatch())
				getShell().getDisplay().sleep();
		}
		parent.getShell().setEnabled(true);
	}

}
