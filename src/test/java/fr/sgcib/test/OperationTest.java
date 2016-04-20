package fr.sgcib.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import fr.sgcib.test.constants.OperationType;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static fr.sgcib.test.constants.OperationType.INITIALIZATION;

public class OperationTest {
	private static final char LF = '\n';
	private static final String OK = "Test is OK!",
	SEPARATOR = "------" + LF;

	@Test
	public void throwIAEOnIncorrectInitialization() {
		final String testCase = "throwIAEOnIncorrectInitialization() - ";
		final List<Exception> exceptions = new ArrayList<>();
		final OperationType operationType= INITIALIZATION;
		final BigDecimal zero = BigDecimal.ZERO,
			one = BigDecimal.ONE;

		System.out.println(LF + SEPARATOR + testCase  + "Try incorrect operationType");
		try {
			new Operation(null, zero, one);
		}
		catch (final Exception iae) {
			addAndCheck(exceptions, iae);
		}

		System.out.println(testCase  + "Try incorrect previousAmount");
		try {
			new Operation(operationType, null, one);
		}
		catch (final Exception iae) {
			addAndCheck(exceptions, iae);
		}

		System.out.println(testCase  + "Try incorrect newAmount");
		try {
			new Operation(operationType, zero, null);
		}
		catch (final Exception iae) {
			addAndCheck(exceptions, iae);
		}

		System.out.println(testCase  + "Try with all incorrect parameters");
		try {
			new Operation(null, null, null);
		}
		catch (final Exception iae) {
			addAndCheck(exceptions, iae);
		}

		System.out.println("Check list length");
		assertTrue("There only " + exceptions.size() + " that has been thrown, on 4 expected!", 4 == exceptions.size());

		System.out.println("Check exceptions are not null");
		exceptions.forEach(exc -> assertNotNull("Exception " + exceptions.indexOf(exc) + " is null!", exc));

		System.out.println("Check type of catch exceptions");
		exceptions.forEach(exc -> assertTrue("Exception " + exceptions.indexOf(exc) + " is not an IllegalArgumentException!", exc instanceof  IllegalArgumentException));
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	@Test
	public void initializeNormally() {
		final String testCase = "initializeNormally() - ";
		Exception exception = null;

		System.out.println(LF + SEPARATOR + testCase + "Launch standard Initialization");
		try {
			new Operation(INITIALIZATION, BigDecimal.ZERO, BigDecimal.ONE);
		}
		catch (final Exception exc) {
			exception = exc;
		}
		assertNull(testCase + "An exception has been thrown on Operation correct construction!", exception);
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	private static void addAndCheck(List<Exception> exceptions, Exception iae) {
		assertNotNull(iae);
		exceptions.add(iae);
	}
}