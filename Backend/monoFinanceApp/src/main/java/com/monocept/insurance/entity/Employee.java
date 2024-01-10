package com.monocept.insurance.entity;

import java.sql.Date;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor

public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
    private long employeeId;
	
	@Column
    private Double salary;
	
	@Column(name="date_of_joining")
	@CreationTimestamp
    private Date joiningDate;
    

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH} )
    @JoinColumn(name = "loginId")
    private Login login;

    
    @OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "UserDetailsId")
	private UserDetails userDetails;
    
    @Column
    private boolean isActive = true;

}
