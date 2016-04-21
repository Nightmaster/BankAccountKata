package fr.sgcib.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static fr.sgcib.test.constants.AccountType.SAVING_ACCOUNT;
import static fr.sgcib.test.constants.LogHelper.LF;
import static fr.sgcib.test.constants.LogHelper.OK;
import static fr.sgcib.test.constants.LogHelper.SEPARATOR;

public class IntegrationTest {
	private final Bank bank = Bank.getInstance();
	private final static SecureRandom SECURE_RANDOM = new SecureRandom();

	/**
	* Create a randomized list of users in the bank
	**/
	@Before
	public void  initializeClientsList() {
		int x = 50,
			random = x + ((int) (SECURE_RANDOM.nextDouble() * x)); // Random is in [50; 100] interval
		for (int i = 0; i < random; i++)
			this.bank.createClient(true, randomString(), new String[] {randomString()}, randomString() + "@" + randomString() + ".fr", randomString() + ' ' + randomString() + ' ' + randomString(), new BigDecimal(1500L), 500L);
	}

	@Test
	public void clientWantsToSaveMoneyUSTest() {
		final String testCase = "clientWantsToSaveMoneyUSTest() - ";
		final int amount = 50000;
		final BigDecimal realAmount;
		final Account savingAccount;

		System.out.println(LF + SEPARATOR + testCase + "Check we can create a new account and add money on it");
		savingAccount = addMoneyOnNewSavingAccount(amount, this.bank.getClients().get(25L));
		realAmount = savingAccount.getAmount();
		assertTrue(testCase + "Amount missmatch: expected " + amount + " but found: " + realAmount.toPlainString(), 0 == new BigDecimal(amount).compareTo(realAmount));
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	@Test
	public void clientWantsToRetrieveMoneyUSTest() {

	}

	@Test
	public void clientWantsToCheckOperationsUSTest() {

	}

	private Account addMoneyOnNewSavingAccount(final int amount, final Client client) {
		final Account savingAccount;

		savingAccount = this.bank.createAccountForClient(client, SAVING_ACCOUNT, new BigDecimal(0), 0);
		savingAccount.addOrRemoveMoney(new BigDecimal(amount));

		return savingAccount;
	}

	private static String randomString() {
		return new BigInteger(130, SECURE_RANDOM).toString(32);
	}
}
