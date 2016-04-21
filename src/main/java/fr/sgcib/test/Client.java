package fr.sgcib.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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
	private final Set<AccountType> accountTypes = new HashSet<>();
	private boolean isMale,
		isActive;
	private String emailAddress;
	private String physicalAddress;
	private List<Account> accounts;

	public Client(final long id, final boolean isMale, final String lastName, final List<String> names, final String emailAddress, final String physicalAddress, final List<Account> accounts) {
		final List<String> trimmedNames = new ArrayList<>();
		final List<Account> checkedAccounts;
		if (isBlank(lastName) ||null == names || isBlank(emailAddress) || isBlank(physicalAddress) || null == accounts)
			throw new IllegalArgumentException();
		checkedAccounts = checkListElements(accounts);
		if (checkedAccounts.isEmpty())
			throw new IllegalArgumentException("No correct account to add! Please check the data");
		checkedAccounts.forEach(account -> this.accountTypes.add(account.getAccountType()));
		checkListElements(names).forEach(name -> {
			final String trimmed = name.trim();
			if (isNotBlank(trimmed))
				trimmedNames.add(trimmed);
		});
		if (trimmedNames.isEmpty())
			throw new IllegalArgumentException("No one of the names is valid! Please check data");
		else if (checkedAccounts.isEmpty() || checkedAccounts.size() != this.accountTypes.size())
			throw new IllegalArgumentException("Check the list of accounts to be sure they are not of the same type!");
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

		if (!checkedAccounts.isEmpty()) {
			this.accountTypes.clear();
			for (final Account account : checkedAccounts) {
				final int length = this.accountTypes.size();
				this.accountTypes.add(account.getAccountType());
				if (length != this.accountTypes.size())
					addAccount(account, true);
			}
		}
	}

	public void addAccount(final Account account) {
		addAccount(account, false);
	}

	private void addAccount(final Account account, final boolean checked) {
		if (!checked) {
			final int length = this.accountTypes.size();
			if (null == account)
				return;
			this.accountTypes.add(account.getAccountType());
			if (length == this.accountTypes.size())
				return;
		}
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
