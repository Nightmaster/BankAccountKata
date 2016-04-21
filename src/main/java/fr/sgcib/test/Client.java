package fr.sgcib.test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Client {
	private final long id;
	private final String lastName;
	private final List<String> names;
	private boolean isMale,
		isActive;
	private String emailAddress;
	private String physicalAddress;
	private Set<Account> accounts;

	public Client(final long id, final boolean isMale, final String lastName, final List<String> names, final String emailAddress, final String physicalAddress, final Set<Account> accounts) {
		this.id = id;
		this.lastName = lastName;
		this.names = names;
		this.isMale = isMale;
		this.emailAddress = emailAddress;
		this.physicalAddress = physicalAddress;
		this.accounts = accounts;
		this.isActive = true;
	}

	public Client(final long id, final boolean isMale, final String lastName, final String[] names, final String emailAddress, final String physicalAddress, final Set<Account> accounts) {
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
		this.emailAddress = emailAddress;
	}

	public String getPhysicalAddress() {
		return this.physicalAddress;
	}

	public void setPhysicalAddress(final String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public Set<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
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
