package com.monocept.insurance.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.Customer;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	Page<Agent> findByIsactiveTrue(Pageable pageable);



}
