package com.monocept.insurance.mapper;



import org.springframework.beans.factory.annotation.Autowired;

import com.monocept.insurance.dto.AgentDto;
import com.monocept.insurance.dto.AgentGetDto;
import com.monocept.insurance.dto.EditProfileDto;
import com.monocept.insurance.entity.Address;
import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.Login;
import com.monocept.insurance.entity.UserDetails;
import com.monocept.insurance.repository.UserDetailsRepository;

public class AgentMapper {
	
	
	public UserDetails agentDtoToAgent(AgentDto agentDto) {
		
		
		UserDetails userdetails = new UserDetails();
		userdetails .setFirstName(agentDto.getFirstName());
		userdetails .setLastName(agentDto.getLastName());
		userdetails .setMobileNumber(agentDto.getMobileNumber());
		userdetails .setEmail(agentDto.getEmail());
		userdetails.setDateOfBirth(agentDto.getDateOfBirth());
	    System.out.println("-----------------------------------------------"+userdetails);
		Address address = new Address();
		address.setHouseNo(agentDto.getHouseNo());
		address.setApartment(agentDto.getApartment());
		address.setCity(agentDto.getCity());
		address.setState(agentDto.getState());
		address.setPincode(agentDto.getPincode());
		userdetails.setAddress(address);
		System.out.println("-----------------------------------------------"+userdetails);

		
		return userdetails;
		
	}

	public AgentGetDto agentToAgentGetDto(Agent agentDb) {
		
		AgentGetDto agentGetDto=new AgentGetDto();
		
		agentGetDto.setId(agentDb.getAgentId());
		agentGetDto.setFirstName(agentDb.getUserDetails().getFirstName());
		agentGetDto.setLastName(agentDb.getUserDetails().getLastName());
		agentGetDto.setEmail(agentDb.getUserDetails().getEmail());
		agentGetDto.setDateOfBirth(agentDb.getUserDetails().getDateOfBirth());
		agentGetDto.setMobileNumber(agentDb.getUserDetails().getMobileNumber());
//		agentGetDto.setUserName(agentDb.getLogin().getUsername());
		agentGetDto.setStatus(agentDb.isIsactive()==true?"Active":"InActive");
		double totalCommission = agentDb.getTotalCommission();
		double roundedCommission = Math.round(totalCommission * 100.0) / 100.0;

		agentGetDto.setCommission(roundedCommission);
		
		return agentGetDto;
		
		
	}

	public  Agent agentProfileDtoToAgent(EditProfileDto agentProfileDto, Agent agent) {
		
		Login login = agent.getLogin();
		UserDetails userDetails = new UserDetails();
		userDetails.setFirstName(agentProfileDto.getFirstName());
		userDetails.setLastName(agentProfileDto.getLastName());
		userDetails.setMobileNumber(agentProfileDto.getMobile());
		userDetails.setEmail(agentProfileDto.getEmail());
		userDetails.setDateOfBirth(agentProfileDto.getDateOfBirth());
		agent.setUserDetails(userDetails);
		return agent;
	}

}