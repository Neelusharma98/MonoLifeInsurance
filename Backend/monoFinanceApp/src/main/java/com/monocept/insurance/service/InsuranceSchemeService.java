package com.monocept.insurance.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.monocept.insurance.dto.AddSchemeDto;
import com.monocept.insurance.dto.EditSchemeDto;
import com.monocept.insurance.dto.GetSchemeDetailDto;
import com.monocept.insurance.dto.GetSchemeDto;
import com.monocept.insurance.dto.GetSchemeDto1;
import com.monocept.insurance.dto.Message;

public interface InsuranceSchemeService {
	
	Message addScheme(AddSchemeDto addSchemeDto);

	Message activeScheme(long schemeId);

	Message editScheme(EditSchemeDto editSchemeDto);

	Message inActiveScheme(long schemeId);

	GetSchemeDetailDto getSchemeByPlan(long planId);

	List<GetSchemeDto> getScheme(long planId);

	List<GetSchemeDto1> getScheme1(long planId);

}
