package com.rankinteractive.casino.model.dto;

import java.time.LocalDate;

public class WagerDto {

	private Long transactionId;
	private String username;
	private LocalDate createdDate;
	private Long playerId;
	private double amount;
	private String promoCode;
	private double playerRemainingBalance;
	private boolean win;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public double getPlayerRemainingBalance() {
		return playerRemainingBalance;
	}

	public void setPlayerRemainingBalance(double playerRemainingBalance) {
		this.playerRemainingBalance = playerRemainingBalance;
	}

	public String isWin() {
		return win ? "Win" : "Loss";
	}

	public void setWin(boolean win) {
		this.win = win;
	}

}
