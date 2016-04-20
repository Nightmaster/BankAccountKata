package fr.sgcib.test;

import java.util.Map;
import java.util.TreeMap;

public class Bank {
	private final Map<Long, Client> clients;

	public Bank() {
		this.clients = new TreeMap<Long, Client>();
	}

	public Map<Long, Client> getClients() {
		return this.clients;
	}
}
