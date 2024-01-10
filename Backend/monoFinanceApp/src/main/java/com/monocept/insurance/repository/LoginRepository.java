package com.monocept.insurance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.Login;

public interface LoginRepository extends JpaRepository<Login, Long> {
	
	Optional<Login> findByUsername(String username);

}
