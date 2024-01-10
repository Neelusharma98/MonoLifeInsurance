package com.monocept.insurance.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "query")
public class Query {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column
	    private Long queryId;

	    @NotEmpty(message = "Title is required")
	    @Column
	    private String title;

	    @NotEmpty(message = "Message is required")
	    @Column
	    private String message;
	    
	    @Column
	    private String response;

	    @Column
	    private boolean isresolved=false;
	    
	    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
	    @JoinColumn(name = "CustomerId", referencedColumnName = "CustomerId")
	    private Customer CustomerId;
	    
	    
	    
}


