package fr.sgcib.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import fr.sgcib.test.constants.AccountType;

import static fr.sgcib.test.constants.AccountType.CHECKING_ACCOUNT;

public class Bank {
	private static final Bank INSTANCE = new Bank();
	private static long clientCount = 0,
		accountCount = 0;
	private final Map<Long, Client> clients;

	private Bank() {
		this.clients = new TreeMap<>();
	}

	public static Bank getInstance(){
		return INSTANCE;
	}

	public synchronized void createClient(final boolean isMale, final String familyName, final String[] names, final String emailAddress, final String physicalAddress, final BigDecimal amount, final long overdraftAllowed) {
		final List<Account> accounts = new ArrayList<>(1);
		final Client client;

		accounts.add(createAccount(CHECKING_ACCOUNT, amount, overdraftAllowed));
		client = new Client(clientCount, isMale, familyName, names, emailAddress, physicalAddress, accounts);
		this.clients.put(clientCount, client);
		clientCount++;
	}

	public void createAccountForClient(final Client client, final AccountType accountType, final BigDecimal amount, final long overdraftAllowed) {

	}

	private synchronized Account createAccount(final AccountType accountType, final BigDecimal amount, final long overdraftAllowed) {
		final Account checkingAccount;

		checkingAccount = new Account(accountCount, accountType, amount, overdraftAllowed);
		accountCount++;

		return checkingAccount;
	}

	public Map<Long, Client> getClients() {
		return this.clients;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.clients);
	}
}
