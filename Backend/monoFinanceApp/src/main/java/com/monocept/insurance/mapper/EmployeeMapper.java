package com.monocept.insurance.mapper;

import org.springframework.data.domain.Page;

import com.monocept.insurance.dto.AddEmployeeDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.ShowEmployeeDto;
import com.monocept.insurance.entity.Address;
import com.monocept.insurance.entity.Customer;
import com.monocept.insurance.entity.Employee;
import com.monocept.insurance.entity.Login;
import com.monocept.insurance.entity.UserDetails;

public class EmployeeMapper {
	
	public static Employee addEmployeeDtoToEmployee(AddEmployeeDto addEmployeeDto)
	{
		Employee emp = new Employee();
		emp.setSalary(addEmployeeDto.getSalary());
		Login login = new Login();
		login.setUsername(addEmployeeDto.getUsername());
		login.setPassword(addEmployeeDto.getPassword());
		emp.setLogin(login);
		UserDetails userDetails = new UserDetails();
		userDetails.setFirstName(addEmployeeDto.getFirstName());
		userDetails.setLastName(addEmployeeDto.getLastName());
		userDetails.setEmail(addEmployeeDto.getEmail());
		userDetails.setMobileNumber(addEmployeeDto.getMobileNumber());
		userDetails.setDateOfBirth(addEmployeeDto.getDateOfBirth());
		Address address = new Address();
		address.setApartment(addEmployeeDto.getApartment());
		address.setCity(addEmployeeDto.getCity());
		address.setState(addEmployeeDto.getState());
		address.setHouseNo(addEmployeeDto.getHouseNo());
		address.setPincode(addEmployeeDto.getPincode());
		userDetails.setAddress(address);
		emp.setUserDetails(userDetails);
		return emp;
	}

	public static ShowEmployeeDto EmployeeToShowEmployeeDto(Employee employee) {
		ShowEmployeeDto showEmployeeDto = new ShowEmployeeDto();
		showEmployeeDto.setEmployeeId(employee.getEmployeeId());
		showEmployeeDto.setSalary(employee.getSalary());
		showEmployeeDto.setJoiningDate(employee.getJoiningDate());
		showEmployeeDto.setFirstName(employee.getUserDetails().getFirstName());
		showEmployeeDto.setLastName(employee.getUserDetails().getLastName());
		showEmployeeDto.setMobileNumber(employee.getUserDetails().getMobileNumber());
		showEmployeeDto.setEmail(employee.getUserDetails().getEmail());
		showEmployeeDto.setDateOfBirth(employee.getUserDetails().getDateOfBirth());
		showEmployeeDto.setStatus(employee.isActive()==true?"Active":"Inactive");
		
		return showEmployeeDto;
		
	}

	public static Employee editEmployeeDtoToEmployee(EditProfileDto editEmployeeDto,Employee employee) {
		
		Login login = employee.getLogin();
		UserDetails userDetails = employee.getUserDetails();
		userDetails.setFirstName(editEmployeeDto.getFirstName()==null?employee.getUserDetails().getFirstName():editEmployeeDto.getFirstName());
		userDetails.setLastName(editEmployeeDto.getLastName()==null?employee.getUserDetails().getLastName():editEmployeeDto.getLastName());
		userDetails.setEmail(editEmployeeDto.getEmail()==null?employee.getUserDetails().getEmail():editEmployeeDto.getEmail());
		userDetails.setMobileNumber(editEmployeeDto.getMobile()==null?employee.getUserDetails().getMobileNumber():editEmployeeDto.getMobile());
		userDetails.setDateOfBirth(editEmployeeDto.getDateOfBirth()==null?employee.getUserDetails().getDateOfBirth():editEmployeeDto.getDateOfBirth());
		employee.setUserDetails(userDetails);
		return employee;
	}

	

}
