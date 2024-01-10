package com.monocept.insurance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monocept.insurance.entity.SchemeDocument;

public interface SchemeDocumentRepository extends JpaRepository<SchemeDocument, Long> {

}
