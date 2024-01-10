package com.monocept.insurance.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.Admin;
import com.monocept.insurance.entity.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	Page<Employee> findByIsActiveTrue(Pageable pageable);


}
