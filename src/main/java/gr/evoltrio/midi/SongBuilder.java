package gr.evoltrio.midi;

import java.io.File;
import java.io.IOException;

import org.jfugue.Pattern;
import org.jfugue.Player;
import org.jgap.IChromosome;

/**
 * This class needs major changes.
 */
public class SongBuilder {

    private IChromosome[] phrases;
	
    private Pattern song;
    private Player player;

    private File file;

    public SongBuilder(File file,IChromosome[] phrases2) {
        this.file = file;
        this.phrases = phrases2;
        
        song = new Pattern();
        player = new Player();

    }
    
    public static void buildAndSave(IChromosome chromo, String file) {
    	Pattern voice0 = new Pattern("V0 I[" + MusicConfiguration.getInstance().getSoloOrgan() +"] T[" +  MusicConfiguration.getInstance().getTempo() + "] ");
    	String jfuguePattern = MusicFactory.chromosomeToJFuguePattern(chromo);
    	
    	voice0.add(jfuguePattern);
    	
    	Player player = new Player();
    	Pattern song = new Pattern();
    	song.add(voice0);
    	
    	try {
			player.saveMidi(song, new File(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void buildSong(){
        Pattern voice0 = new Pattern("V0 I[" + MusicConfiguration.getInstance().getSoloOrgan() +"] T[" +  MusicConfiguration.getInstance().getTempo() + "] ");

        for(int i=0; i<phrases.length; i++){
        	String jfugueRoundedPat = MusicFactory.chromosomeToJFuguePattern(phrases[i]);
        	System.out.println(jfugueRoundedPat);
            voice0.add(jfugueRoundedPat);
        }

        song.add(voice0);

    }

    public void playTheSong(){
        // Play the song!
        
        player.play(song);
    }

    public void saveTheSong(){

        saveTheSong(file);
    }

    public void saveTheSong(File fileName){
        try {
			player.saveMidi(song, fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }



}
