package com.monocept.insurance.dto;

import com.monocept.insurance.entity.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
	private String username;
	private long policyId;
	private long paymentId;
	private String paymentType;
	private double amount;
	private String cardNumber;
	private int cvv;
	private String expiry;
}