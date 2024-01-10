package com.monocept.insurance.entity;

import java.sql.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "Commission")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Commission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long commisionId;

	@Column(name = "commision_type")
	private String commisionType;

	@Column(name = "issuedate")
	@CreationTimestamp
	private Date issueDate;

	@Column(name = "amount")
	private double amount;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "agentId", referencedColumnName = "agentId")
	private Agent agent;


}
