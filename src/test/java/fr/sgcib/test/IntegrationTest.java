package fr.sgcib.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static fr.sgcib.test.constants.AccountType.SAVING_ACCOUNT;

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
		final int amount = 50000;
		final BigDecimal realAmount;
		final Client client = this.bank.getClients().get(25L);

		this.bank.createAccountForClient(client, SAVING_ACCOUNT, new BigDecimal(0), 0);
		final Account savingAccount = client.getAccountByType(SAVING_ACCOUNT);
		savingAccount.addOrRemoveMoney(new BigDecimal(amount));

		realAmount = savingAccount.getAmount();
		assertTrue("Amount missmatch: expected " + amount + " but found: " + realAmount.toPlainString(), 0 == new BigDecimal(amount).compareTo(realAmount));
	}



	@Test
	public void clientWantsToRetrieveMoneyUSTest() {

	}

	@Test
	public void clientWantsToCheckOperationsUSTest() {

	}

	private static String randomString() {
		return new BigInteger(130, SECURE_RANDOM).toString(32);
	}
}
