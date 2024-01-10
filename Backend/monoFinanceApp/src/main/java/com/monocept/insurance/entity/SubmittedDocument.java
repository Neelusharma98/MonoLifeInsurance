package com.monocept.insurance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "submitteddocuments")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SubmittedDocument {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "documentid")
	    private Long documentId;
	    
	    @Column(name = "documentname")
	    private String documentName;
	    
	    @Column(name = "documentstatus")
	    private DocumentStatus documentStatus;

	    @Column(name = "documentimage")
	    private String documentImage;}