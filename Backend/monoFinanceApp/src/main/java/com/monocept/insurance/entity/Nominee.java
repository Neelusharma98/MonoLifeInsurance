package com.monocept.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nominee")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

public class Nominee {

	    @Id
	    @Column
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long nomineeId;

	    @NotEmpty(message = "Nominee Name is required")
	    @Column
	    private String nomineeName;

	    @NotEmpty(message = "Relationship is required")
	    @Column
	    private String relationship;

    

   
}

