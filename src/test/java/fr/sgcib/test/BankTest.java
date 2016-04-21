package fr.sgcib.test;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class BankTest {
	@Test
	public void getClientListTest() {
		final Bank bank = Bank.getInstance();

		assertNotEquals(0, bank.getClients().size());
	}
}
