package gr.evoltrio.midi;

import gr.evoltrio.core.Evolution;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolItem;
import org.jfugue.Pattern;
import org.jfugue.Player;

/**
 * A player for the best chromosome.
 * The thread also can disable and enable UI components,
 * such as play, pause, and stop buttons.
 */
public class SongPlayerThread extends Thread {

	private Display display;
	private ToolItem playItem;
	private ToolItem pauseItem;
	private ToolItem stopItem;
	private Evolution evolution;

	private Player player;

	public SongPlayerThread(Display display, ToolItem playItem, ToolItem pauseItem,
			ToolItem stopItem, Evolution evolution) {
		super();
		this.display = display;
		this.playItem = playItem;
		this.pauseItem = pauseItem;
		this.stopItem = stopItem;
		this.evolution = evolution;
		
		player = new Player();
	}

	@Override
	public void run() {
		buttonsInPlayingMode();

		// disable the button
		Pattern pattern = new Pattern("V0 I["
				+ MusicConfiguration.getInstance().getSoloOrgan() + "] T["
				+ MusicConfiguration.getInstance().getTempo() + "] ");
		String jFuguePattern = MusicFactory.chromosomeToJFuguePattern(evolution.getFittestChromosome());
		pattern.add(jFuguePattern);
		player.play(pattern);

		buttonsInStoppedMode();

	}
	
	public void startPlayer() {
		buttonsInPlayingMode();
		if (player.isPaused())
			player.resume();
		else {
			this.start();
		}
	}
	
	public void pausePlayer() {
		player.pause();
		
		buttonsInPausedMode();
	}

	public void stopPlayer() {

		try {
			player.stop();
		} catch (NullPointerException e) {

		} catch (IllegalStateException e) {

		}

		
	}
	
	private void buttonsInPlayingMode() {
		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				playItem.setEnabled(false);
				pauseItem.setEnabled(true);
				stopItem.setEnabled(true);
			}
		});
	}
	
	private void buttonsInStoppedMode() {
		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				playItem.setEnabled(true);
				pauseItem.setEnabled(false);
				stopItem.setEnabled(false);
			}
		});
	}
	
	private void buttonsInPausedMode() {
		display.asyncExec(new Runnable() {

			@Override
			public void run() {
				playItem.setEnabled(true);
				pauseItem.setEnabled(false);
				stopItem.setEnabled(true);
			}
		});
	}
}
