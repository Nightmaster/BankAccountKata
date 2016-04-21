package fr.sgcib.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import fr.sgcib.test.constants.AccountType;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static fr.sgcib.test.utils.Utilitarians.checkListElements;

public class Client {
	private final long id;
	private final String lastName;
	private final List<String> names;
	private boolean isMale,
		isActive;
	private String emailAddress;
	private String physicalAddress;
	private List<Account> accounts;
	private Set<AccountType> accountTypes;

	public Client(final long id, final boolean isMale, final String lastName, final List<String> names, final String emailAddress, final String physicalAddress, final List<Account> accounts) {
		final List<String> trimmedNames = new ArrayList<>();
		final List<Account> checkedAccounts;
		if (isBlank(lastName) ||null == names || isBlank(emailAddress) || isBlank(physicalAddress) || null == accounts)
			throw new IllegalArgumentException();
		checkedAccounts = checkListElements(accounts);
		checkListElements(names).forEach(name -> {
			final String trimmed = name.trim();
			if (isNotBlank(trimmed))
				trimmedNames.add(trimmed);
		});
		if (trimmedNames.isEmpty())
			throw new IllegalArgumentException();
		else if (checkedAccounts.isEmpty())
			throw new IllegalArgumentException();
		this.id = id;
		this.lastName = lastName.trim();
		this.names = trimmedNames;
		this.isMale = isMale;
		this.emailAddress = emailAddress.trim();
		this.physicalAddress = physicalAddress.trim();
		this.accounts = checkedAccounts;
		this.isActive = true;
	}

	public Client(final long id, final boolean isMale, final String lastName, final String[] names, final String emailAddress, final String physicalAddress, final List<Account> accounts) {
		this(id, isMale, lastName, Arrays.asList(names), emailAddress, physicalAddress, accounts);
	}

	public long getId() {
		return this.id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public List<String> getNames() {
		return this.names;
	}

	public boolean isActive() {
		return this.isActive;
	}

	public void setActive(final boolean active) {
		this.isActive = active;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(final String emailAddress) {
		if (null == emailAddress)
			return;
		final String trimmed = emailAddress.trim();
		if (isNotBlank(trimmed))
			this.emailAddress = trimmed;
	}

	public String getPhysicalAddress() {
		return this.physicalAddress;
	}

	public void setPhysicalAddress(final String physicalAddress) {
		if (null == physicalAddress)
			return;
		final String trimmed = physicalAddress.trim();
		if (isNotBlank(trimmed))
			this.physicalAddress = trimmed;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(final List<Account> accounts) {
		if (null == accounts)
			return;
		final List<Account> checkedAccounts = checkListElements(accounts);

		if (!checkedAccounts.isEmpty())
			this.accounts = checkedAccounts;
	}

	public void addAccount(final Account account) {
		if (null == account)
			return;
		this.accounts.add(account);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.lastName, this.names, this.isActive, this.emailAddress, this.physicalAddress);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		final Client other = (Client) obj;
		return Objects.equals(this.id, other.id)
			&& Objects.equals(this.lastName, other.lastName)
			&& Objects.equals(this.names, other.names)
			&& Objects.equals(this.isActive, other.isActive)
			&& Objects.equals(this.emailAddress, other.emailAddress)
			&& Objects.equals(this.physicalAddress, other.physicalAddress);
	}

	@Override
	public String toString() {
		return "The  " + (this.isActive ? "active" : "inactive") + "Client n " + this.id + ": is named "
			+ (this.isMale ? "Mr" : "Ms") + this.lastName + namesToString()
			+ ", lives at: " + this.physicalAddress + ", and can be contacted via this email address: " + this.emailAddress;
	}

	private String namesToString() {
		final StringBuilder sb = new StringBuilder(this.lastName.toUpperCase());

		this.names.forEach(name -> sb.append(' ').append(name));

		return sb.toString();
	}
}
