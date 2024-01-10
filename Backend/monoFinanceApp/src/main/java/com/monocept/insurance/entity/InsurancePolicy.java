package com.monocept.insurance.entity;




import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "insurance_policies")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class InsurancePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "policyno")
    private Long policyNo;
    
    
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
	@JoinColumn(name = "schemeid",referencedColumnName="schemeId")
    private InsuranceScheme insuranceScheme;

    @Column(name = "issuedate")
    @CreationTimestamp
    private Date issueDate;

    @Column(name = "maturitydate")
    private Date maturityDate;

    
    @Column(name = "premiumtype")
    private PremiumType premiumType;

    @Column(name = "sumassured")
    private Double sumAssured;

    @Column(name = "premiumamount")
    private Double premiumAmount;

    @Column(name = "status")
    private PolicyStatus status=PolicyStatus.PENDING;

    
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "agentId",referencedColumnName="agentId")
    private Agent agent;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Nominee> nominees;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Payment> payments;
    

    @OneToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "claim")
    private Claim claims;

    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private Set<SubmittedDocument> submittedDocuments = new HashSet<>();
      
    
}
