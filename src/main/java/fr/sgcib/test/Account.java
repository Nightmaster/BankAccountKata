package fr.sgcib.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import fr.sgcib.test.constants.AccountType;

import static fr.sgcib.test.constants.OperationType.INITIALIZATION;

public class Account {
	private final long id;
	private final AccountType accountType;
	private final List<Operation> operations = new ArrayList<>(1);
	private boolean overdraft;
	private long overdraftAllowed;
	private BigDecimal amount;

	public Account(final long id, final AccountType accountType, final BigDecimal amount, final long overdraftAllowed) {
		if (null == accountType ||null == amount)
			throw new IllegalArgumentException("The accountType or the amount is null! Please check the data");
		else if (1 == BigDecimal.ZERO.compareTo(amount))
			throw new IllegalArgumentException("The amount can not be a negative amount on Account creation");
		this.id = id;
		this.accountType = accountType;
		this.amount = amount;
		if (0 > overdraftAllowed)
			this.overdraftAllowed = -overdraftAllowed;
		else
			this.overdraftAllowed = overdraftAllowed;
		this.overdraft = false;
		this.operations.add(new Operation(INITIALIZATION, BigDecimal.ZERO, amount));
	}

	public long getId() {
		return this.id;
	}

	public AccountType getAccountType() {
		return this.accountType;
	}

	public List<Operation> getOperations() {
		return this.operations;
	}

	public boolean isOverdraft() {
		return this.overdraft;
	}

	public void setOverdraft(final boolean overdraft) {
		this.overdraft = overdraft;
	}

	public long getOverdraftAllowed() {
		return this.overdraftAllowed;
	}

	public void setOverdraftAllowed(final long overdraftAllowed) {
		this.overdraftAllowed = overdraftAllowed;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(final BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.accountType, this.operations, this.overdraft, this.overdraftAllowed, this.amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final Account other = (Account) obj;
		return Objects.equals(this.id, other.id)
			&& Objects.equals(this.accountType, other.accountType)
			&& Objects.equals(this.operations, other.operations)
			&& Objects.equals(this.overdraft, other.overdraft)
			&& Objects.equals(this.overdraftAllowed, other.overdraftAllowed)
			&& Objects.equals(this.amount, other.amount);
	}
}
