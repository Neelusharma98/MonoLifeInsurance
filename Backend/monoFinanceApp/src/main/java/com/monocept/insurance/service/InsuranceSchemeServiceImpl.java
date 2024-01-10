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
import org.springframework.stereotype.Service;

import com.monocept.insurance.dto.AddSchemeDto;
import com.monocept.insurance.dto.EditSchemeDto;
import com.monocept.insurance.dto.GetSchemeDetailDto;
import com.monocept.insurance.dto.GetSchemeDto;
import com.monocept.insurance.dto.GetSchemeDto1;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.entity.InsuranceScheme;
import com.monocept.insurance.entity.InsurencePlan;
import com.monocept.insurance.entity.SchemeDocument;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.mapper.InsuranceSchemeMapper;
import com.monocept.insurance.repository.InsurancePlanRepository;
import com.monocept.insurance.repository.InsuranceSchemeRepository;
import com.monocept.insurance.repository.SchemeDocumentRepository;


@Service
public class InsuranceSchemeServiceImpl implements InsuranceSchemeService {

	@Autowired
	private InsuranceSchemeRepository insuranceSchemeRepository;
	@Autowired
	private InsurancePlanRepository insurancePlanRepository;
	@Autowired
	private SchemeDocumentRepository schemeDocumentRepository;
	
	
	@Override
	public Message addScheme(AddSchemeDto addSchemeDto) {
		
		System.out.println("i am here0000000000000000000000000000000000000000000000"+addSchemeDto);
		Optional<InsurencePlan> plan = insurancePlanRepository.findById(addSchemeDto.getPlanId());
		
		if(!plan.isPresent())
		{
			throw new InsuranceException("plan doesn't exist!");
		}
		
		List<InsuranceScheme>schemes = insuranceSchemeRepository.findAll(); 
		
		for(InsuranceScheme scheme:schemes)
		{
			if(scheme.getSchemeName().equalsIgnoreCase(addSchemeDto.getSchemeName()))
			throw new InsuranceException("Scheme alredy exists!");
		}
		
		Message message=new Message();
		InsuranceScheme insuranceScheme = InsuranceSchemeMapper.addSchemeDtoToScheme(addSchemeDto);
		Set<SchemeDocument>docs = new HashSet();
		for(long ct:addSchemeDto.getDocuments())
		{
			Optional<SchemeDocument>document =  schemeDocumentRepository.findById(ct);
			System.out.println("document value is "+document.get());
			if(!document.isPresent())
			throw new InsuranceException("document not exists!");
			insuranceScheme.getSchemeDetail().getDocuments().add(document.get());
		}
		
		
		
		plan.get().getScheme().add(insuranceScheme);
		insurancePlanRepository.save(plan.get());
		message.setStatus(HttpStatus.OK);
		message.setMessage("Scheme Added Successfully!");
		return message;
	}


	@Override
	public Message activeScheme(long schemeId) {
		Optional<InsuranceScheme>scheme = insuranceSchemeRepository.findById(schemeId);
		if(!scheme.isPresent())
		{
			throw new InsuranceException("Scheme not exists!");
		}
		
		if(scheme.get().isIsactive())
		{
			throw new InsuranceException("Scheme is already Active!");
		}
		scheme.get().setIsactive(true);
		insuranceSchemeRepository.save(scheme.get());
		Message message = new Message();
		message.setStatus(HttpStatus.OK);
		message.setMessage("Scheme Activated successfully!");
		return message;
	}


	@Override
	public Message editScheme(EditSchemeDto editSchemeDto) {
	   
		Optional<InsuranceScheme> scheme = insuranceSchemeRepository.findById(editSchemeDto.getSchemeId()); 
		
		
	    if(!scheme.isPresent() || !scheme.get().isIsactive())
		throw new InsuranceException("Scheme doesn't exists!");
		
		Message message=new Message();
		InsuranceScheme insuranceScheme = InsuranceSchemeMapper.editSchemeDtoToScheme(editSchemeDto,scheme.get());
		insuranceSchemeRepository.save(insuranceScheme);
		message.setStatus(HttpStatus.OK);
		message.setMessage("Scheme Updated Successfully!");
		return message;
		
	}


	@Override
	public Message inActiveScheme(long schemeId) {
		Optional<InsuranceScheme>scheme = insuranceSchemeRepository.findById(schemeId);
		if(scheme.isPresent())
		{
			throw new InsuranceException("Scheme not exists!");
		}
		Message message = new Message();
		
		if(scheme.get().isIsactive())
		{
			scheme.get().setIsactive(false);
			insuranceSchemeRepository.save(scheme.get());
			message.setStatus(HttpStatus.OK);
			message.setMessage("Scheme Inactivated successfully!");
			return message;
		}
		
		else
		{
		scheme.get().setIsactive(true);
		insuranceSchemeRepository.save(scheme.get());
		message.setStatus(HttpStatus.OK);
		message.setMessage("Scheme activated successfully!");
		return message;
		}
	}


	@Override
	public GetSchemeDetailDto getSchemeByPlan(long planId) {
		Optional<InsuranceScheme> scheme = insuranceSchemeRepository.findById(planId);
		
		if(!scheme.isPresent())
		{
			throw new InsuranceException("plan Not exists!");
		}
		
		GetSchemeDetailDto schemeDetail = new GetSchemeDetailDto();
		schemeDetail = InsuranceSchemeMapper.schemeToSchemeGetDto(scheme.get());
		return schemeDetail;
	}


	@Override
	public List<GetSchemeDto> getScheme(long planId) {
    Optional<InsurencePlan> plan = insurancePlanRepository.findById(planId);
		
		if(!plan.isPresent())
		{
			throw new InsuranceException("plan Not exists!000000"+plan.get().getScheme());
		}
		
		List<GetSchemeDto>schemes =new ArrayList<>();
		for(InsuranceScheme sc :plan.get().getScheme())
		{
			schemes.add(InsuranceSchemeMapper.schemeToSchemeDto(sc));
		}
		
		if(schemes.isEmpty())
			throw new InsuranceException("scheme not exists"+plan.get().getScheme());
		return schemes;
	}


	@Override
	public List<GetSchemeDto1> getScheme1(long planId) {
		
Optional<InsurencePlan> plan = insurancePlanRepository.findById(planId);
		
		if(!plan.isPresent())
		{
			throw new InsuranceException("plan Not exists!");
		}
		
		List<GetSchemeDto1>schemes =new ArrayList<>();
		for(InsuranceScheme sc :plan.get().getScheme())
		{
			schemes.add(InsuranceSchemeMapper.schemeToSchemeGetDto1(sc));
		}
		
		if(schemes.isEmpty())
			throw new InsuranceException("scheme not exists"+plan.get().getScheme());
		return schemes;
	}

}
