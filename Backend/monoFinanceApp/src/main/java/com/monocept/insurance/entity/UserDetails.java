package com.monocept.insurance.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userDetailsId;
     
    @Column
    @NotEmpty(message = "First name is required")
    private String firstName;

    
    @Column
    @NotEmpty(message = "Last name is required")
    private String lastName;

    
    @Column
    @NotEmpty(message = "Mobile number is required")
    private String mobileNumber;

    @Column
    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    private String email;

    @Column
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressId")
    private Address address;
 
   

   
}
