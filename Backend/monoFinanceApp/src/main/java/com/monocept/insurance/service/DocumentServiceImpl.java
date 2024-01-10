package com.monocept.insurance.service;

import com.monocept.insurance.entity.Document;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public Document addDocument(Document document) {
        // here Checking if the document with the same name already exists
        Optional<Document> existingDocument = documentRepository.findByDocumentName(document.getDocumentName());

        if (existingDocument.isPresent()) {
            throw new InsuranceException("Document with the same name already exists!");
        }

        return documentRepository.save(document);
    }
}
