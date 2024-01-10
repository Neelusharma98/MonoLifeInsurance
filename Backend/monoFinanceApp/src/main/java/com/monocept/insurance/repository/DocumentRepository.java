
package com.monocept.insurance.repository;

import com.monocept.insurance.entity.Document;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
    
    Optional<Document> findByDocumentName(String documentName);
}
