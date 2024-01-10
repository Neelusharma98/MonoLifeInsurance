package com.monocept.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.InsurancePolicy;

public interface PolicyRepository extends JpaRepository<InsurancePolicy, Long>{

}
