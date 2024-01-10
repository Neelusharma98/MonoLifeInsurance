package com.monocept.insurance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.UserDetails;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

	
}
