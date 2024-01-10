package com.monocept.insurance.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monocept.insurance.dto.AddEmployeeDto;
import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.ShowEmployeeDto;
import com.monocept.insurance.entity.Customer;
import com.monocept.insurance.entity.Employee;
import com.monocept.insurance.entity.Login;
import com.monocept.insurance.entity.Role;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.mapper.CustomerMapper;
import com.monocept.insurance.mapper.EmployeeMapper;
import com.monocept.insurance.repository.EmployeeRepository;
import com.monocept.insurance.repository.LoginRepository;
import com.monocept.insurance.repository.RoleRepository;
import com.monocept.insurance.security.JwtTokenProvider;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
    private LoginRepository loginRepository;

	
	@Override
	public JwtAuthResponse employeeLogin(LoginDto logindto) {
		try
		{
//			
			System.out.println("login dto value is +"+logindto);
			
			
			
			JwtAuthResponse response=new JwtAuthResponse();
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logindto.getUserName(), logindto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String token = jwtTokenProvider.generateToken(authentication);
			response.setTokenType("Bearer");
			response.setAccessToken(token);
			response.setUsername(authentication.getName());
			System.out.println("authentication is "+authentication);
			response.setRoleType(authentication.getAuthorities().iterator().next().toString());
			System.out.println("login dto value is rrrrrrrrrrr+"+response);
			if(!response.getRoleType().equals(logindto.getRoleType()))
			{
				throw new InsuranceException("Employee not exist!");
			}
			return response;
			
			
		}
		catch (BadCredentialsException e)
		{
			throw new InsuranceException("Bad Request");
		}
		
	}
   

	@Override
	public Message addEmployee(AddEmployeeDto addEmployeeDto) {

		List<Login> admindb = loginRepository.findAll();
		System.out.println("addadmin dto value "+addEmployeeDto);
		
		for(Login log:admindb)
		{
			if(addEmployeeDto.getUsername().equals(log.getUsername()))
			{
				throw new InsuranceException("username already used!");
			}
		}

		Employee emp = EmployeeMapper.addEmployeeDtoToEmployee(addEmployeeDto);
		Login login = new Login();
		login.setUsername(addEmployeeDto.getUsername());
		login.setPassword(passwordEncoder.encode(addEmployeeDto.getPassword()));

		Set<Role> role = new HashSet<>();

		Role userRole = roleRepository.findByRolename("ROLE_Employee").get();
		role.add(userRole);
		login.setRoles(role);
		emp.setLogin(login);
		employeeRepository.save(emp);
		Message message = new Message();
		message.setStatus(HttpStatus.OK);
		message.setMessage("employee Saved Successfully!");
		return message;

	}

	@Override
	public Message editEmployee(EditProfileDto editEmployeeDto) {
		Optional<Employee> employee = employeeRepository.findById(editEmployeeDto.getId());

		if (!employee.isPresent() || !employee.get().isActive())
			throw new InsuranceException("Employee doesn't exists!");

		Message message = new Message();
		Employee emp = EmployeeMapper.editEmployeeDtoToEmployee(editEmployeeDto, employee.get());

		System.out.println("final>>>>>>>>>>>>>>>>>>>>" + emp);
		employeeRepository.save(emp);
		message.setStatus(HttpStatus.OK);
		message.setMessage("employee Updated Successfully!");
		return message;

	}

	@Override
	public Message activeEmployee(Long employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (!employee.isPresent()) {
			throw new InsuranceException("Employee doesn't exists!");
		}

		if (employee.get().isActive() == true) {
			throw new InsuranceException("Employee is already Active!");
		}

		employee.get().setActive(true);
		employeeRepository.save(employee.get());
		Message message = new Message();
		message.setStatus(HttpStatus.OK);
		message.setMessage("Employee Activated successfully!");
		return message;
	}

	@Override
	public Message inActiveEmployee(Long employeeId) {
		Optional<Employee> employee = employeeRepository.findById(employeeId);
		if (!employee.isPresent()) {
			throw new InsuranceException("Employee doesn't exists!");
		}
		
		Message message = new Message();
		if (employee.get().isActive() == true) {
			employee.get().setActive(false);
			employeeRepository.save(employee.get());
		    message.setStatus(HttpStatus.OK);
			message.setMessage("Employee InActived successfully!");
			return message;
		}

		employee.get().setActive(true);
		employeeRepository.save(employee.get());
		message.setStatus(HttpStatus.OK);
		message.setMessage("Employee activated successfully!");
		return message;

	}

	@Override
	public Page<ShowEmployeeDto> getAllEmployee(Pageable pageable) {

		Page<Employee> employees = employeeRepository.findAll(pageable);
		Page<ShowEmployeeDto> employeesList = employees
				.map(employee -> (EmployeeMapper.EmployeeToShowEmployeeDto(employee)));

		if (employeesList.isEmpty()) {
			throw new InsuranceException("No Employee Found!");
		}

		return employeesList;
	}


	@Override
	public Employee getEmployeeByUsername(String username) {
		List<Employee>employees=employeeRepository.findAll();
		Employee employeeDb=null;
		
		for(Employee ct: employees) {
			System.out.println(ct.getLogin().getUsername());
			if(ct.getLogin().getUsername().equals(username)) {
				
				employeeDb=ct;
				break;
			}
		}
		
		if(employeeDb==null) {
			throw new InsuranceException("Agent is not found");
		}
		
		return employeeDb;
		
	}

}
