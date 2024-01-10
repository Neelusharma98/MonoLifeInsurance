package com.monocept.insurance.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.catalina.mapper.Mapper;
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
import com.monocept.insurance.dto.AdminGetDto;
import com.monocept.insurance.dto.AdminPostDto;
import com.monocept.insurance.dto.ClaimApproveDto;
import com.monocept.insurance.dto.JwtAuthResponse;
import com.monocept.insurance.dto.LoginDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.entity.Admin;
import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.Claim;
import com.monocept.insurance.entity.ClaimStatus;
import com.monocept.insurance.entity.Employee;
import com.monocept.insurance.entity.InsurancePolicy;
import com.monocept.insurance.entity.Login;
import com.monocept.insurance.entity.PolicyStatus;
import com.monocept.insurance.entity.Role;
import com.monocept.insurance.entity.UserDetails;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.mapper.EmployeeMapper;
import com.monocept.insurance.mapper.InsuranceSchemeMapper;
import com.monocept.insurance.mapper.UserMapper;
import com.monocept.insurance.repository.AdminRepository;
import com.monocept.insurance.repository.AgentRepository;
import com.monocept.insurance.repository.CustomerRepository;
import com.monocept.insurance.repository.EmployeeRepository;
import com.monocept.insurance.repository.LoginRepository;
import com.monocept.insurance.repository.PolicyRepository;
import com.monocept.insurance.repository.RoleRepository;
import com.monocept.insurance.security.JwtTokenProvider;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private PolicyRepository policyRepository;
    
    

	@Override
	public JwtAuthResponse adminLogin(LoginDto loginDto) {
		 try{
			 			 
			 JwtAuthResponse response = new JwtAuthResponse();
				System.out.println("login dto value is +"+loginDto);
				Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(), loginDto.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String token = jwtTokenProvider.generateToken(authentication);
				response.setTokenType("Bearer");
				response.setAccessToken(token);
				response.setUsername(authentication.getName());
				System.out.println("authentication is "+authentication);
				response.setRoleType(authentication.getAuthorities().iterator().next().toString());
				
				if(!response.getRoleType().equals(loginDto.getRoleType()))
				{
					throw new InsuranceException("Admin not exist!");
				}
				return response;
				
			}
			catch (BadCredentialsException e)
			{
				throw new InsuranceException("Bad Request");
			}
			
		}
	


	@Override
	public AdminGetDto addAdmin(AdminPostDto adminPostDto) {
		
		List<Login> admindb = loginRepository.findAll();
		System.out.println("addadmin dto value "+adminPostDto);
		
		for(Login log:admindb)
		{
			if(adminPostDto.getUsername().equals(log.getUsername()))
			{
				throw new InsuranceException("username already used!");
			}
		}
		
		
		
		 UserDetails details = UserMapper.adminPostDtoToUserDetails(adminPostDto);
			

	        Login login = new Login();
			login.setUsername(adminPostDto.getUsername());
			login.setPassword(passwordEncoder.encode(adminPostDto.getPassword()));

			Set<Role> role = new HashSet<>();
	      
			  Role userRole = roleRepository.findByRolename("ROLE_ADMIN").get(); 
			  role.add(userRole); 
			  login.setRoles(role);
			  Admin admin = new Admin();
			  
		      admin.setUserDetails(details);
		      admin.setLogin(login);

	          adminRepository.save(admin);
			  return UserMapper.userToUserGetDto(details);
		
		
	}

	@Override
	public Page<AdminGetDto> getAllAdmin(Pageable pageable) {
		Page<Admin> admins = adminRepository.findByIsactiveTrue(pageable);
		Page<AdminGetDto> adminList = admins.map(user -> UserMapper.userToUserGetDto(user.getUserDetails()));

	    if (adminList.isEmpty()) {
	        throw new InsuranceException("No Admin found");
	    }

	    
	    return adminList;
	}
	
	
	@Override
	public String validateuser(String token) {
		JwtTokenProvider jwtprovider = new JwtTokenProvider();
		String role = jwtprovider.getRole(token).toString();
		
		return role;
	}



	@Override
	public List<Agent> getAgentClaims() {
		List<Agent> agents=agentRepository.findAll();
		List<Agent>agentDb=new ArrayList<>();
		for(Agent agent:agents) {
			List<Claim> claims=agent.getClaims();
			boolean flag =false;
			for(Claim claim:claims) {
				if(claim.getStatus().equals("PENDING")) {
					flag=true;
				}
			}
			if(flag)
			agentDb.add(agent);
		}	
//	)
		return agentDb;
	}
	
	
	@Override
	public List<InsurancePolicy> getpolicyClaims() {
		
		List<InsurancePolicy>policies=policyRepository.findAll();
		
		List<InsurancePolicy>policyDb=new ArrayList<>();
		
		for(InsurancePolicy insurancePolicy:policies) {
			if(insurancePolicy.getClaims()!=null && (insurancePolicy.getStatus()==PolicyStatus.DROP || insurancePolicy.getStatus()==PolicyStatus.COMPLETE)) {
				
				policyDb.add(insurancePolicy);
			
			
			}
			
		}
		
		return policyDb;
		
	}
	
	@Override
	public Message agentClaimApprove(ClaimApproveDto claimAppproveDto) {
		Optional<Agent> agentDb=agentRepository.findById(claimAppproveDto.getAgentId());
		Agent agent=new Agent();
		
		if(!agentDb.isPresent()) {
			throw new InsuranceException("agent not found");
		}
		
		agent=agentDb.get();
		
		List<Claim>claims=agent.getClaims();
		
		Claim claim=null;
		
		for(Claim c:claims) {
			if(c.getClaimId()==claimAppproveDto.getClaimId()) {
				claim=c;
			}
		}
		
		if(claim==null) {
			throw new InsuranceException("claim not found");
		}
		
		if(agent.getTotalCommission()<claim.getClaimAmount()) {
			throw new InsuranceException("claim amount must be less than earning");
		}
		
		agent.setTotalCommission(agent.getTotalCommission()-claim.getClaimAmount());
		
		claim.setStatus(ClaimStatus.APPROVED.toString());
		
		agentRepository.save(agent);
		
		return new Message(HttpStatus.OK,"claim approved");
		
	}
	
	@Override
	public Message agentPolicyClaimApprove(long policyId) {
		// TODO Auto-generated method stub
		Optional<InsurancePolicy> insuraOptional=policyRepository.findById(policyId);
		InsurancePolicy insurancePolicy=null;
		if(!insuraOptional.isPresent()) {
			throw new InsuranceException("policy not found");
		}
		insurancePolicy=insuraOptional.get();
		insurancePolicy.getClaims().setStatus(ClaimStatus.APPROVED.toString());
		insurancePolicy.setStatus(PolicyStatus.CLAIMED);
		policyRepository.save(insurancePolicy);
		return  new Message(HttpStatus.OK,"policy claimed successfully");
	}



	@Override
	public Message agentClaimReject(ClaimApproveDto claimAppproveDto) {
		Optional<Agent> agentDb=agentRepository.findById(claimAppproveDto.getAgentId());
		Agent agent=new Agent();
		
		if(!agentDb.isPresent()) {
			throw new InsuranceException("agent not found");
		}
		
		agent=agentDb.get();
		
		List<Claim>claims=agent.getClaims();
		
		Claim claim=null;
		
		for(Claim c:claims) {
			if(c.getClaimId()==claimAppproveDto.getClaimId()) {
				claim=c;
			}
		}
		
		if(claim==null) {
			throw new InsuranceException("claim not found");
		}
		
		claim.setStatus(ClaimStatus.REJECT.toString());
		
		agentRepository.save(agent);
		
		return new Message(HttpStatus.OK,"claim Rejected");
	}



	@Override
	public Message agentPolicyClaimReject(long policyId) {
		// TODO Auto-generated method stub
				Optional<InsurancePolicy> insuraOptional=policyRepository.findById(policyId);
				InsurancePolicy insurancePolicy=null;
				if(!insuraOptional.isPresent()) {
					throw new InsuranceException("policy not found");
				}
				insurancePolicy=insuraOptional.get();
				insurancePolicy.getClaims().setStatus(ClaimStatus.REJECT.toString());
				insurancePolicy.setStatus(PolicyStatus.REJECT);
				policyRepository.save(insurancePolicy);
				return  new Message(HttpStatus.OK,"policy Rejected!");
	}



	

	
	
}
