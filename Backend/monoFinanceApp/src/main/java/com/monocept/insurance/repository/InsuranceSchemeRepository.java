package com.monocept.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.InsuranceScheme;

public interface InsuranceSchemeRepository extends JpaRepository<InsuranceScheme,Long>{

}
