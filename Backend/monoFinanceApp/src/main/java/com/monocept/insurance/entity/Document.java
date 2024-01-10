package com.monocept.insurance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="document")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int documentId;
	@Column
	private String documentName;

}
