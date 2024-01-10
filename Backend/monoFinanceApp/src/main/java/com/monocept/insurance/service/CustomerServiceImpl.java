package com.monocept.insurance.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.monocept.insurance.dto.AccountDto;
import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.CustomerPostDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.entity.Customer;
import com.monocept.insurance.entity.InsurancePolicy;
import com.monocept.insurance.entity.InsuranceScheme;
import com.monocept.insurance.entity.Login;
import com.monocept.insurance.entity.Role;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.mapper.CustomerMapper;
import com.monocept.insurance.mapper.PolicyMapper;
import com.monocept.insurance.repository.CustomerRepository;
import com.monocept.insurance.repository.LoginRepository;
import com.monocept.insurance.repository.RoleRepository;
import com.monocept.insurance.security.JwtTokenProvider;

@Service
public class CustomerServiceImpl implements CustomerService {
   @Autowired
   private CustomerRepository customerRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;
   @Autowired
	private RoleRepository roleRepository;
   @Autowired
   private JwtTokenProvider jwtTokenProvider;
   @Autowired
   private AuthenticationManager authenticationManager;
   @Autowired
   private LoginRepository loginRepository;
   
   
   
   
   @Override
	public JwtAuthResponse customerLogin(LoginDto logindto) {
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
			if(!response.getRoleType().equals(logindto.getRoleType()))
			{
				throw new InsuranceException("Customer not exist!");
			}
			return response;
			
			
		}
		catch (BadCredentialsException e)
		{
			throw new InsuranceException("Bad Request");
		}
		
	}
   
   
	@Override
	public CustomerGetDto getcustomerByUsername(String username) {
		List<Customer>customers=customerRepository.findAll();
		Customer customerDb=null;
		
		for(Customer ct:customers) {
			System.out.println(ct.getLogin().getUsername());
			if(ct.getLogin().getUsername().equals(username)) {
				
				customerDb=ct;
				break;
			}
		}
		
		if(customerDb==null) {
			throw new InsuranceException("Customer not found");
		}
		
		return CustomerMapper.customerToCustomerGetDto(customerDb);
		
	}

	
	@Override
	public Page<CustomerGetDto> getAllCustomer(Pageable pageable) {
		Page<Customer> customers = customerRepository.findAll(pageable);
		Page<CustomerGetDto>customerList = customers.map(customer->(CustomerMapper.customerToCustomerGetDto(customer)));
		
		if(customerList.isEmpty())
		{
			throw new InsuranceException("No Customer Found!");
		}
		
		return customerList;
	}

	
	@Override
	public Message inActiveCustomer(long id) {
		Optional<Customer>customer = customerRepository.findById(id);
		if(!customer.isPresent())
		{
			throw new InsuranceException("Customer doesn't exists!");
		}
		Message message = new Message();
		if(customer.get().isIsactive())
		{
			customer.get().setIsactive(false);
			customerRepository.save(customer.get());
			message.setStatus(HttpStatus.OK);
			message.setMessage("Customer InActived successfully!");
			return message;
		}
		
		customer.get().setIsactive(true);
		customerRepository.save(customer.get());
		message.setStatus(HttpStatus.OK);
		message.setMessage("Customer Actived successfully!");
		return message;
	}

	
	@Override
	public Message ActiveCustomer(long id) {
		Optional<Customer>customer = customerRepository.findById(id);
		if(!customer.isPresent())
		{
			throw new InsuranceException("Customer doesn't exists!");
		}
		
		if(customer.get().isIsactive()==true)
		{
			throw new InsuranceException("Customer is already Active!");
		}
		
		customer.get().setIsactive(true);
		customerRepository.save(customer.get());
		Message message = new Message();
		message.setStatus(HttpStatus.OK);
		message.setMessage("Customer Activated successfully!");
		return message;
	}

	@Override
	public Message editCustomer(EditProfileDto editCustomerDto) {
        Optional<Customer> customer = customerRepository.findById(editCustomerDto.getId()); 
		
		
	    if(!customer.isPresent() || !customer.get().isIsactive())
		throw new InsuranceException("Customer doesn't exists!");
		
		Message message=new Message();
		Customer ct = CustomerMapper.editCustomerDtoToCustomer(editCustomerDto,customer.get());
		
		System.out.println("final>>>>>>>>>>>>>>>>>>>>"+customer);
		customerRepository.save(ct);
		message.setStatus(HttpStatus.OK);
		message.setMessage("employee Updated Successfully!");
		return message;
	}

	@Override
	public Message addcustomer(CustomerPostDto customerDto) {
		List<Login> admindb = loginRepository.findAll();
		System.out.println("addadmin dto value "+customerDto);
		
		for(Login log:admindb)
		{
			if(customerDto.getUsername().equals(log.getUsername()))
			{
				throw new InsuranceException("username already used!");
			}
		}
		
		    Customer ct = CustomerMapper.CustomerPostDtoToCustomer(customerDto);
		    Login login = new Login();
			login.setUsername(customerDto.getUsername());
			login.setPassword(passwordEncoder.encode(customerDto.getPassword()));

		Set<Role> role = new HashSet<>();
	      
		  Role userRole = roleRepository.findByRolename("ROLE_CUSTOMER").get(); 
		  role.add(userRole); 
		  login.setRoles(role);
		  ct.setLogin(login);
		  customerRepository.save(ct);
		Message message = new Message();
		message.setStatus(HttpStatus.OK);
		message.setMessage("Customer Saved Successfully!");
		return message;
	}


	@Override
	public Page<AccountDto> getCustomerAccounts(Pageable pageable, long id) {
		Customer ct = customerRepository.findById(id).get();
		
		List<InsurancePolicy>policies = ct.getPolicies();
		List<AccountDto>ac=new ArrayList<>();
		 for(InsurancePolicy p:policies)
		 {
			 ac.add(PolicyMapper.policyToAccountDto1(p));
		 }

	        int start = (int) pageable.getOffset();
	        int end = Math.min((start + pageable.getPageSize()), ac.size());
	        Page<AccountDto> allPolicies = new PageImpl<>(ac.subList(start, end), pageable, ac.size());

	        return allPolicies;
	}

	

}
