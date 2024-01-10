package com.monocept.insurance.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "address")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    private Long addressId;

    private String houseNo;

    private String apartment;

    @NotEmpty(message = "City is required")
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;

    //@Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be a 6-digit number")
    @Column(name = "pincode")
    private int pincode;

   
}
