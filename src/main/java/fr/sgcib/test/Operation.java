package fr.sgcib.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import fr.sgcib.test.constants.OperationType;

public class Operation {
	private final OperationType operationType;
	private final LocalDate date;
	private final LocalTime hour;
	private final BigDecimal previousAmount, newAmount;

	public Operation(final OperationType operationType, final BigDecimal previousAmount, final BigDecimal newAmount) {
		this.operationType = operationType;
		this.previousAmount = previousAmount;
		this.newAmount = newAmount;
		this.date = LocalDate.now();
		this.hour = LocalTime.now();
	}

	public LocalDate getDate() {
		return this.date;
	}

	public LocalTime getHour() {
		return this.hour;
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
}
