package gr.evoltrio.ui;

import gr.evoltrio.core.Evolution;
import gr.evoltrio.midi.SongBuilder;
import gr.evoltrio.midi.SongPlayerThread;
import gr.evoltrio.ui.prefs.PlaybackSettingsComposite;
import gr.evoltrio.ui.prefs.PreferencesMainComposite;
import gr.evoltrio.ui.prefs.PresetComposite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.jface.action.StatusLineContributionItem;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.swtchart.Chart;
import org.swtchart.IAxisTick;
import org.swtchart.IBarSeries;
import org.swtchart.ILineSeries;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.ISeries.SeriesType;

public class EvolTrioUI {

	private final String RESET_LABEL = "Reset";
	private final String RESET_TOOLTIP = "Reset Current Evolutionary Process";
	public static final String PRESET_LABEL = "Preset";
	public static final String PRESET_TOOLTIP = "Select a saved preset";
	public static final String PREFERENCES_LABEL = "Preferences";
	public static final String PREFERENCES_TOOLTIP = "Configure Settings";
	private final String EVOLVE_LABEL = "Evolve";
	private final String EVOLVE_TOOLTIP = "Start/Resume the Evolutionary Process";
	public static final String PLAY_LABEL = "Play";
	public static final String STOP_LABEL = "Stop";
	private final String PLAY_TOOLTIP = "Play The Best Evolved Chromosome";
	public static final String PAUSE_LABEL = "Pause";
	public static final String PAUSE_TOOLTIP = "Pause The Music";
	private final String STOP_TOOLTIP = "Stop The Music";
	private final String SAVE_LABEL = "Save";
	private final String SAVE_TOOLTIP = "Save The Best Evolved Chromosome (in midi format)";

	public final static String ICON_PATH = "icons"
			+ System.getProperty("file.separator");

	private Image evolTrioIcon, evolveIcon, resetIcon, preferencesIcon,
			saveIcon, infoIcon, presetIcon, playIcon, pauseIcon, stopIcon;

	private TabFolder tabFolder;

	public static final int[] FILTER_CHART_COLORS = { SWT.COLOR_BLUE,
			SWT.COLOR_GREEN, SWT.COLOR_RED, SWT.COLOR_YELLOW,
			SWT.COLOR_DARK_CYAN, SWT.COLOR_DARK_MAGENTA, SWT.COLOR_DARK_YELLOW,
			SWT.COLOR_BLACK };

	private Shell shell;
	private Display display;

	private Evolution evolution;
	private EvolTrioUI evolTrioUI;
	private Chart evolutionChart, chromosomeChart;

	private ToolItem playSongItem, pauseSongItem, stopSongItem;

	private Text infoText;

	private StatusLineContributionItem iterationStatusItem;
	private StatusLineContributionItem fitnessStatusItem;
	private StatusLineContributionItem chromosomeStatusItem;

	private EvolutionRunner evolutionRunner;

	private SongPlayerThread songPlayer;

	public EvolTrioUI() {
		Display.setAppName("EvolTrio");
		display = new Display();

		shell = new Shell(display);
		shell.setText("EvolTrio");

		initIcons();
		shell.setImage(evolTrioIcon);

		evolTrioUI = this;

		// parse default configuration file

		evolution = new Evolution();

		setupToolBar();
		setupMenus();
		setupMainView();

		evolutionRunner = new EvolutionRunner(shell, display, evolTrioUI,
				evolution);

		evolutionRunner.init();

		songPlayer = new SongPlayerThread(display, playSongItem, pauseSongItem,
				stopSongItem, evolution);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	public Evolution getEvolution() {
		return evolution;
	}

	private void initIcons() {
		try {
			evolTrioIcon = new Image(shell.getDisplay(), new FileInputStream(
					ICON_PATH + "icon.ico"));
			evolveIcon = createImage("evolve.png", 16, 16);
			resetIcon = createImage("reset.png", 16, 16);
			presetIcon = createImage("presets.png", 16, 16);
			preferencesIcon = createImage("preferences.png", 16, 16);
			playIcon = createImage("play.png", 16, 16);
			pauseIcon = createImage("pause.png", 16, 16);
			stopIcon = createImage("stop.png", 16, 16);
			saveIcon = createImage("save.png", 16, 16);
			infoIcon = createImage("info.png", 16, 16);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void setupMainView() {

		// main wrapper
		shell.setLayout(new GridLayout());

		tabFolder = new TabFolder(shell, SWT.NULL);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

		TabItem evolutionChartItem = new TabItem(tabFolder, SWT.NULL);
		evolutionChartItem.setText("Evolution Chart");

		TabItem chromosomeChartItem = new TabItem(tabFolder, SWT.NULL);
		chromosomeChartItem.setText("Chromosome Chart");

		TabItem logItem = new TabItem(tabFolder, SWT.NULL);
		logItem.setText("Information");

		evolutionChart = createEvolutionChart(tabFolder);
		evolutionChartItem.setControl(evolutionChart);

		chromosomeChart = createChromosomeChart(tabFolder);
		chromosomeChartItem.setControl(chromosomeChart);

		Composite infoComposite = new Composite(tabFolder, SWT.NONE);
		logItem.setControl(infoComposite);

		infoComposite.setLayout(new FillLayout());

		infoText = new Text(infoComposite, SWT.MULTI | SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL);
		infoText.setEditable(false);

		StatusLineManager slm = new StatusLineManager();

		iterationStatusItem = new StatusLineContributionItem("Iteration", 20);
		iterationStatusItem.setText("Iteration: ");
		slm.add(iterationStatusItem);

		fitnessStatusItem = new StatusLineContributionItem("Fitness", 20);
		fitnessStatusItem.setText("Fitness: ");
		slm.add(fitnessStatusItem);

		chromosomeStatusItem = new StatusLineContributionItem("Chromosome", 100);
		chromosomeStatusItem.setText("Best Chromosome: ");
		slm.add(chromosomeStatusItem);

		slm.createControl(shell);

	}

	private void setupToolBar() {
		final ToolBar toolBar = new ToolBar(shell, SWT.FLAT | SWT.WRAP
				| SWT.TRAIL);

		final ToolItem startEvolutionItem = createToolItem(toolBar,
				SWT.DROP_DOWN, EVOLVE_LABEL, evolveIcon, null, EVOLVE_TOOLTIP);

		final ToolItem resetEvolutionItem = createToolItem(toolBar, SWT.PUSH,
				RESET_LABEL, resetIcon, null, RESET_TOOLTIP);

		// add a separator
		new ToolItem(toolBar, SWT.SEPARATOR);

		final ToolItem presetEvolutionItem = createToolItem(toolBar, SWT.PUSH,
				PRESET_LABEL, presetIcon, null, PRESET_TOOLTIP);

		final ToolItem preferencesEvolutionItem = createToolItem(toolBar,
				SWT.PUSH, PREFERENCES_LABEL, preferencesIcon, null,
				PREFERENCES_TOOLTIP);

		// add a separator
		new ToolItem(toolBar, SWT.SEPARATOR);

		playSongItem = createToolItem(toolBar, SWT.DROP_DOWN, PLAY_LABEL,
				playIcon, null, PLAY_TOOLTIP);

		final Menu playbackMenu = new Menu(shell, SWT.POP_UP);

		final MenuItem playbackItem = new MenuItem(playbackMenu, SWT.PUSH);
		playbackItem.setText("Settings");

		pauseSongItem = createToolItem(toolBar, SWT.PUSH, PAUSE_LABEL,
				pauseIcon, null, PAUSE_TOOLTIP);
		pauseSongItem.setEnabled(false);

		stopSongItem = createToolItem(toolBar, SWT.PUSH, STOP_LABEL, stopIcon,
				null, STOP_TOOLTIP);
		stopSongItem.setEnabled(false);

		new ToolItem(toolBar, SWT.SEPARATOR);

		final ToolItem saveSongItem = createToolItem(toolBar, SWT.PUSH,
				SAVE_LABEL, saveIcon, null, SAVE_TOOLTIP);

		final Menu evolutionMenu = new Menu(shell, SWT.POP_UP);

		MenuItem evolveOnceItem = new MenuItem(evolutionMenu, SWT.PUSH);
		evolveOnceItem.setText("Evolve Once\tCtrl+1");
		evolveOnceItem.setAccelerator(SWT.CTRL + '1');

		new MenuItem(evolutionMenu, SWT.SEPARATOR);

		MenuItem evolveNitem = new MenuItem(evolutionMenu, SWT.PUSH);
		evolveNitem.setText("Evolve N\tCtrl+N");
		evolveNitem.setAccelerator(SWT.CTRL + 'N');

		presetEvolutionItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				Shell newShell = new Shell(shell);
				new PresetComposite(evolTrioUI, newShell);
				openComposite(newShell, "Presets");
			}
		});

		preferencesEvolutionItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				Shell newShell = new Shell(shell);
				new PreferencesMainComposite(evolTrioUI, newShell);
				openComposite(newShell, "Preferences");
			}
		});

		startEvolutionItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Rectangle bounds = startEvolutionItem.getBounds();
					Point point = toolBar.toDisplay(bounds.x, bounds.y
							+ bounds.height);
					evolutionMenu.setLocation(point);
					evolutionMenu.setVisible(true);
				} else {
					evolutionRunner.evolve(evolution.getEvolConf()
							.getIterations());
				}

			}

		});

		evolveOnceItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				evolutionRunner.evolve(1);
			}
		});

		evolveNitem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				openInputDialogAndEvolve();
			}
		});

		resetEvolutionItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				// TODO unify those
				resetGui();
				evolutionRunner.init();
			}
		});

		playbackItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				Shell newShell = new Shell(shell);
				new PlaybackSettingsComposite(newShell);
				openComposite(newShell, "Playback Settings");
			}
		});

		playSongItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Rectangle bounds = playSongItem.getBounds();
					Point point = toolBar.toDisplay(bounds.x, bounds.y
							+ bounds.height);
					playbackMenu.setLocation(point);
					playbackMenu.setVisible(true);
				} else {
					if (!songPlayer.isAlive())
						songPlayer = new SongPlayerThread(display,
								playSongItem, pauseSongItem, stopSongItem,
								evolution);
					playSongItem.setEnabled(false); // temporarily disable
													// button
					songPlayer.startPlayer();
				}
			}
		});

		pauseSongItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				songPlayer.pausePlayer();
			}
		});

		stopSongItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				songPlayer.stopPlayer();
			}
		});

		saveSongItem.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				saveSong();
			}
		});

		toolBar.pack();
	}

	private void setupMenus() {
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);

		MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
		fileItem.setText("File");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(fileMenu);

		MenuItem saveItem = new MenuItem(fileMenu, SWT.PUSH);
		saveItem.setImage(saveIcon);
		saveItem.setText("Save\tCtrl+S");
		saveItem.setAccelerator(SWT.CTRL + 'S');

		saveItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				saveSong();
			}

		});

		new MenuItem(fileMenu, SWT.SEPARATOR);

		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("Exit");

		exitItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				display.dispose();
			}

		});

		MenuItem editItem = new MenuItem(menuBar, SWT.CASCADE);
		editItem.setText("Edit");

		Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
		editItem.setMenu(editMenu);

		MenuItem presetItem = new MenuItem(editMenu, SWT.PUSH);
		presetItem.setText("Presets\tCtrl+P");
		presetItem.setAccelerator(SWT.CTRL + 'P');
		presetItem.setImage(presetIcon);

		presetItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell newShell = new Shell(shell);
				new PresetComposite(evolTrioUI, newShell);
				openComposite(newShell, "Presets");
			}

		});

		MenuItem preferencesItem = new MenuItem(editMenu, SWT.PUSH);
		preferencesItem.setText("Preferences\tAlt+P");
		preferencesItem.setAccelerator(SWT.ALT + 'P');
		preferencesItem.setImage(preferencesIcon);

		preferencesItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell newShell = new Shell(shell);
				new PreferencesMainComposite(evolTrioUI, newShell);
				openComposite(newShell, "Preferences");
			}

		});

		MenuItem evolutionItem = new MenuItem(menuBar, SWT.CASCADE);
		evolutionItem.setText("Evolution");

		Menu evolutionMenu = new Menu(shell, SWT.DROP_DOWN);
		evolutionItem.setMenu(evolutionMenu);

		MenuItem evolveItem = new MenuItem(evolutionMenu, SWT.PUSH);
		evolveItem.setText("Start/Resume\tCtrl+G");
		evolveItem.setAccelerator(SWT.CTRL + 'G');
		evolveItem.setImage(evolveIcon);

		evolveItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				evolutionRunner.evolve(evolution.getEvolConf().getIterations());
			}

		});

		MenuItem evolveOnce = new MenuItem(evolutionMenu, SWT.PUSH);
		evolveOnce.setText("Evolve Once\tCtrl+1");
		evolveOnce.setAccelerator(SWT.CTRL + '1');

		evolveOnce.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				evolutionRunner.evolve(1);
			}

		});

		MenuItem evolveNItem = new MenuItem(evolutionMenu, SWT.PUSH);
		evolveNItem.setText("Evolve N\tCtrl + N");
		evolveNItem.setAccelerator(SWT.CTRL + 'N');

		evolveNItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openInputDialogAndEvolve();
			}

		});

		new MenuItem(evolutionMenu, SWT.SEPARATOR);

		MenuItem resetEvolution = new MenuItem(evolutionMenu, SWT.PUSH);
		resetEvolution.setText("Reset\tCtrl+R");
		resetEvolution.setAccelerator(SWT.CTRL + 'R');
		resetEvolution.setImage(resetIcon);

		resetEvolution.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// TODO unify those
				resetGui();
				evolutionRunner.init();
			}

		});

		MenuItem helpItem = new MenuItem(menuBar, SWT.CASCADE);
		helpItem.setText("Help");

		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpItem.setMenu(helpMenu);

		MenuItem aboutItem = new MenuItem(helpMenu, SWT.PUSH);
		aboutItem.setText("About");
		aboutItem.setImage(infoIcon);

		aboutItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				// new AboutDialog(evolTrioUI, shell, SWT.NONE).open();
				//openAboutComposite();
				Shell newShell = new Shell(shell);
				new AboutComposite(evolTrioUI, newShell);
				openComposite(newShell, "About EvolTrio");
			}

		});

	}

	public StatusLineContributionItem getIterationStatusItem() {
		return iterationStatusItem;
	}

	public StatusLineContributionItem getFitnessStatusItem() {
		return fitnessStatusItem;
	}

	public StatusLineContributionItem getChromosomeStatusItem() {
		return chromosomeStatusItem;
	}

	public Chart getEvolutionChart() {
		return evolutionChart;
	}

	public Chart getChromosomeChart() {
		return chromosomeChart;
	}

	public Text getInfoText() {
		return infoText;
	}

	public EvolutionRunner getEvolutionRunner() {
		return evolutionRunner;
	}

	private ToolItem createToolItem(ToolBar parent, int type, String text,
			Image image, Image hotImage, String toolTipText) {
		ToolItem item = new ToolItem(parent, type);
		item.setText(text);
		item.setImage(image);
		item.setHotImage(hotImage);
		item.setToolTipText(toolTipText);
		return item;
	}

	private Image createImage(String imageName, int width, int height) {
		ImageData imageData = new ImageData(ICON_PATH + imageName);
		ImageData imageData2 = imageData.scaledTo(width, height);
		return new Image(display, imageData2);
	}

	public void resetGui() {
		// resetCharts

		chromosomeChart.getSeriesSet().getSeries("Music Chromosome")
				.setYSeries(new double[] {});
		chromosomeChart.getAxisSet().adjustRange();
		chromosomeChart.redraw();

		evolutionChart.getSeriesSet().getSeries("fitness")
				.setYSeries(new double[] {});
		evolutionChart.getAxisSet().adjustRange();
		evolutionChart.redraw();

		fitnessStatusItem.setText("Fitness: ");
		iterationStatusItem.setText("Iteration: ");
		chromosomeStatusItem.setText("Best Chromosome: ");

		infoText.setText("");

	}

	public void saveSong() {
		String fileName = null;

		boolean done = false;
		FileDialog fileDialog = new FileDialog(shell, SWT.SAVE);

		while (!done) {
			fileDialog.setFileName("song.mid");
			fileDialog.setFilterExtensions(new String[] { "*.mid", "*.midi" });
			fileName = fileDialog.open();

			if (fileName == null)
				done = true;
			else {
				File file = new File(fileName);

				if (file.exists()) {
					MessageBox messageBox = new MessageBox(shell,
							SWT.ICON_WARNING | SWT.YES | SWT.NO);
					messageBox.setMessage("Warning");
					messageBox.setMessage(fileName + " exists. Replace?");

					int response = messageBox.open();
					done = response == SWT.YES;

				} else {
					done = true;
				}
				
				if (done)
					SongBuilder.buildAndSave(
							evolution.getFittestChromosome(), fileName);
			}
		}
	}

	public Chart createEvolutionChart(Composite parent) {
		Chart evolutionChart = new Chart(parent, SWT.NONE);

		// set titles
		evolutionChart.getTitle().setText("");
		evolutionChart.getAxisSet().getXAxis(0).getTitle().setText("Iteration");

		IAxisTick xTick = evolutionChart.getAxisSet().getXAxis(0).getTick();
		xTick.setForeground(new Color(Display.getDefault(), 0, 0, 0));
		xTick = evolutionChart.getAxisSet().getYAxis(0).getTick();
		xTick.setForeground(new Color(Display.getDefault(), 0, 0, 0));

		Font font = new Font(Display.getDefault(), "Tahoma", 12, SWT.NORMAL);
		evolutionChart.getAxisSet().getXAxis(0).getTitle().setFont(font);
		evolutionChart.getAxisSet().getXAxis(0).getTitle()
				.setForeground(new Color(Display.getDefault(), 0, 0, 0));

		evolutionChart.getAxisSet().getYAxis(0).getTitle().setFont(font);
		evolutionChart.getAxisSet().getYAxis(0).getTitle()
				.setForeground(new Color(Display.getDefault(), 0, 0, 0));

		evolutionChart.getAxisSet().getYAxis(0).getTitle()
				.setText("Fitness Evaluation");

		// create line series
		ILineSeries lineSeries = (ILineSeries) evolutionChart.getSeriesSet()
				.createSeries(SeriesType.LINE, "fitness");
		lineSeries.setSymbolType(PlotSymbolType.NONE);

		// MAGENTA
		Color color = new Color(Display.getDefault(), 151, 47, 151);
		lineSeries.setLineColor(color);
		lineSeries.setLineWidth(2);
		lineSeries.setAntialias(SWT.ON);

		return evolutionChart;
	}

	public Chart createChromosomeChart(Composite parent) {
		Chart chromosomeChart = new Chart(parent, SWT.NONE);

		IAxisTick xTick = chromosomeChart.getAxisSet().getXAxis(0).getTick();
		xTick.setForeground(new Color(Display.getDefault(), 0, 0, 0));
		xTick = chromosomeChart.getAxisSet().getYAxis(0).getTick();
		xTick.setForeground(new Color(Display.getDefault(), 0, 0, 0));

		Font font = new Font(Display.getDefault(), "Tahoma", 12, SWT.NORMAL);
		chromosomeChart.getAxisSet().getXAxis(0).getTitle().setFont(font);
		chromosomeChart.getAxisSet().getXAxis(0).getTitle()
				.setForeground(new Color(Display.getDefault(), 0, 0, 0));

		chromosomeChart.getAxisSet().getYAxis(0).getTitle().setFont(font);
		chromosomeChart.getAxisSet().getYAxis(0).getTitle()
				.setForeground(new Color(Display.getDefault(), 0, 0, 0));

		chromosomeChart.getTitle().setText("");
		chromosomeChart.getAxisSet().getXAxis(0).enableCategory(true);

		chromosomeChart
				.getAxisSet()
				.getXAxis(0)
				.setCategorySeries(
						evolution.getEvolConf().getSoloFitnessEvol()
								.getActiveFiltersNameArray());
		chromosomeChart.getAxisSet().getXAxis(0).getTitle().setText("Filter");
		chromosomeChart.getAxisSet().getYAxis(0).getTitle()
				.setText("Filter Evaluation");
		chromosomeChart.getAxisSet().getXAxis(0).getTick()
				.setTickLabelAngle(45);

		// create bar series
		IBarSeries barSeries = (IBarSeries) chromosomeChart.getSeriesSet()
				.createSeries(SeriesType.BAR, "Music Chromosome");

		barSeries.setBarColor(Display.getDefault().getSystemColor(
				SWT.COLOR_DARK_MAGENTA));

		// adjust the axis range
		chromosomeChart.getAxisSet().adjustRange();

		return chromosomeChart;
	}

	protected void openInputDialogAndEvolve() {
		IInputValidator validator = new IInputValidator() {

			@Override
			public String isValid(String text) {
				try {
					int value = Integer.parseInt(text);
					if (value < 0)
						return "Input cannot be negative";
				} catch (NumberFormatException e) {
					return "Invalid input";
				}

				return null;
			}
		};

		InputDialog inputDialog = new InputDialog(shell, "Iteration Input",
				"Input a positive integer", "10", validator);
		inputDialog.open();

		if (inputDialog.getReturnCode() == Window.OK)
			evolutionRunner.evolve(Integer.parseInt(inputDialog.getValue()));
	}
	
	private void openComposite(Shell newShell, String text) {
		shell.setEnabled(false);
		newShell.setLayout(new FillLayout());
		//newShell.setMinimumSize(100, 200);
		newShell.pack();
		newShell.setText(text);

		// Center
		Rectangle childRec = newShell.getBounds();
		Rectangle parentRec = shell.getBounds();

		Point point = shell.toDisplay((parentRec.width - childRec.width) / 2,
				(parentRec.height - childRec.height) / 3);
		newShell.setLocation(point);

		newShell.open();

		while (!newShell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		shell.setEnabled(true);
	}

	public static void main(String[] args) {
		new EvolTrioUI();
//		System.out.println("yolo");
	}
}
