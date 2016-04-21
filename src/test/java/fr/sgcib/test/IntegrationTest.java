package fr.sgcib.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import fr.sgcib.test.constants.OperationType;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static fr.sgcib.test.constants.AccountType.CHECKING_ACCOUNT;
import static fr.sgcib.test.constants.AccountType.HOME_PURCHASE_SAVINGS_ACCOUNT;
import static fr.sgcib.test.constants.AccountType.SAVING_ACCOUNT;
import static fr.sgcib.test.constants.LogHelper.LF;
import static fr.sgcib.test.constants.LogHelper.OK;
import static fr.sgcib.test.constants.LogHelper.SEPARATOR;
import static fr.sgcib.test.constants.OperationType.CREDIT;
import static fr.sgcib.test.constants.OperationType.INITIALIZATION;
import static fr.sgcib.test.constants.OperationType.WITHDRAWAL;
import static fr.sgcib.test.utils.Utilitarians.isNegative;

public class IntegrationTest {
	private final Bank bank = Bank.getInstance();
	private final static SecureRandom SECURE_RANDOM = new SecureRandom();
	private LocalDateTime creationDate;
	private Comparator<Operation> sortByDateAndHour = (operation1, operation2) -> operation1.getDate().compareTo(operation2.getDate());

	private static String randomString() {
		return new BigInteger(130, SECURE_RANDOM).toString(32);
	}

	private static boolean isBetweenDateAndHour(final LocalDateTime dateToCompare, final LocalDateTime startDate, final LocalDateTime endDate) {
		final boolean beforeStartDate =  startDate.isBefore(dateToCompare),
			equalsStartDate = startDate.isEqual(dateToCompare),
			afterEndDate = endDate.isAfter(dateToCompare),
			equalsEndDate = endDate.isEqual(dateToCompare);
		return beforeStartDate && afterEndDate
			|| (equalsStartDate && afterEndDate)
			|| (beforeStartDate && equalsEndDate)
			|| (equalsStartDate && equalsEndDate);
	}

	/**
	* Create a randomized list of users in the bank
	**/
	@Before
	public void  initializeClientsList() {
		this.creationDate = LocalDateTime.now();
		int x = 50,
			random = x + ((int) (SECURE_RANDOM.nextDouble() * x)); // Random is in [50; 100] interval
		this.bank.clearClients();
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
		final String testCase = "clientWantsToRetrieveMoneyUSTest() - ";
		final int initialAmount = 100000,
			retrievedAmount = 25000;
		final Client client;
		final Account savingAccount;
		int expectedNewAmount;
		BigDecimal realAmount;

		System.out.println(LF + SEPARATOR + testCase + "Check we can create a new account and add money on it");
		client = this.bank.getClients().get(8L);
		savingAccount = addMoneyOnNewSavingAccount(initialAmount, client);
		realAmount = savingAccount.getAmount();
		assertTrue(testCase + "Amount mismatch: expected " + initialAmount + " but found: " + realAmount.toPlainString(), 0 == new BigDecimal(initialAmount).compareTo(realAmount));

		System.out.println(testCase + "Check we retrieve money from the created account");
		savingAccount.addOrRemoveMoney(new BigDecimal(-retrievedAmount));
		realAmount = savingAccount.getAmount();
		expectedNewAmount = initialAmount - retrievedAmount;
		assertTrue(testCase + "Amount mismatch: expected " + expectedNewAmount + " but found: " + realAmount.toPlainString(), 0 == new BigDecimal(expectedNewAmount).compareTo(realAmount));

		System.out.println(testCase + "Check we retrieve all the money from the created account");
		savingAccount.addOrRemoveMoney(realAmount.negate());
		realAmount = savingAccount.getAmount();
		assertTrue(testCase + "Amount mismatch: expected " + 0 + " but found: " + realAmount.toPlainString(), 0 == BigDecimal.ZERO.compareTo(realAmount));
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	@Test
	public void clientWantsToCheckOperationsUSTest() {
		final String testCase = "clientWantsToCheckOperationsUSTest() - ";
		final int[] amountsByStep = {1500, 0, 100000, -150, -15000, 15000, -15000},
			balanceByStep = {1500, 0, 100000, 1350, 85000, 16350, 1350};
		final Client client;
		final Account checkingAccount,
			homePurchaseSavingsAccount;
		final List<Operation> allOperations = new ArrayList<>();
		final List<LocalDateTime> dates = new ArrayList<>();
		BigDecimal realAmount,
			balance;

		dates.add(LocalDateTime.now());
		System.out.println(LF + SEPARATOR + testCase + "Make operation that we will check via operations just after");
		client = this.bank.getClients().get(25L);
		checkingAccount = client.getAccountByType(CHECKING_ACCOUNT);
		assertNotNull("Checking account might have been created, but is not!", checkingAccount);

		dates.add(LocalDateTime.now());
		homePurchaseSavingsAccount = this.bank.createAccountForClient(client, HOME_PURCHASE_SAVINGS_ACCOUNT, new BigDecimal(0), 0);
		assertNotNull("home purchase savings account might have been created, but is not!", homePurchaseSavingsAccount);
		dates.add(LocalDateTime.now());

		for (int i = 2; i < amountsByStep.length; i++) {
			final long expectedtime = System.currentTimeMillis() + 10;
			while (System.currentTimeMillis() < expectedtime) {} //Force Thread to run but not to block the LocalDateTime inner time
			dates.add(LocalDateTime.now());
			if (0 != i % 2 || 6 == i)
				checkingAccount.addOrRemoveMoney(new BigDecimal(amountsByStep[i]));
			else
				homePurchaseSavingsAccount.addOrRemoveMoney(new BigDecimal(amountsByStep[i]));
			dates.add(LocalDateTime.now());
		}

		System.out.println(testCase + "Retrieving the operations for the two counts");
		allOperations.addAll(checkingAccount.getOperations());
		allOperations.addAll(homePurchaseSavingsAccount.getOperations());
		allOperations.sort(this.sortByDateAndHour);

		System.out.println(testCase + "Checking that operations are as expected");
		for (int i = 0; i < allOperations.size(); i++) {
			final Operation currentOperation = allOperations.get(i);
			final LocalDateTime startDateToCheck,
				endDateToCheck,
				currentDate;
			OperationType expectedOperationType;
			if (0 == i) {
				startDateToCheck = this.creationDate;
				endDateToCheck = dates.get(0);
			}
			else {
				startDateToCheck = dates.get(i * 2 - 1);
				endDateToCheck = dates.get(i * 2);
			}
			currentDate = currentOperation.getDate();
			System.out.println(testCase + "Checking operation n°" + i);
			assertTrue(
				"Date  is not in expected date range: date=" + currentDate.format(ISO_LOCAL_DATE_TIME) + ", dateRange=[" + startDateToCheck.format(ISO_LOCAL_DATE_TIME) + ", " +
				endDateToCheck.format(ISO_LOCAL_DATE_TIME) + "] for operation n°" + i,
				isBetweenDateAndHour(currentDate, startDateToCheck, endDateToCheck));

			realAmount = currentOperation.getAmount();
			assertTrue(
				"Expected amounts are not the same: expected: " + amountsByStep[i] + " but found: " + realAmount.toPlainString() + " for operation n°" + i,
				0 == new BigDecimal(amountsByStep[i]).compareTo(realAmount));

			if (2 > i)
				expectedOperationType = INITIALIZATION;
			else
				expectedOperationType = isNegative(realAmount) ? WITHDRAWAL : CREDIT;
			assertEquals("Operation type mismatch!", expectedOperationType, currentOperation.getOperationType());


			balance = currentOperation.getNewAmount();
			assertTrue(
				"Expected balance are not the same: expected: " + balanceByStep[i] + " but found: " + balance.toPlainString() + " for operation n°" + i,
				0 == new BigDecimal(balanceByStep[i]).compareTo(balance));
			System.out.println(testCase + "Operation n°" + i + " is OK");
		}
		System.out.println(testCase + OK + LF + SEPARATOR);
	}

	private Account addMoneyOnNewSavingAccount(final int amount, final Client client) {
		final Account savingAccount;

		savingAccount = this.bank.createAccountForClient(client, SAVING_ACCOUNT, new BigDecimal(0), 0);
		savingAccount.addOrRemoveMoney(new BigDecimal(amount));

		return savingAccount;
	}
}
