package fr.sgcib.test.utils;

import java.util.List;
import fr.sgcib.test.Account;

import static java.math.BigDecimal.TEN;
import static org.junit.Assert.assertNotNull;
import static fr.sgcib.test.constants.AccountType.SAVING_ACCOUNT;

public final class TestUtils {
	private static long accountCount = -1;

	private TestUtils() {
		throw new AssertionError("Utils class have not to be instantiated!");
	}

	public static Account createAccount() {
		final Account account = new Account(accountCount, SAVING_ACCOUNT, TEN, 100);
		accountCount--;
		return account;
	}

	public static void addAndCheck(final List<Throwable> throwables, final Throwable th) {
		assertNotNull(th);
		throwables.add(th);
	}
}
