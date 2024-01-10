package com.monocept.insurance.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.monocept.insurance.dto.AddEmployeeDto;
import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.ShowEmployeeDto;
import com.monocept.insurance.entity.Employee;

public interface EmployeeService{

	Message addEmployee(AddEmployeeDto addEmployeeDto);
    Message editEmployee(EditProfileDto editEmployeeDto);
    Message activeEmployee(Long employeeId);
    Message inActiveEmployee(Long employeeId);
    Page<ShowEmployeeDto> getAllEmployee(Pageable pageable);
    JwtAuthResponse employeeLogin(LoginDto logindto);
	Employee getEmployeeByUsername(String username);
}
