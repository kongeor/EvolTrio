package gr.evoltrio.fitness.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DirectionFilterTest.class, DiversityFilterTest.class,
		OutOfScaleFilterTest.class, PatternFilterTest.class,
		RootNoteFilterTest.class, SimpleDurationFilterTest.class })
public class AllTests {

}
