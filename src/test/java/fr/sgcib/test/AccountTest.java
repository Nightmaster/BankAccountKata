package fr.sgcib.test;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static java.math.BigDecimal.TEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static fr.sgcib.test.constants.AccountType.CHECKING_ACCOUNT;
import static fr.sgcib.test.constants.AccountType.SAVING_ACCOUNT;
import static fr.sgcib.test.constants.LogHelper.LF;
import static fr.sgcib.test.constants.LogHelper.OK;
import static fr.sgcib.test.constants.LogHelper.SEPARATOR;

public class AccountTest {
	@Test
	public void throwIAEOnIncorrectInitializationTest() {
		final String testCase = "throwIAEOnIncorrectInitializationTest() - ";
		final int overdraftAllowed = 500;
		final List<Throwable> throwables = new ArrayList<>();
		final Account account;

		System.out.println(LF + SEPARATOR + testCase  + "Try incorrect overdraftAllowed");
		account = new Account(0, CHECKING_ACCOUNT, TEN, -overdraftAllowed);
		assertEquals("The two overdraft allowed are not the same!", overdraftAllowed, account.getOverdraftAllowed());

		System.out.println(testCase  + "Try incorrect accountType");
		try {
			new Account(0, null,  TEN, overdraftAllowed);
		}
		catch (final Throwable th) {
			addAndCheck(throwables, th);
		}

		System.out.println(testCase  + "Try incorrect amount");
		try {
			new Account(0, CHECKING_ACCOUNT, null, overdraftAllowed);
		}
		catch (final Throwable th) {
			addAndCheck(throwables, th);
		}

		System.out.println(testCase  + "Try with all incorrect parameters");
		try {
			new Account(0, null, null, overdraftAllowed);
		}
		catch (final Throwable th) {
			addAndCheck(throwables, th);
		}

		System.out.println("Check list length");
		assertTrue("There only " + throwables.size() + " that has been thrown, on 4 expected!", 3 == throwables.size());

		System.out.println("Check exceptions are not null");
		throwables.forEach(exc -> assertNotNull("Exception " + throwables.indexOf(exc) + " is null!", exc));

		System.out.println("Check type of catch exceptions");
		throwables.forEach(exc -> assertTrue("Exception " + throwables.indexOf(exc) + " is not an IllegalArgumentException!", exc instanceof  IllegalArgumentException));
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	@Test
	public void initializeNormallyTest() {
		final String testCase = "initializeNormallyTest() - ";
		Throwable throwable = null;

		System.out.println(LF + SEPARATOR + testCase + "Launch standard Initialization");
		try {
			new Account(0, SAVING_ACCOUNT, TEN, 0);
		}
		catch (final Throwable exc) {
			throwable = exc;
		}
		assertNull(testCase + "An exception has been thrown on Account correct construction!", throwable);
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	private static void addAndCheck(final List<Throwable> throwables, final Throwable th) {
		assertNotNull(th);
		throwables.add(th);
	}
}
