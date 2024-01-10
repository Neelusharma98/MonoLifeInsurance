package com.monocept.insurance.mapper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.monocept.insurance.dto.AddSchemeDto;
import com.monocept.insurance.dto.EditSchemeDto;
import com.monocept.insurance.dto.GetSchemeDetailDto;
import com.monocept.insurance.dto.GetSchemeDto;
import com.monocept.insurance.dto.GetSchemeDto1;
import com.monocept.insurance.entity.InsuranceScheme;
import com.monocept.insurance.entity.SchemeDetail;
import com.monocept.insurance.entity.SchemeDocument;
import com.monocept.insurance.repository.DocumentRepository;
import com.monocept.insurance.repository.SchemeDocumentRepository;

public class InsuranceSchemeMapper {

	public static InsuranceScheme addSchemeDtoToScheme(AddSchemeDto addSchemeDto) {
		
		InsuranceScheme insuranceScheme = new InsuranceScheme();
		insuranceScheme.setSchemeName(addSchemeDto.getSchemeName());
		SchemeDetail schemeDetail = new SchemeDetail();
		schemeDetail.setDescription(addSchemeDto.getDescription());
		schemeDetail.setInstallmentCommRatio(addSchemeDto.getInstallmentCommRatio());
		schemeDetail.setMaxAge(addSchemeDto.getMaxAge());
		schemeDetail.setMaxAmount(addSchemeDto.getMaxAmount());
		schemeDetail.setMaxInvestmentTime(addSchemeDto.getMaxInvestmentTime());
		schemeDetail.setMinAge(addSchemeDto.getMinAge());
		schemeDetail.setMinAmount(addSchemeDto.getMinAmount());
		schemeDetail.setMinInvestmentTime(addSchemeDto.getMinInvestmentTime());
		schemeDetail.setProfitRatio(addSchemeDto.getProfitRatio());
		schemeDetail.setRegistrationCommRatio(addSchemeDto.getRegistrationCommRatio());
		schemeDetail.setSchemeImage(addSchemeDto.getSchemeImage());
		insuranceScheme.setSchemeDetail(schemeDetail);
		return insuranceScheme;
	}

	public static InsuranceScheme editSchemeDtoToScheme(EditSchemeDto editSchemeDto,InsuranceScheme scheme) {
		InsuranceScheme insuranceScheme = scheme;
		insuranceScheme.setSchemeName(editSchemeDto.getSchemeName()==null?scheme.getSchemeName():editSchemeDto.getSchemeName());
		insuranceScheme.setSchemeId(editSchemeDto.getSchemeId());
		SchemeDetail schemeDetail = scheme.getSchemeDetail();
		schemeDetail.setDescription(editSchemeDto.getDescription()==null?scheme.getSchemeDetail().getDescription():editSchemeDto.getDescription());
		schemeDetail.setInstallmentCommRatio(editSchemeDto.getInstallmentCommRatio()==null?scheme.getSchemeDetail().getInstallmentCommRatio():editSchemeDto.getInstallmentCommRatio());
		schemeDetail.setMaxAge(editSchemeDto.getMaxAge()==0?scheme.getSchemeDetail().getMaxAge():editSchemeDto.getMaxAge());
		schemeDetail.setMaxAmount(editSchemeDto.getMaxAmount()==null?scheme.getSchemeDetail().getMaxAmount():editSchemeDto.getMaxAmount());
		schemeDetail.setMaxInvestmentTime(editSchemeDto.getMaxInvestmentTime()==0?scheme.getSchemeDetail().getMaxInvestmentTime():editSchemeDto.getMaxInvestmentTime());
		schemeDetail.setMinAge(editSchemeDto.getMinAge()==0?scheme.getSchemeDetail().getMinAge():editSchemeDto.getMinAge());
		schemeDetail.setMinAmount(editSchemeDto.getMinAmount()==null?scheme.getSchemeDetail().getMinAmount():editSchemeDto.getMinAmount());
		schemeDetail.setMinInvestmentTime(editSchemeDto.getMinInvestmentTime()==0?scheme.getSchemeDetail().getMinInvestmentTime():editSchemeDto.getMinInvestmentTime());
		schemeDetail.setProfitRatio(editSchemeDto.getProfitRatio()==null?scheme.getSchemeDetail().getProfitRatio():editSchemeDto.getProfitRatio());
		schemeDetail.setRegistrationCommRatio(editSchemeDto.getRegistrationCommRatio()==null?scheme.getSchemeDetail().getRegistrationCommRatio():editSchemeDto.getInstallmentCommRatio());
		schemeDetail.setSchemeImage(editSchemeDto.getSchemeImage()==null?scheme.getSchemeDetail().getSchemeImage():editSchemeDto.getSchemeImage());
		schemeDetail.setDocuments(scheme.getSchemeDetail().getDocuments());
		insuranceScheme.setSchemeDetail(schemeDetail);
		System.out.println("insurance value >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+insuranceScheme);
		return insuranceScheme;
	}

	public static GetSchemeDetailDto schemeToSchemeGetDto(InsuranceScheme sc) {
		GetSchemeDetailDto getSchemeDetailDto = new GetSchemeDetailDto();
		getSchemeDetailDto.setDescription(sc.getSchemeDetail().getSchemeImage());
		getSchemeDetailDto.setMaxAge(sc.getSchemeDetail().getMaxAge());
		getSchemeDetailDto.setMaxAmount(sc.getSchemeDetail().getMaxAmount());
		getSchemeDetailDto.setMaxDuration(sc.getSchemeDetail().getMaxInvestmentTime());
		Set<String>docs = new HashSet<>();
		for(SchemeDocument doc:sc.getSchemeDetail().getDocuments())
		{
			docs.add(doc.getDocumentName());
		}
		getSchemeDetailDto.setRequierdDocs(docs);
		getSchemeDetailDto.setMinAge(sc.getSchemeDetail().getMinAge());
		getSchemeDetailDto.setMinAmount(sc.getSchemeDetail().getMinAmount());
		getSchemeDetailDto.setMinDuration(sc.getSchemeDetail().getMinInvestmentTime());
		getSchemeDetailDto.setSchemeImage(sc.getSchemeDetail().getSchemeImage());
		System.out.println("schemedto+++++++++++++++++++"+getSchemeDetailDto);
		return getSchemeDetailDto;
		
	}

	public static GetSchemeDto schemeToSchemeDto(InsuranceScheme sc) {
		GetSchemeDto schemeDto = new GetSchemeDto();
		schemeDto.setId(sc.getSchemeId());
		schemeDto.setSchemeName(sc.getSchemeName());
		schemeDto.setStatus(sc.isIsactive()==true?"Active":"Inactive");
		return schemeDto;
	}
	
	public static GetSchemeDto1 schemeToSchemeGetDto1(InsuranceScheme sc) {
		GetSchemeDto1 getSchemeDto = new GetSchemeDto1();
		getSchemeDto.setSchemeId(sc.getSchemeId());
		getSchemeDto.setSchemeName(sc.getSchemeName());
		getSchemeDto.setDescription(sc.getSchemeDetail().getDescription());
		getSchemeDto.setMaxAge(sc.getSchemeDetail().getMaxAge());
		getSchemeDto.setMaxAmount(sc.getSchemeDetail().getMaxAmount());
		getSchemeDto.setMaxDuration(sc.getSchemeDetail().getMaxInvestmentTime());
		Set<String>docs = new HashSet<>();
		for(SchemeDocument doc:sc.getSchemeDetail().getDocuments())
		{
			docs.add(doc.getDocumentName());
		}
		getSchemeDto.setRequierdDocs(docs);
		getSchemeDto.setMinAge(sc.getSchemeDetail().getMinAge());
		getSchemeDto.setMinAmount(sc.getSchemeDetail().getMinAmount());
		getSchemeDto.setMinDuration(sc.getSchemeDetail().getMinInvestmentTime());
		getSchemeDto.setSchemeImage(sc.getSchemeDetail().getSchemeImage());
		getSchemeDto.setInstallmentCommRatio(sc.getSchemeDetail().getInstallmentCommRatio());
		getSchemeDto.setProfitRatio(sc.getSchemeDetail().getProfitRatio());
		getSchemeDto.setRegistrationCommRatio(sc.getSchemeDetail().getRegistrationCommRatio());
		
		return getSchemeDto;
		
	}

	
	
}
