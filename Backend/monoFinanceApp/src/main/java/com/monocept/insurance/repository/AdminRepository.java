package com.monocept.insurance.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monocept.insurance.entity.Admin;


public interface AdminRepository extends JpaRepository<Admin, Long>{

	Page<Admin> findByIsactiveTrue(Pageable pageable);



}
