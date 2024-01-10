package com.monocept.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
