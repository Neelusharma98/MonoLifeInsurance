

package com.monocept.insurance.service;

import com.monocept.insurance.entity.Document;

import java.util.List;

public interface DocumentService {

    List<Document> getAllDocuments();
    Document addDocument(Document document);
}

