package fr.sgcib.test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class Operation {
	private final String label;
	private final LocalDate date;
	private final LocalTime hour;
	private final BigDecimal previousAmount, newAmount;

	public Operation(final String label, final BigDecimal previousAmount, final BigDecimal newAmount) {
		this.label = label;
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

	public String getLabel() {
		return this.label;
	}

	public BigDecimal getNewAmount() {
		return this.newAmount;
	}

	public BigDecimal getPreviousAmount() {
		return this.previousAmount;
	}
}
