package com.monocept.insurance.entity;



import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "login")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor

public class Login{
	
	@Id
    @Column(name = "loginId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loginId;
	
	@Column(name="username")
    private String username;
	@Column(name="password")
    private String password;

    @ManyToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "loginid"),inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Role> roles =new HashSet<>();


}

