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
@Table(name = "roles")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Role {
	@Id
	@Column(name="roleid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleid;
	
	@Column(name="rolename")
    private String rolename;

    
	

}

