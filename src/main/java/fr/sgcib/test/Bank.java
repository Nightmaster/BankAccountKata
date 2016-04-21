package fr.sgcib.test;

import java.util.Map;
import java.util.TreeMap;

public class Bank {
	private static final Bank INSTANCE = new Bank();
	private static long clientCount = 0,
		accountCount = 0;
	private final Map<Long, Client> clients;

	private Bank() {
		this.clients = new TreeMap<Long, Client>();
	}

	public static Bank getInstance(){
		return INSTANCE;
	}

	public Map<Long, Client> getClients() {
		return this.clients;
	}
}
