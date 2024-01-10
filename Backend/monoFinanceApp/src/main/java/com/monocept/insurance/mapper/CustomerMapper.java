package com.monocept.insurance.mapper;

import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.CustomerPostDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.entity.Address;
import com.monocept.insurance.entity.Customer;
import com.monocept.insurance.entity.Login;
import com.monocept.insurance.entity.UserDetails;

public class CustomerMapper {

	public static CustomerGetDto customerToCustomerGetDto(Customer customerDb) {
		CustomerGetDto customerGetDto = new CustomerGetDto();
		customerGetDto.setId(customerDb.getCustomerId());
		customerGetDto.setFirstName(customerDb.getUserDetails().getFirstName());
		customerGetDto.setLastName(customerDb.getUserDetails().getLastName());
		customerGetDto.setMobile(customerDb.getUserDetails().getMobileNumber());
		customerGetDto.setEmail(customerDb.getUserDetails().getEmail());
		customerGetDto.setDateOfBirth(customerDb.getUserDetails().getDateOfBirth());
		customerGetDto.setStatus(customerDb.isIsactive()==true?"Active":"InActive");
		return customerGetDto;
	}

	public static Customer CustomerPostDtoToCustomer(CustomerPostDto customerDto) {
		Login login = new Login();
		login.setUsername(customerDto.getUsername());
		login.setPassword(customerDto.getPassword());
		UserDetails userDetails = new UserDetails();
		userDetails.setFirstName(customerDto.getFirstName());
		userDetails.setLastName(customerDto.getLastName());
		userDetails.setMobileNumber(customerDto.getMobileNumber());
		userDetails.setEmail(customerDto.getEmail());
		userDetails.setDateOfBirth(customerDto.getDateOfBirth());
		Address address = new Address();
		address.setApartment(customerDto.getApartment());
		address.setCity(customerDto.getCity());
		address.setHouseNo(customerDto.getHouseNo());
		address.setPincode(customerDto.getPincode());
		address.setState(customerDto.getState());
		userDetails.setAddress(address);
		Customer customer = new Customer();
		customer.setUserDetails(userDetails);
		customer.setLogin(login);
		return customer;
	}

	public static Customer editCustomerDtoToCustomer(EditProfileDto editCustomerDto, Customer customer) {
		UserDetails userDetails = new UserDetails();
		userDetails.setFirstName(editCustomerDto.getFirstName());
		userDetails.setLastName(editCustomerDto.getLastName());
		userDetails.setMobileNumber(editCustomerDto.getMobile());
		userDetails.setEmail(editCustomerDto.getEmail());
		userDetails.setDateOfBirth(editCustomerDto.getDateOfBirth());
		customer.setUserDetails(userDetails);
		return customer;
	   
	}

}
