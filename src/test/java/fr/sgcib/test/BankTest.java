package fr.sgcib.test;

import java.math.BigDecimal;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class BankTest {
	@Test
	public void getClientListTest() {
		final Bank bank = Bank.getInstance();

		bank.createClient(true, "TEST", new String[] {"Tester"}, "tester@test.fr", "1 road of the Imagination, 4242 SF City", new BigDecimal(1000), 500L);

		assertNotEquals(0, bank.getClients().size());
	}
}
