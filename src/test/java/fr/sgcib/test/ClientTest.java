package fr.sgcib.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static fr.sgcib.test.constants.LogHelper.LF;
import static fr.sgcib.test.constants.LogHelper.OK;
import static fr.sgcib.test.constants.LogHelper.SEPARATOR;
import static fr.sgcib.test.utils.TestUtils.addAndCheck;
import static fr.sgcib.test.utils.TestUtils.createAccount;

public class ClientTest {
	private static final String TAB = "\t",
		SPACE = " ",
		FAMILY_NAME = "Test",
		EMAIL_ADDRESS = "alice@test.fr",
		EMAIL_ADDRESS_CHANGED = "Email address is not the same after set",
		PHYSICAL_ADDRESS = "Cul-de-sac, The Shire",
		PHYSICAL_ADDRESS_CHANGED = "Physical address is not the same after set",
		ACCOUNTS_CHANGED = "Accounts are not the same after set or add";
	private static final String[] NAMES = new String[] {"Alice", "Wonderland"};

	private final static List<Account> ACCOUNTS = new ArrayList<>();

	static {
		ACCOUNTS.add(createAccount());
	}

	@Test
	public void throwIAEOnIncorrectInitializationTest() {
		final String testCase = "throwIAEOnIncorrectInitializationTest() - ";
		final List<Throwable> throwables = new ArrayList<>();
		final List<Account> twoSavingAccounts = new ArrayList<>();

		tryCatchAndAdd(LF + SEPARATOR + testCase + "Try incorrect name", 0, false, null, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS, throwables);

		tryCatchAndAdd(testCase + "Try incorrect names", 0, false, FAMILY_NAME, new String[] {}, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS, throwables);

		tryCatchAndAdd(testCase + "Try incorrect emailAddress", 0, false, FAMILY_NAME, NAMES, null, PHYSICAL_ADDRESS, ACCOUNTS, throwables);

		tryCatchAndAdd(testCase + "Try incorrect accounts", 0, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, null, ACCOUNTS,throwables);

		tryCatchAndAdd(testCase + "Try incorrect accounts", 0, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, null, throwables);

		twoSavingAccounts.add(createAccount());
		twoSavingAccounts.add(createAccount());
		tryCatchAndAdd(testCase + "Try two accountsof same type", 0, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, twoSavingAccounts, throwables);

		System.out.println("Check list length");
		assertTrue("There only " + throwables.size() + " that has been thrown, on 6 expected!", 6 == throwables.size());

		System.out.println("Check exceptions are not null");
		throwables.forEach(exc -> assertNotNull("Exception " + throwables.indexOf(exc) + " is null!", exc));

		System.out.println("Check type of catch exceptions");
		throwables.forEach(exc -> assertTrue("Exception " + throwables.indexOf(exc) + " is not an IllegalArgumentException!", exc instanceof IllegalArgumentException));
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
			final List<Account> accounts, final List<Throwable> throwables) {
		System.out.println(log);
		try {
			new Client(id, isMale, familyName, names, emailAddress, physicalAddress, accounts);
		}
		catch (final Throwable th) {
			addAndCheck(throwables, th);
		}
	}

	@Test
	public void trimOnInitAndSet() {
		final String testCase = "trimOnInitAndSet() - ";
		final Client client;
		final Set<Client> clients = new HashSet<>(4);
		final String[] names = {SPACE + NAMES[0] + TAB, TAB + SPACE + NAMES[1] + SPACE + TAB};
		long it = 0L;

		System.out.println(LF + SEPARATOR + testCase + "Check trim is well done in constructor");
		clients.add(new Client(--it, false, SPACE + FAMILY_NAME + TAB, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS));
		clients.add(new Client(--it, false, FAMILY_NAME, names, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS));
		clients.add(new Client(--it, false, FAMILY_NAME, NAMES, TAB + EMAIL_ADDRESS + SPACE, PHYSICAL_ADDRESS, ACCOUNTS));
		clients.add(new Client(--it, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, SPACE + PHYSICAL_ADDRESS, ACCOUNTS));

		clients.forEach(current -> {
			assertEquals("Family name is not the same!", FAMILY_NAME, current.getLastName());
			for (int i = 0; i < NAMES.length; i++)
				assertEquals("Name n" + i + " are not the same!", NAMES[i], current.getNames().get(i));
			assertEquals("Email address is not the same", EMAIL_ADDRESS, current.getEmailAddress());
			assertEquals("Physical address is not the same", PHYSICAL_ADDRESS, current.getPhysicalAddress());
		});
		System.out.println(testCase + "Check on constructor is OK!\nNow let's check on setters");
		client = new Client(--it, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS);
		client.setEmailAddress(SPACE + EMAIL_ADDRESS + TAB);
		assertEquals(EMAIL_ADDRESS_CHANGED, EMAIL_ADDRESS, client.getEmailAddress());
		client.setPhysicalAddress(SPACE + PHYSICAL_ADDRESS + TAB);
		assertEquals(PHYSICAL_ADDRESS_CHANGED, PHYSICAL_ADDRESS, client.getPhysicalAddress());
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	@Test
	public void settersDoNotChangeDataOnNullOrBlankButChangeOnCorrectTest() {
		final String testCase = "settersDoNotChangeDataOnNullOrBlankButChangeOnCorrectTest() - ";
		final Client client = new Client(-10, false, FAMILY_NAME, NAMES, EMAIL_ADDRESS, PHYSICAL_ADDRESS, ACCOUNTS);
		final List<Account> accounts = new ArrayList<>();

		System.out.println(LF + SEPARATOR + testCase + "Check physical address setter set only on correct data");
		client.setPhysicalAddress("  \t     ");
		assertEquals(PHYSICAL_ADDRESS_CHANGED, PHYSICAL_ADDRESS, client.getPhysicalAddress());
		client.setPhysicalAddress(null);
		assertEquals(PHYSICAL_ADDRESS_CHANGED, PHYSICAL_ADDRESS, client.getPhysicalAddress());
		client.setPhysicalAddress("   a    ");
		assertEquals(PHYSICAL_ADDRESS_CHANGED, "a", client.getPhysicalAddress());
		System.out.println(testCase + "Check on physical address setter OK");

		System.out.println(testCase + "Check email address setter set only on correct data");
		client.setEmailAddress("      \t ");
		assertEquals(EMAIL_ADDRESS_CHANGED, EMAIL_ADDRESS, client.getEmailAddress());
		client.setEmailAddress(null);
		assertEquals(EMAIL_ADDRESS_CHANGED, EMAIL_ADDRESS, client.getEmailAddress());
		client.setEmailAddress(" \t  wonderland@alice.fr \t   ");
		assertEquals(EMAIL_ADDRESS_CHANGED, "wonderland@alice.fr", client.getEmailAddress());
		System.out.println(testCase + "Check on email address setter OK");

		System.out.println(testCase + "Check accounts setter and add change data only on correct received data");
		client.setAccounts(null);
		assertArrayEquals(ACCOUNTS_CHANGED, ACCOUNTS.toArray(), client.getAccounts().toArray());
		client.setAccounts(accounts);
		assertArrayEquals(ACCOUNTS_CHANGED, ACCOUNTS.toArray(), client.getAccounts().toArray());
		client.addAccount(null);
		assertArrayEquals(ACCOUNTS_CHANGED, ACCOUNTS.toArray(), client.getAccounts().toArray());
		client.addAccount(createAccount());
		assertArrayEquals(ACCOUNTS_CHANGED, ACCOUNTS.toArray(), client.getAccounts().toArray());
		System.out.println(testCase + OK + LF + SEPARATOR);
	}
}
