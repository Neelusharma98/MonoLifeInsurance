package com.monocept.insurance.controller;

import com.monocept.insurance.entity.Document;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/insuranceapp")
@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getalldoc")
    public ResponseEntity<List<Document>> getAllDocuments() {
    	System.out.println("in get document");
        List<Document> documents = documentService.getAllDocuments();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/adddoc")
    public ResponseEntity<Document> addDocument(@RequestBody Document document) {
        try {
            Document addedDocument = documentService.addDocument(document);
            return new ResponseEntity<>(addedDocument, HttpStatus.CREATED);
        } catch (InsuranceException e) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
