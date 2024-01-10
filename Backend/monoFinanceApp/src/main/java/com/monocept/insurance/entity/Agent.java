package com.monocept.insurance.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "agent")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Agent {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long agentId;
	
	@OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Commission> commissions;
    
    @Column
    private boolean isactive=true;
    
    @Column
    private double totalCommission=0.0;
    

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH} )
    @JoinColumn(name = "loginId")
    private Login login;

    
    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UserDetailsId")
	private UserDetails userDetails;
    
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Claim> claims;
}
