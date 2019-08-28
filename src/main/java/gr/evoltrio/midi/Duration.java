package gr.evoltrio.midi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Duration enum represents in a meaningful and constant way
 * the duration that are supported by the jfugue library.
 * 
 * Currently besides the common durations are as well supported 
 * dotted and triplets.
 * 
 * @author Konstantinos Georgiadis
 * @since 0.1.0
 *
 */
public enum Duration {
	
	WHOLE("w", 4),
	WHOLE_HALF("w.", 6),
	WH0LE_TRIP("w*", 2.6667),
	
	HALF("h", 2d),
	HALF_DOT("h.", 3d),
	HALF_TRIP("h*", 1.33332),
	
	QUARTER("q", 1d),
	QUARTER_HALF("q.", 1.5),
	QUARTER_TRIP("q*", 0.66666),
	
	EIGHTH("i", 0.5),
	EIGHTH_HALF("i.", 0.75),
	EIGHTH_TRIP("i*", 0.333333334),
	
	SIXTEENTH("s", 0.25),
	SIXTEENTH_HALF("s.", 0.375),
	SIXTEENTH_TRIP("s*", 0.166666667),
	
	THIRTY_SECOND("t", 0.125),
	THIRTY_SECOND_HALF("t.", 0.1875),
	THIRTY_SECOND_TRIP("t*", 0.083333333),
	
	SIXTY_FORTH("x", 0.0625),
	SIXTY_FORTH_HALF("x.", 0.09375),
	SIXTY_FORTH_TRIP("x*", 0.041666666),
	
	ONE_TWENTY_EIGHTH("o", 0.03125),
	ONE_TWENTY_EIGHTH_HALF("o.", 0.046875),
	ONE_TWENTY_EIGHTH_TRIP("o*", 0.020833333);
	
	private String character;
	private double value;
	
	private static final Map<String, Duration> stringToEnum  =
			new HashMap<String, Duration>();
	
	static {
		for (Duration d: Duration.values())
			stringToEnum.put(d.getCharacter(), d);
	}
	
	public static List<Duration> buildDefaultArrayList() {
		List<Duration> defaultList = new ArrayList<Duration>();
		defaultList.add(Duration.HALF);
		defaultList.add(Duration.QUARTER);
		defaultList.add(Duration.EIGHTH);
		defaultList.add(Duration.SIXTEENTH);
		
		return defaultList;
	}

	private Duration(String character, double value) {
		this.character = character;
		this.value = value;
	}
	
	public static Duration fromString(String character) {
		return stringToEnum.get(character);
	}
	
	public String getCharacter() {
		return character;
	}

	public double getValue() {
		return value;
	}

	public String toString() {
		return "Character: " + character + " -- Value: " + value + "\n";
	}
	
	public static void main(String[] args) {
		
		for(Duration d : Duration.values())
			System.out.print("Duration class: " + d.name() + " -> " + d);
	}

}
