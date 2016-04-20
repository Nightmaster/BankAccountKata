package fr.sgcib.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import fr.sgcib.test.constants.OperationType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static fr.sgcib.test.constants.OperationType.INITIALIZATION;

public class OperationTest {
	@Test
	public void throwIAEOnIncorrectInitialization() {
		final List<Exception> exceptions = new ArrayList<>();
		final OperationType operationType= INITIALIZATION;
		final BigDecimal zero = BigDecimal.ZERO,
			one = BigDecimal.ONE;

		System.out.println("Try incorrect operationType");
		try {
			new Operation(null, zero, one);
		}
		catch (final Exception iae) {
			assertNotNull(iae);
			exceptions.add(iae);
		}

		System.out.println("Try incorrect previousAmount");
		try {
			new Operation(operationType, null, one);
		}
		catch (final Exception iae) {
			assertNotNull(iae);
			exceptions.add(iae);
		}

		System.out.println("Try incorrect newAmount");
		try {
			new Operation(operationType, zero, null);
		}
		catch (final Exception iae) {
			assertNotNull(iae);
			exceptions.add(iae);
		}

		System.out.println("Try with all incorrect parameters");
		try {
			new Operation(null, null, null);
		}
		catch (final Exception iae) {
			assertNotNull(iae);
			exceptions.add(iae);
		}

		checkExceptions(exceptions);
		System.out.println("\nTest is OK!");
	}

	private static void checkExceptions(final List<Exception> exceptions) {
		System.out.println("Check list length");
		assertTrue("There only " + exceptions.size() + " that has been thrown, on 4 expected!", 4 == exceptions.size());

		System.out.println("Check exceptions are not null");
		exceptions.forEach(exc -> assertNotNull("Exception " + exceptions.indexOf(exc) + " is null!", exc));

		System.out.println("Check type of catch exceptions");
		exceptions.forEach(exc -> assertTrue("Exception " + exceptions.indexOf(exc) + " is not an IllegalArgumentException!", exc instanceof  IllegalArgumentException));
	}
}