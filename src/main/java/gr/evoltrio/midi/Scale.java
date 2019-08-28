package gr.evoltrio.midi;

import java.util.HashMap;
import java.util.Map;

/**
 * A Scale representation enum constant that can hold 
 * the intervals of well known musical scales.
 */
public enum Scale {
	
	MAJOR(new int[]{0,2,4,5,7,9,11}),
	MINOR(new int[]{0,2,3,5,7,8,10}),
	MINOR_PENTATONIC(new int[]{0,3,5,7,10}),
	MINOR_BLUES(new int[]{0,3,5,6,7,10}),
	MINOR_HARMONIC(new int[]{0,2,3,5,7,8,11});
	
	private static final Map<String, Scale> stringToEnum  =
			new HashMap<String, Scale>();
	
	static {
		for (Scale s: Scale.values())
			stringToEnum.put(s.name(), s);
	}
	
	public static Scale fromString(String name) {
		return stringToEnum.get(name);
	}
	
	public static String[] stringValues() {
		String[] values = new String[Scale.values().length];
		
		for (int i = 0; i < values.length; i++) {
			values[i] = Scale.values()[i].name();
		}
		
		return values;
	}
	
	private int[] intervals;
	
	private Scale(int[] intervals) {
		this.intervals = intervals;
	}
	
	public int[] getIntervals() {
		return intervals;
	}

}
