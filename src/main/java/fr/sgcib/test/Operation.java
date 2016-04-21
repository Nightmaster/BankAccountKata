package fr.sgcib.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import fr.sgcib.test.constants.OperationType;

import static fr.sgcib.test.constants.OperationType.INITIALIZATION;

public class Operation {
	private final OperationType operationType;
	private final LocalDateTime date;
	private final BigDecimal previousAmount,
		newAmount;

	public Operation(final OperationType operationType, final BigDecimal previousAmount, final BigDecimal newAmount) {
		if (null == operationType || null == newAmount || null == previousAmount)
			throw new IllegalArgumentException("Error, at least one argument is empty or null. Please check data!");
		else if (!INITIALIZATION.equals(operationType) && 0 == newAmount.compareTo(previousAmount))
			throw new IllegalArgumentException("Error, the two amounts are the same, this is not allowed!");
		this.operationType = operationType;
		this.previousAmount = previousAmount;
		this.newAmount = newAmount;
		this.date = LocalDateTime.now();
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	public OperationType getOperationType() {
		return this.operationType;
	}

	public BigDecimal getNewAmount() {
		return this.newAmount;
	}

	public BigDecimal getPreviousAmount() {
		return this.previousAmount;
	}

	public BigDecimal getAmount() {
		return this.newAmount.add(this.previousAmount.negate());
	}
}
