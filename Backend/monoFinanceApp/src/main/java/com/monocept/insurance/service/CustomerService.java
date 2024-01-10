package com.monocept.insurance.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monocept.insurance.dto.AccountDto;
import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.CustomerPostDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.dto.Message;

public interface CustomerService {

	CustomerGetDto getcustomerByUsername(String username);

	Page<CustomerGetDto> getAllCustomer(Pageable pageable);

	Message inActiveCustomer(long id);

	Message ActiveCustomer(long id);

	Message editCustomer(EditProfileDto editCustomerDto);

	Message addcustomer(CustomerPostDto customerDto);

	JwtAuthResponse customerLogin(LoginDto logindto);

	Page<AccountDto> getCustomerAccounts(Pageable pageable, long id);

}
