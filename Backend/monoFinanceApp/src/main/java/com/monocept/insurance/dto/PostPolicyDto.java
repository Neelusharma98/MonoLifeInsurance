package com.monocept.insurance.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.util.Pair;

import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.Claim;
import com.monocept.insurance.entity.InsuranceScheme;
import com.monocept.insurance.entity.Nominee;
import com.monocept.insurance.entity.Payment;
import com.monocept.insurance.entity.PremiumType;
import com.monocept.insurance.entity.SubmittedDocument;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class PostPolicyDto {

	 
	 private long schemeId;
	 private long agentId;
	 private String username;
     private int duration;
	 private int premiumType;
     private Double investMent;
     private List<NomineeDto> nominees=new ArrayList<>();
     private Set<DocumentDto> docs=new HashSet<>();
	   
	    
}
