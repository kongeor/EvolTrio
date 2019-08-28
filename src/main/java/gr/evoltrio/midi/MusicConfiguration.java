package gr.evoltrio.midi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class MusicConfiguration {

	private static final MusicConfiguration INSTANCE = new MusicConfiguration();

	public static final int SCALE_NOTES_COUNT = 12;
	
	public static final String[] NOTES = { "C", "C#", "D", "D#", "E", "F",
			"F#", "G", "G#", "A", "A#", "B" };

	public static final String[] TEMPOS = { "Grave", "Largo", "Larghetto",
			"Lento", "Adagio", "Adagietto", "Andante", "Andantino", "Moderato",
			"Allegretto", "Allegro", "Vivace", "Presto", "Pretissimo" };

	public static final String[] STATIC_DURATION_MAP = { "half - 1/2",
			"quarter - 1/4", "eight - 1/8", "sixteenth - 1/16" };

	public static final Map<String, Integer> CHORDS;
	public static final Map<String, Integer> INSTRUMENTS;

	static {
		Map<String, Integer> chordsMap = new HashMap<String, Integer>();
		chordsMap.put("I", 0);
		chordsMap.put("II", 1);
		chordsMap.put("III", 2);
		chordsMap.put("IV", 3);
		chordsMap.put("V", 4);
		chordsMap.put("VI", 5);
		chordsMap.put("VII", 6);
		chordsMap.put("VIII", 7);
		chordsMap.put("IX", 8);
		chordsMap.put("X", 9);
		CHORDS = Collections.unmodifiableMap(chordsMap);

		Map<String, Integer> instrumentsMap = new HashMap<String, Integer>();
		instrumentsMap.put("ELECTRIC_PIANO", 4);
		instrumentsMap.put("MUSIC_BOX", 10);
		instrumentsMap.put("ROCK_ORGAN", 18);
		instrumentsMap.put("CHURCH_ORGAN", 19);
		instrumentsMap.put("ELECTRIC_JAZZ_GUITAR", 26);
		instrumentsMap.put("OVERDRIVEN_GUITAR", 29);
		instrumentsMap.put("DISTORTION_GUITAR", 30);
		instrumentsMap.put("GUITAR_HARMONICS", 31);
		instrumentsMap.put("DISTORTION_GUITAR", 34);
		instrumentsMap.put("ORCHESTRAL_STRINGS", 46);
		instrumentsMap.put("STRING_ENSEMBLE_1", 48);
		instrumentsMap.put("STRING_ENSEMBLE_2", 49);
		instrumentsMap.put("SYNTH_STRINGS_1", 50);
		instrumentsMap.put("SYNTH_STRINGS_2", 51);
		instrumentsMap.put("CHOIR_AAHS", 52);
		instrumentsMap.put("VOICE_OOHS", 53);
		instrumentsMap.put("SYNTH_VOICE", 54);
		instrumentsMap.put("ORCHESTRA_HIT", 55);
		instrumentsMap.put("TROMBONE", 57);
		instrumentsMap.put("SYNTHBRASS_1", 62);
		instrumentsMap.put("OBOE", 68);
		INSTRUMENTS = Collections.unmodifiableMap(instrumentsMap);
	}

	public static final List<String> INSTRUMENT_CATEGORIES = Arrays
			.asList(new String[] { "Piano", "Chromatic Percussion", "Organ",
					"Guitar", "Bass", "Strings", "Ensemble", "Brass", "Reed",
					"Pipe", "Synth Lead", "Synth Pad", "Synth Effects",
					"Ethnic", "Percussive", "Sound Effects" });

	public static final List<String> INSTRUMENT_NAMES = Arrays
			.asList(new String[] { "ACOUSTIC GRAND PIANO",
					"BRIGHT ACOUSTIC PIANO", "ELECTRIC GRAND PIANO",
					"HONKY-TONK PIANO", "ELECTRIC PIANO 1", "ELECTRIC PIANO 2",
					"HARPSICHORD", "CLAVI", "CELESTA", "GLOCKENSPIEL",
					"MUSIC BOX", "VIBRAPHONE", "MARIMBA", "XYLOPHONE",
					"TUBULAR BELLS", "DULCIMER", "DRAWBAR ORGAN",
					"PERCUSSIVE ORGAN", "ROCK ORGAN", "CHURCH ORGAN",
					"REED ORGAN", "ACCORDION", "HARMONICA", "TANGO ACCORDION",
					"NYLON STRING GUITAR", "STEEL ACOUSTIC GUITAR",
					"JAZZ ELECTRIC GUITAR", "CLEAN ELECTRIC GUITAR",
					"MUTED ELECTRIC GUITAR", "OVERDRIVEN GUITAR",
					"DISTORTION GUITAR", "GUITAR HARMONICS", "ACOUSTIC BASS",
					"FINGERED ELECTRIC BASS", "PICKED ELECTRIC BASS",
					"FRETLESS BASS", "SLAP BASS 1", "SLAP BASS 2",
					"SYNTH BASS 1", "SYNTH BASS 2", "VIOLIN", "VIOLA", "CELLO",
					"CONTRABASS", "TREMOLO STRINGS", "PIZZICATO STRINGS",
					"ORCHESTRAL HARP", "TIMPANI", "STRING ENSEMBLE 1",
					"STRING ENSEMBLE 2", "SYNTH STRINGS 1", "SYNTH STRINGS 2",
					"CHOIR AAHS", "VOICE OOHS", "SYNTH VOICE", "ORCHESTRA HIT",
					"TRUMPET", "TROMBONE", "TUBA", "MUTED TRUMPET",
					"FRENCH HORN", "BRASS SECTION", "SYNTH BRASS 1",
					"SYNTH BRASS 2", "SOPRANO SAX", "ALTO SAX", "TENOR SAX",
					"BARITONE SAX", "OBOE", "ENGLISH HORN", "BASSOON",
					"CLARINET", "PICCOLO", "FLUTE", "RECORDER", "PAN FLUTE",
					"BLOWN BOTTLE", "SHAKUHACHI", "WHISTLE", "OCARINA",
					"SQUARE WAVE", "SAWTOOTH WAVE", "CALLIOPE", "CHIFF",
					"CHARANG", "VOICE", "FIFTHS", "BASS AND LEAD", "NEW AGE",
					"WARM", "POLYSYNTH", "CHOIR", "BOWED", "METAL", "HALO",
					"SWEEP", "RAIN", "SOUNDTRACK", "CRYSTAL", "ATMOSPHERE",
					"BRIGHTNESS", "GOBLINS", "ECHOES", "SCI-FI", "SITAR",
					"BANJO", "SHAMISEN", "KOTO", "KALIMBA", "BAG PIPE",
					"FIDDLE", "SHANAI", "TINKLE BELL", "AGOGO", "STEEL DRUMS",
					"WOODBLOCK", "TAIKO DRUM", "MELODIC TOM", "SYNTH DRUM",
					"REVERSE CYMBAL", "GUITAR FRET NOISE", "BREATH NOISE",
					"SEASHORE", "BIRD TWEET", "TELEPHONE RING", "HELICOPTER",
					"APPLAUSE", "GUNSHOT" });

	/**
	 * The major intervals for a major scale.
	 */
	//public static final int[] MAJOR_INTERVALS = { 0, 2, 4, 5, 7, 9, 11 };
	private int[] scaleIntervals = { 0, 2, 4, 5, 7, 9, 11 };
	
	private String scaleName = "MAJOR";

	/**
	 * The rest offset for each octave.
	 */
	private int restOffset = 0;

	/**
	 * The keyNote is the root note for a given chromosome. It's been calculated
	 * as the index of the NOTE plus the octave times NOTES.length.
	 */
	private int keyNote = 72; // C6

	/**
	 * The root note for the given chromosome.
	 */
	private String rootNote = "C";

	/**
	 * The octave for the given chromosome.
	 */
	private int octave = 5;

	/**
	 * The index of the beginning duration in the DURATION_MAP.
	 */
	public int beginningDuration = 3; // eight

	/**
	 * The number of notes in a phrase (chromosome).
	 */
	private int phraseNotes = 16;

	/**
	 * The maximum possible jump for a gene in the interval portion. This value
	 * is handled by jgap.
	 */
	private int maxIntervalJump = 7;

	/**
	 * The maximum possible jump for a gene in the duration portion. This value
	 * is handled by jgap.
	 */
	private int maxDurationJump = 2;

	/**
	 * The tempo (is handled by jfugue).
	 */
	private String tempo = "Allegro";

	/**
	 * The index of the solo organ. Refer to jfuguekeyNote manual for the organ
	 * indexing.
	 */
	private int soloOrgan = 19;

	private List<Duration> activeDurationList = Duration.buildDefaultArrayList();

	/**
	 * Applying the singleton pattern.
	 */
	private MusicConfiguration() {
	}

	/**
	 * 
	 * @return The instance of the MusicConfiguration class.
	 */
	public static MusicConfiguration getInstance() {
		return INSTANCE;
	}

	/**
	 * Updates the the keyNote when rootNote, restOffset or octave is changed.
	 */
	private void updateKeyNote() {
		keyNote = getNoteIndex(rootNote)
				+ ((NOTES.length + restOffset) * (octave + 1));
	}

	/**
	 * Gives the index of the given note as it appears in the NOTES array.
	 * 
	 * @param theNote
	 *            a given note
	 * @return the index of the note in the NOTES array.
	 */
	private int getNoteIndex(String theNote) {
		for (int i = 0; i < NOTES.length; i++)
			if (theNote.equalsIgnoreCase(NOTES[i]))
				return i;

		// this should never happen.
		throw new IllegalArgumentException("The given note : " + theNote
				+ " is invalid, this should never happen");
	}
	

	public int[] getScaleIntervals() {
		return scaleIntervals;
	}

	public void setScaleIntervals(int[] scaleIntervals) {
		this.scaleIntervals = scaleIntervals;
	}

	/**
	 * @return the keyNote
	 */
	public int getKeyNote() {
		return keyNote;
	}

	/**
	 * @return the rootNote
	 */
	public String getRootNote() {
		return rootNote;
	}

	public int getRootNoteIndex() {
		for (int i = 0; i < NOTES.length; i++) {
			if (NOTES[i].equalsIgnoreCase(rootNote))
				return i;
		}

		return -1;
	}

	/**
	 * @param rootNote
	 *            the rootNote to set
	 */
	public void setRootNote(String rootNote) {

		boolean noteFound = false;

		for (String n : NOTES)
			if (rootNote.equalsIgnoreCase(n))
				noteFound = true;

		if (!noteFound) {
			System.out.println("MusicConfiguration: Invalid rootNote value :"
					+ rootNote + ". Setting to default (C).");
			rootNote = "C";
		}
		this.rootNote = rootNote;
		// update the keyNote
		updateKeyNote();
	}

	/**
	 * @return the octave
	 */
	public int getOctave() {
		return octave;
	}

	/**
	 * @param octave
	 *            the octave to set
	 */
	public void setOctave(int octave) {
		if (octave < 2 || octave > 7) {
			System.out.println("MusicConfiguration: Invalid octave value :"
					+ octave + ". Setting to default (5).");
			octave = 5;
		}
		this.octave = octave;
		// update the keyNote
		updateKeyNote();
	}

	public String[] getCategory(int index) {

		List<String> cat = INSTRUMENT_NAMES.subList(index * 8, (index + 1) * 8);
		String[] catArray = new String[cat.size()];

		for (int i = 0; i < catArray.length; i++) {
			catArray[i] = cat.get(i);
		}

		return catArray;

	}

	/**
	 * @return the beginningDuration
	 */
	public int getBeginningDuration() {
		return beginningDuration;
	}
	
	
	//TODO correct duration stuff
	/**
	 * @param beginningDuration
	 *            the beginningDuration to set
	 */
	public void setBeginningDuration(int beginningDuration) {
		if (beginningDuration < 0 || beginningDuration > MusicConfiguration.getInstance().getActiveDurationList().size()) {
			System.out
					.println("MusicConfiguration: Invalid beginning duration value :"
							+ beginningDuration + ". Setting to default (0).");
			beginningDuration = 0;
		}
		this.beginningDuration = beginningDuration;
	}

	// TODO change this to smt more meaningful.
	public void setStaticBeginningDuration(int duration) {
		beginningDuration = (duration * 3) + 1;
	}

	/**
	 * @return the phraseNotes
	 */
	public int getPhraseNotes() {
		return phraseNotes;
	}

	/**
	 * @param phraseNotes
	 *            the phraseNotes to set
	 */
	public void setPhraseNotes(int phraseNotes) {
		if (phraseNotes < 4 || phraseNotes > 128) {
			System.out
					.println("MusicConfiguration: Invalid phrase notes value :"
							+ phraseNotes + ". Setting to default (16).");
			phraseNotes = 16;
		}
		this.phraseNotes = phraseNotes;
	}

	/**
	 * @return the maxIntervalJump
	 */
	public int getMaxIntervalJump() {
		return maxIntervalJump;
	}

	/**
	 * @param maxIntervalJump
	 *            the maxIntervalJump to set TODO check weather changing this
	 *            according to the restOffset is needed
	 */
	public void setMaxIntervalJump(int maxIntervalJump) {
		if (maxIntervalJump < 1 || maxIntervalJump > 12) {
			System.out
					.println("MusicConfiguration: Invalid maxIntevalJump value :"
							+ maxIntervalJump + ". Setting to default (5).");
			maxIntervalJump = 5;
		}
		this.maxIntervalJump = maxIntervalJump;
	}

	/**
	 * @return the maxDurationJump
	 */
	public int getMaxDurationJump() {
		return maxDurationJump;
	}

	/**
	 * @param maxDurationJump
	 *            the maxDurationJump to set
	 */
	public void setMaxDurationJump(int maxDurationJump) {
		if (maxDurationJump < 0 || maxDurationJump > MusicConfiguration.getInstance().getActiveDurationList().size()) {
			System.out
					.println("MusicConfiguration: Invalid maxDurationJump value :"
							+ maxDurationJump + ". Setting to default (1).");
			maxDurationJump = 1;
		}
		this.maxDurationJump = maxDurationJump;
	}

	/**
	 * @return the tempo
	 */
	public String getTempo() {
		return tempo;
	}
	
	public int getSelectedTempoIndex() {
		
		for(int i=0; i<TEMPOS.length; i++)
			if(tempo.equalsIgnoreCase(TEMPOS[i]))
				return i;
		
		return 10; // defaults to allegro
	}

	/**
	 * @param tempo
	 *            the tempo to set
	 */
	public void setTempo(String tempo) {

		boolean tempoFound = false;

		for (String a_tempo : TEMPOS)
			if (tempo.equalsIgnoreCase(a_tempo))
				tempoFound = true;

		if (!tempoFound) {
			System.out.println("MusicConfiguration: Invalid tempo value :"
					+ tempo + ". Setting to default (Allegro).");
			tempo = "Allegro";
		}
		this.tempo = tempo;
	}

	/**
	 * @return the soloOrgan
	 */
	public int getSoloOrgan() {
		return soloOrgan;
	}

	/**
	 * @param soloOrgan
	 *            the soloOrgan to set
	 */
	public void setSoloOrgan(int soloOrgan) {
		if (soloOrgan < 0 || soloOrgan > 127) {
			System.out
					.println("MusicConfiguration: Invalid solo organ value :"
							+ maxDurationJump
							+ ". Setting to default (75). \n"
							+ " . Refer to the jfugue manual for the organ-value relation.\n"
							+ "http://www.jfugue.org/jfugue-chapter2.pdf , p.12");
			soloOrgan = 75;
		}
		this.soloOrgan = soloOrgan;
	}

	public void setSoloOrgan(String instrument) {
		soloOrgan = INSTRUMENTS.get(instrument);
	}

	public String toString() {
		String str = "---------------------------------------------------\n"
				+ "Music Configuration                                \n"
				+ "---------------------------------------------------\n"
				+ "Rest Offset : "
				+ restOffset
				+ "\n"
				+ "Key Note : "
				+ keyNote
				+ "\n"
				+ "Root Note : "
				+ rootNote
				+ " \n"
				+ "Octave : "
				+ octave
				+ "\n"
				+ "Beggining Duration : "
				+ beginningDuration
				+ "\n"
				+ "Phrase Notes : "
				+ phraseNotes
				+ "\n"
				+ "Max Interval Jump : "
				+ maxIntervalJump
				+ "\n"
				+ "Max Duration Jump : "
				+ maxDurationJump
				+ "\n"
				+ "Tempo : "
				+ tempo
				+ "\n"
				+ "Solo Organ : " + soloOrgan + "\n"
				+ "Scale: " + formatScale(scaleIntervals) + "\n\n";

		return str;
	}
	
	public String formatScale(int[] scale) {
		String str = "";
		
		for (int i = 0; i < scale.length; i++) {
			str += scale[i] + " ";
		}
		
		return str;
	}
	
	public void setIntervalsString(String intervals) {
		StringTokenizer st = new StringTokenizer(intervals.trim(), ",");
		
		scaleIntervals = new int[st.countTokens()];
		
		int index = 0;
		
		while(st.hasMoreTokens()) {
			scaleIntervals[index++] = Integer.parseInt(st.nextToken());
		}
	}
	
	public String getIntervalsString() {
		String intervals = "";
		
		for (int i = 0; i < scaleIntervals.length; i++) {
			intervals += "" + scaleIntervals[i] + ",";
		}
		
		return intervals;
	}
	
	public String getScaleName() {
		return scaleName;
	}

	public void setScaleName(String scaleName) {
		this.scaleName = scaleName;
	}

	public void setActiveDurationList(String durationString) {
		//nullify current list
		activeDurationList = new ArrayList<Duration>();
		
		
		StringTokenizer st = new StringTokenizer(durationString.trim(), ",");
		
		while(st.hasMoreTokens()) {
			String token = st.nextToken().trim();
//			System.out.println("-->" + token + "<--");
			activeDurationList.add(Duration.fromString(token));
		}
		
	}
	
	public void setActiveDurationList(List<Duration> activeDurationList) {
		this.activeDurationList = activeDurationList;
	}
	
	public List<Duration> getActiveDurationList() {
		return activeDurationList;
	}
	
	public String[] getActiveDurationStringArray() {
		String[] list = new String[activeDurationList.size()];
		
		for (int i = 0; i < activeDurationList.size(); i++) {
			list[i] = activeDurationList.get(i).name();
		}
		
		return list;
	}
	
	public String getActiveDurationString() {
		String durStr = "";
		
		for(Duration d: activeDurationList)
			durStr += d.getCharacter() + ",";
		
		return durStr;
	}
}
