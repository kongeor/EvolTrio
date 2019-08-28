package gr.evoltrio.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.1.0
 *
 */
public class AboutComposite extends Composite {

	private EvolTrioUI evolTrioUI;
	
	private FormText text;

	public AboutComposite(EvolTrioUI evolTrioUI, Composite parent) {
		super(parent, SWT.NONE);
		this.evolTrioUI = evolTrioUI;

		setLayout(new GridLayout(1, false));

		setupControls();
		
		getParent().pack();
	}

	private void setupControls() {

		try {
			Image evolTrioIcon = new Image(this.getShell().getDisplay(),
					new FileInputStream(EvolTrioUI.ICON_PATH + "evoltrio.png"));
			Label imageLabel = new Label(this, SWT.NONE);
			imageLabel.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));
			imageLabel.setImage(evolTrioIcon);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Sets up the toolkit.
		FormToolkit toolkit = new FormToolkit(getShell().getDisplay());

		// Creates a form instance.
		final Form form = toolkit.createForm(this);
		form.setLayoutData(new GridData(GridData.FILL_BOTH));
		form.getBody().setLayout(new TableWrapLayout());

		text = toolkit.createFormText(form.getBody(), true);

		text.setText(
				"<form>"
						+ "<p> Version: " + evolTrioUI.getEvolution().getVersion() + "</p>"
						+ "<p> Licenced under the <a href=\"http://www.opensource.org/licenses/MIT\">MIT public license</a> </p>"
						+ "<p> Developed By Kostas Georgiadis {kongeor@it.teithe.gr} </p>"
						+ "<p> Project Homepage: <a href=\"www.it.teithe.gr/evomusic/\">www.it.teithe.gr/evomusic/</a> </p>"
						+ "<p> Source Repo: <a href=\"www.github.com/psykasso/EvolTrio\">www.github.com/psykasso/EvolTrio</a></p>"
						+ "<p> Using: "
						+ "<a href=\"www.eclipse.org\">SWT/JFACE</a>, "
						+ "<a href=\"www.jgap.org\">JGAP</a>, "
						+ "<a href=\"www.jfugue.org\">JFUGUE</a>"
						+ "</p></form>", true, true);
		
		text.addHyperlinkListener(new HyperlinkAdapter() {
	        public void linkActivated(HyperlinkEvent e) {
	          Program.launch((String) e.getHref());
	        }
	    });
		    
		    // Create a horizontal separator
		    Label separator = new Label(this, SWT.HORIZONTAL | SWT.SEPARATOR);
		    separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		    
		    final Button closeButton = new Button(this, SWT.PUSH);
			closeButton.setText("Close");
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_END);
			data.horizontalSpan = 2;
			closeButton.setLayoutData(data);

			closeButton.addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					getShell().dispose();
				}

			});

	}
}
