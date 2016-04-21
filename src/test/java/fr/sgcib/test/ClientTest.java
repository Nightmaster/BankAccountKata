package fr.sgcib.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static fr.sgcib.test.constants.LogHelper.LF;
import static fr.sgcib.test.constants.LogHelper.OK;
import static fr.sgcib.test.constants.LogHelper.SEPARATOR;
import static fr.sgcib.test.utils.TestUtils.addAndCheck;
import static fr.sgcib.test.utils.TestUtils.createAccount;

public class ClientTest {
	private static final String FAMILY_NAME = "Test",
		EMAIL_ADDRESS = "alice@test.fr",
		PHYSICAL_ADDRESS = "Cul-de-sac, The Shire";
	private static final String[] NAMES = new String[] {"Alice", "Wonderland"};

	private final static Set<Account> ACCOUNTS = new HashSet<>();

	static {
		ACCOUNTS.add(createAccount());
	}

	@Test
	public void throwIAEOnIncorrectInitializationTest() {
		final String testCase = "throwIAEOnIncorrectInitializationTest() - ";
		final List<Throwable> throwables = new ArrayList<>();

		tryCatchAndAdd(LF + SEPARATOR + testCase + "Try incorrect name", 0, false, null, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS, throwables);

		tryCatchAndAdd(testCase + "Try incorrect names", 0, false, FAMILY_NAME, new String[] {}, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS, throwables);

		tryCatchAndAdd(testCase + "Try incorrect emailAddress", 0, false, FAMILY_NAME, NAMES, null, PHYSICAL_ADDRESS, ACCOUNTS, throwables);

		tryCatchAndAdd(testCase + "Try incorrect accounts", 0, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, null, ACCOUNTS,throwables);

		tryCatchAndAdd(testCase + "Try incorrect accounts", 0, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, null, throwables);

		System.out.println("Check list length");
		assertTrue("There only " + throwables.size() + " that has been thrown, on 5 expected!", 5 == throwables.size());

		System.out.println("Check exceptions are not null");
		throwables.forEach(exc -> assertNotNull("Exception " + throwables.indexOf(exc) + " is null!", exc));

		System.out.println("Check type of catch exceptions");
		throwables.forEach(exc -> assertTrue("Exception " + throwables.indexOf(exc) + " is not an IllegalArgumentException!", exc instanceof  IllegalArgumentException));
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	@Test
	public void initializeNormallyTest() {
		final String testCase = "initializeNormallyTest() - ";
		List<Throwable> throwables = new ArrayList<>();

		tryCatchAndAdd(LF + SEPARATOR + testCase + "Launch standard Initialization", 0, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS, throwables);

		assertTrue(testCase + "An exception has been thrown on Account correct construction!", throwables.isEmpty());
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	private static void tryCatchAndAdd(final String log, int id, boolean isMale, final String familyName, final String[] names, final String emailAddress, final String physicalAddress,
			final Set<Account> accounts, final List<Throwable> throwables) {
		System.out.println(log);
		try {
			new Client(id, isMale, familyName, names, emailAddress, physicalAddress, accounts);
		}
		catch (final Throwable th) {
			addAndCheck(throwables, th);
		}
	}
}
