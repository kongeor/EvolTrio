package gr.evoltrio.fitness;

import gr.evoltrio.fitness.impl.DirectionFilter;
import gr.evoltrio.fitness.impl.DiversityFilter;
import gr.evoltrio.fitness.impl.ScaleFilter;
import gr.evoltrio.fitness.impl.PatternFilter;
import gr.evoltrio.fitness.impl.RootNoteFilter;
import gr.evoltrio.fitness.impl.SimpleDurationFilter;
import gr.evoltrio.fitness.impl.SimplePitchFilter;
import gr.evoltrio.fitness.impl.TimeFilter;

/**
 * 
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class FiltersFactory {

	public static enum Filter {
		SIMPLEPITCH, SIMPLEDURATION, SCALE, TIME, DIVERSITY, ROOTNOTE, DIRECTION, PATTERN
	}

	public static IFitnessFilter getFilter(Filter filter) {
		switch (filter) {
		case SIMPLEPITCH:
			return new SimplePitchFilter();
		case SIMPLEDURATION:
			return new SimpleDurationFilter();
		case SCALE:
			return new ScaleFilter();
		case TIME:
			return new TimeFilter();
		case DIVERSITY:
			return new DiversityFilter();
		case ROOTNOTE:
			return new RootNoteFilter();
		case DIRECTION:
			return new DirectionFilter();
		case PATTERN:
			return new PatternFilter();

		default:
			return null; // should never happen
		}
	}
	
	public static IFitnessFilter getFilter(String filter) {
		
		if(filter.equals(Filter.SIMPLEPITCH.name()))
			return new SimplePitchFilter();
		if(filter.equals(Filter.SIMPLEDURATION.name()))
			return new SimpleDurationFilter();
		if(filter.equals(Filter.SCALE.name()))
			return new ScaleFilter();
		if(filter.equals(Filter.TIME.name()))
			return new TimeFilter();
		if(filter.equals(Filter.DIVERSITY.name()))
			return new DiversityFilter();
		if(filter.equals(Filter.ROOTNOTE.name()))
			return new RootNoteFilter();
		if(filter.equals(Filter.DIRECTION.name()))
			return new DirectionFilter();
		if(filter.equals(Filter.PATTERN.name()))
			return new PatternFilter();

			return null; // should never happen
		
	}
	
	public static Filter getName(IFitnessFilter filter) {
		if(filter instanceof SimplePitchFilter)
			return Filter.SIMPLEPITCH;
		if(filter instanceof SimpleDurationFilter)
			return Filter.SIMPLEDURATION;
		if(filter instanceof ScaleFilter)
			return Filter.SCALE;
		if(filter instanceof DirectionFilter)
			return Filter.DIRECTION;
		if(filter instanceof DiversityFilter)
			return Filter.DIVERSITY;
		if(filter instanceof PatternFilter)
			return Filter.PATTERN;
		if(filter instanceof RootNoteFilter)
			return Filter.ROOTNOTE;
		if(filter instanceof TimeFilter)
			return Filter.TIME;

			return null; // should never happen
	}

	// TODO arrayList alternative implementation -- toArray cast ??
	public static String[] getFilterNamesArray() {
		Filter[] filter = Filter.values();
		String[] names = new String[filter.length];

		for (int i = 0; i < filter.length; i++) {
			names[i] = "" + filter[i];
		}

		return names;
	}
	
	public static void main(String[] args) {
		System.out.println(new DirectionFilter().getClass());
	}

}
