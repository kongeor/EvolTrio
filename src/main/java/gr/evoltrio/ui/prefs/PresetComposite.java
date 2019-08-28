package gr.evoltrio.ui.prefs;

import gr.evoltrio.conf.ConfigurationManager;
import gr.evoltrio.ui.EvolTrioUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.1.0
 *
 */
public class PresetComposite extends Composite {
	
	private final Composite parent;
	
	private EvolTrioUI evolTrioUI;
	
	public PresetComposite(EvolTrioUI evolTrioUI, Composite parent) {
		super(parent, SWT.NONE);
		this.parent = parent;
		
		this.evolTrioUI = evolTrioUI;
		setupControls();
		
		getParent().pack();
	}

	private void setupControls() {
		final List presetList = new List(this, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
		setLayout(new FormLayout());
		
		FormData data = new FormData();
	    data.top = new FormAttachment(0, 5);
	    data.left = new FormAttachment(0, 5);
	    data.bottom = new FormAttachment(88, -5);
	    data.right = new FormAttachment(100, -5);
	    presetList.setLayoutData(data);
	    
	    presetList.setItems(ConfigurationManager.availableConfsString());
	    presetList.select(0);
	    
	    Button loadPresetButton = new Button(this, SWT.PUSH);
	    loadPresetButton.setText("Load");
	    
	    loadPresetButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO unify these
				evolTrioUI.resetGui();
				ConfigurationManager.parse(presetList.getSelection()[0], evolTrioUI.getEvolution().getEvolConf());
				evolTrioUI.getEvolutionRunner().init();
			}
	    	
		});
	    
	    data = new FormData();
	    data.top = new FormAttachment(presetList, 5);
//	    data.bottom = new FormAttachment(100, -5);
	    data.left = new FormAttachment(40, 5);
	    data.right = new FormAttachment(60);
	    loadPresetButton.setLayoutData(data);
	    
	    final Button deletePresetButton = new Button(this, SWT.PUSH);
	    deletePresetButton.setText("Delete");
	    		
	    //minor check for default conf
	    if(presetList.getSelection()[0].equals("default"))
	    	deletePresetButton.setEnabled(false);
	    
	    deletePresetButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				//TODO create helper methods
				ConfigurationManager.deleteConfiguration(presetList.getSelection()[0]);
				presetList.setItems(ConfigurationManager.availableConfsString());
			}
	    	
		});
	    
	    data = new FormData();
	    data.top = new FormAttachment(presetList, 5);
	    data.left = new FormAttachment(60, 5);
	    data.right = new FormAttachment(80);
	    deletePresetButton.setLayoutData(data);
	    
	    Button closeButton = new Button(this, SWT.PUSH);
	    closeButton.setText("Close");
	    
	    data = new FormData();
	    data.top = new FormAttachment(presetList, 5);
	    data.left = new FormAttachment(80, 5);
	    data.right = new FormAttachment(100, -5);
	    closeButton.setLayoutData(data);
	    
	    closeButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				parent.dispose();
			}
	    	
		}); 
	    
	    
	    // prevent the deletion of the default configuration
	    presetList.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if(presetList.getSelection()[0].equals("default"))
					deletePresetButton.setEnabled(false);
				else
					deletePresetButton.setEnabled(true);
			}
	    	
		});
	}

}
