package gr.evoltrio.midi;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class DurationTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		int multiplier = 1;
		Duration d;
		int wholeMeasure = 4;

		for (int i = 0; i < Duration.values().length; i++) {
			d = Duration.values()[i];

			// whole note
			if (i % 3 == 0)
				assertEquals(d.toString(), wholeMeasure, d.getValue()
						* multiplier, 0.01);
			else if (i % 3 == 1)
				assertEquals(d.toString(), wholeMeasure, (d.getValue()
						* multiplier * 2) / 3, 0.01);
			else if (i % 3 == 2) {
				assertEquals(d.toString(), wholeMeasure, (d.getValue()
						* multiplier * 3) / 2, 0.01);
				multiplier *= 2;
			}
		}
	}

}
