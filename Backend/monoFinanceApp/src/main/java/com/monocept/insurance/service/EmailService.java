package com.monocept.insurance.service;

import com.monocept.insurance.dto.EmailSenderDto;
import com.monocept.insurance.dto.Message;

public interface EmailService {

	Message sendSimpleEmail(EmailSenderDto emailSenderDto);
	
}
