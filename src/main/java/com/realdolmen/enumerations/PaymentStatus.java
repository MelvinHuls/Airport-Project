package com.realdolmen.enumerations;

public enum PaymentStatus {
	PAYED_CREDIT_CARD, PAYMENT_PENDING, PAYED_ENDORSEMENT;

	@Override
	public String toString() {
		switch (this) {
		case PAYED_CREDIT_CARD:
			return "Payed with a creditcard";
		case PAYED_ENDORSEMENT:
			return "Payed through endorsement";
		case PAYMENT_PENDING:
			return "Payment is pending";
		}
		return null;
	}
}
