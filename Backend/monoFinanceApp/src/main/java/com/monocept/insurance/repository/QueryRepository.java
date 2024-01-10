package com.monocept.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.Query;

public interface QueryRepository extends JpaRepository<Query, Long> {

}
