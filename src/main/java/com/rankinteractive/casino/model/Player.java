package com.rankinteractive.casino.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Player {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String username;
	private double balance;
	@OneToMany(mappedBy="player")
	private List<Wager> wagers = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public List<Wager> getWagers() {
		return wagers;
	}

	public void setWagers(List<Wager> wagers) {
		this.wagers = wagers;
	}

	

}
