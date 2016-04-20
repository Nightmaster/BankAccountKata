package fr.sgcib.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OperationTest {
	@Test
	public void throwIAEOnIncorrectInitialization() {
		final List<Exception> exceptions = new ArrayList<>();
		final String emptyLabel = "",
			label = "label";
		final BigDecimal zero = BigDecimal.ZERO,
			one = BigDecimal.ONE;


		// Try incorrect label
		try {
			new Operation(emptyLabel, zero, one);
		}
		catch (final Exception iae) {
			assertNotNull(iae);
			exceptions.add(iae);
		}

		// Try incorrect previousAmount
		try {
			new Operation(label, null, one);
		}
		catch (final Exception iae) {
			assertNotNull(iae);
			exceptions.add(iae);
		}

		// Try incorrect newAmount
		try {
			new Operation(label, zero, null);
		}
		catch (final Exception iae) {
			assertNotNull(iae);
			exceptions.add(iae);
		}

		// Try with all incorrect parameters
		try {
			new Operation(emptyLabel, null, null);
		}
		catch (final Exception iae) {
			assertNotNull(iae);
			exceptions.add(iae);
		}

		checkExceptions(exceptions);
	}

	private static void checkExceptions(final List<Exception> exceptions) {
		assertTrue("There only " + exceptions.size() + " that has been thrown, on 4 expected!", 4 == exceptions.size());
		exceptions.forEach(exc -> assertNotNull("Exception " + exceptions.indexOf(exc) + " is null!", exc));
		exceptions.forEach(exc -> assertTrue("Exception " + exceptions.indexOf(exc) + " is not an IllegalArgumentException!", exc instanceof  IllegalArgumentException));
	}
}