package com.monocept.insurance.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.InsurencePlan;

public interface InsurancePlanRepository extends JpaRepository<InsurencePlan, Long> {

	Page<InsurencePlan> findByIsactiveTrue(Pageable pageable);

}
