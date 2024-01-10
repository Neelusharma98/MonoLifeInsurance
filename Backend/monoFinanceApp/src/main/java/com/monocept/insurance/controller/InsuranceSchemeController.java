package com.monocept.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.monocept.insurance.dto.AddSchemeDto;
import com.monocept.insurance.dto.AgentGetDto;
import com.monocept.insurance.dto.EditSchemeDto;
import com.monocept.insurance.dto.GetSchemeDetailDto;
import com.monocept.insurance.dto.GetSchemeDto;
import com.monocept.insurance.dto.GetSchemeDto1;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.PlanDto;
import com.monocept.insurance.service.InsuranceSchemeService;

@RestController
@RequestMapping("insuranceapp")
public class InsuranceSchemeController {

	@Autowired
	private InsuranceSchemeService insuranceSchemeService;
	
	@GetMapping("/scheme")
	//@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<List<GetSchemeDto>>getSchemeById(@RequestParam long planId) {
        System.out.println("plan id is>>>>>>>>>>>>>>>>>>>>>> "+planId);
		List<GetSchemeDto>schemes= insuranceSchemeService.getScheme(planId);
        return ResponseEntity.ok(schemes);
     }
	@GetMapping("/scheme1")
	ResponseEntity<List<GetSchemeDto1>>getSchemeByPlan(@RequestParam long planId) {
        System.out.println("plan id is>>>>>>>>>>>>>>>>>>>>>> "+planId);
		List<GetSchemeDto1>schemes= insuranceSchemeService.getScheme1(planId);
        return ResponseEntity.ok(schemes);
     }
	
	
	
	@GetMapping("/schemeDetail")
	//@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<GetSchemeDetailDto>getSchemeDetails(@RequestParam long id) {
        System.out.println("plan id is>>>>>>>>>>>>>>>>>>>>>> "+ id);
		GetSchemeDetailDto schemes= insuranceSchemeService.getSchemeByPlan(id);
        return ResponseEntity.ok(schemes);
     }
	
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addscheme")
	public ResponseEntity<Message> addScheme(@RequestBody AddSchemeDto addSchemeDto)
	{   System.out.println("inside addScheme"+addSchemeDto);
		Message scheme = insuranceSchemeService.addScheme(addSchemeDto);
		return new ResponseEntity<>(scheme,HttpStatus.OK);
	}
	
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/Scheme")
	public ResponseEntity<Message> activeScheme(@RequestParam int schemeId)
	{   System.out.println("inside activeScheme"+schemeId);
		Message scheme = insuranceSchemeService.activeScheme(schemeId);
		return new ResponseEntity<>(scheme,HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/Scheme")
	public ResponseEntity<Message> editScheme(@RequestBody EditSchemeDto editSchemeDto)
	{   System.out.println("inside editScheme"+editSchemeDto);
		Message scheme = insuranceSchemeService.editScheme(editSchemeDto);
		return new ResponseEntity<>(scheme,HttpStatus.OK);
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteScheme")
	public ResponseEntity<Message> inActiveScheme(@RequestParam int schemeId)
	{   System.out.println("inside inactiveScheme"+schemeId);
		Message scheme = insuranceSchemeService.inActiveScheme(schemeId);
		return new ResponseEntity<>(scheme,HttpStatus.OK);
	}
	
	

}
