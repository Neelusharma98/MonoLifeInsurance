package com.monocept.insurance.entity;


import java.util.Calendar;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.monocept.insurance.dto.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

public class Payment {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentid")
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymenttype")
    private PaymentType paymentType;
    

    @Column(name = "amount")
    private Double amount;

    @Column(name = "payment_date")
    @CreationTimestamp
    private Date paymentDate;

    @Column(name = "tax")
    private Double tax=0.0;

    @Column(name = "totalpayment")
    private Double totalPayment=0.0;
    
    @Column(name="card_number")
    private String cardNumber;
    
    @Column(name="cvv")
    private int cvv=0;
    
    @Column(name="expiry")
    private String expiry;
    
   
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus=PaymentStatus.UNPAID;	
	
}
