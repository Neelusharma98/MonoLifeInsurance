package com.monocept.insurance.entity;

import java.sql.Date;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "claim")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claimid")
    private Long claimId;

    @OneToOne
    @JoinColumn(name = "policyno")
    private InsurancePolicy policy;

    @Column(name = "claimamount")
    private double claimAmount;
    
    @Column(name = "bankName")
    private String bankName;
    
    @Column(name = "branchName")
    private String branchName;

    @Column(name = "bankaccountnumber")
    private String bankAccountNumber;

    @Column(name = "ifsccode")
    private String ifscCode;

    @Column(name = "claimdate")
    @CreationTimestamp  
    private Date date;

   // @Enumerated(EnumType.STRING)
    @Column(name = "claimstatus")
    private String status;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "agentId", referencedColumnName = "agentId")
	private Agent agent;

    
}
