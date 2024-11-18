package com.techlabs.camunda.entity;

public class CardDetails {
	

	private double amount;
    private long pin;

    public CardDetails(double amount, int pin) {
        this.amount = amount;
        this.pin = pin;
    }

    
    
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getPin() {
		return pin;
	}

	public void setPin(long pin) {
		this.pin = pin;
	}
}
