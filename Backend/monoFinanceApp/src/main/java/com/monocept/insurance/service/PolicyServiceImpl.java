package com.monocept.insurance.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.monocept.insurance.dto.AccountDto;
import com.monocept.insurance.dto.CustomerGetDto;
import com.monocept.insurance.dto.CustomerPostDto;
import com.monocept.insurance.dto.DocumentDto;
import com.monocept.insurance.dto.GetPolicyDto;
import com.monocept.insurance.dto.Message;
import com.monocept.insurance.dto.PaymentDto;
import com.monocept.insurance.dto.PaymentStatus;
import com.monocept.insurance.dto.PolicyClaimDto;
import com.monocept.insurance.dto.PostPolicyDto;
import com.monocept.insurance.entity.Agent;
import com.monocept.insurance.entity.Claim;
import com.monocept.insurance.entity.ClaimStatus;
import com.monocept.insurance.entity.Commission;
import com.monocept.insurance.entity.CommissionType;
import com.monocept.insurance.entity.Customer;
import com.monocept.insurance.entity.DocumentStatus;
import com.monocept.insurance.entity.InsurancePolicy;
import com.monocept.insurance.entity.InsuranceScheme;
import com.monocept.insurance.entity.Login;
import com.monocept.insurance.entity.Nominee;
import com.monocept.insurance.entity.Payment;
import com.monocept.insurance.entity.PaymentType;
import com.monocept.insurance.entity.PolicyStatus;
import com.monocept.insurance.entity.PremiumType;
import com.monocept.insurance.entity.SubmittedDocument;
import com.monocept.insurance.exception.InsuranceException;
import com.monocept.insurance.mapper.PolicyMapper;
import com.monocept.insurance.repository.AgentRepository;
import com.monocept.insurance.repository.CustomerRepository;
import com.monocept.insurance.repository.InsuranceSchemeRepository;
import com.monocept.insurance.repository.LoginRepository;
import com.monocept.insurance.repository.PaymentRepository;
import com.monocept.insurance.repository.PolicyRepository;

@Service
public class PolicyServiceImpl implements PolicyService {

		@Autowired
		private CustomerRepository customerRepository;
		@Autowired
		private InsuranceSchemeRepository insuranceSchemeRepository;
		@Autowired
		private CustomerService customerService;
		@Autowired
		private AgentRepository agentRepository;
		@Autowired
		private PaymentRepository paymentRepository;
		@Autowired
		private PolicyRepository policyRepository;


		@Override
		public Message savePolicy(PostPolicyDto postPolicyDto) {
			
			Customer customerDb=null;
			List<Customer>log = customerRepository.findAll();
			for(Customer lg:log)
			{
				if(postPolicyDto.getUsername().equals(lg.getLogin().getUsername()))
				{
					customerDb= lg ;
				}
			}


			if (customerDb==null) {
				
				throw new InsuranceException("Customer Not Found");
			}

			Optional<InsuranceScheme> insuranceSchemeDb = insuranceSchemeRepository.findById(postPolicyDto.getSchemeId());

			if (!insuranceSchemeDb.isPresent()) {
				throw new InsuranceException("Scheme Not Found");
			}

			InsuranceScheme insuranceScheme = insuranceSchemeDb.get();

			Agent agent = null;

			if (postPolicyDto.getAgentId() != 0) {

				Optional<Agent> agentDb = agentRepository.findById(postPolicyDto.getAgentId());

				if (agentDb.isPresent()) {
					agent = agentDb.get();
					agent.setTotalCommission(agent.getTotalCommission()+(postPolicyDto.getInvestMent()*insuranceScheme.getSchemeDetail().getRegistrationCommRatio())/100);
				}

			}
			

			InsurancePolicy insurancePolicy = new InsurancePolicy();
			
			int premiumTime=0;

			if (postPolicyDto.getPremiumType() == 12) {
				insurancePolicy.setPremiumType(PremiumType.MONTHLY);
				premiumTime=1;
			} else if (postPolicyDto.getPremiumType() == 2) {
				insurancePolicy.setPremiumType(PremiumType.HALF_YEARLY);
				premiumTime=6;
			} else if (postPolicyDto.getPremiumType() == 4) {
				insurancePolicy.setPremiumType(PremiumType.QUARTERLY);
				premiumTime=3;
			} else if (postPolicyDto.getPremiumType() == 1) {
				insurancePolicy.setPremiumType(PremiumType.YEARLY);
				premiumTime=12;
			} else {
				throw new InsuranceException("Premium type not matched");
			}

			double premiumAmount = (postPolicyDto.getInvestMent())
					/ (postPolicyDto.getDuration() * postPolicyDto.getPremiumType());
			insurancePolicy.setPremiumAmount(premiumAmount);

			double sumAssured = postPolicyDto.getInvestMent()
					+ (postPolicyDto.getInvestMent() * insuranceScheme.getSchemeDetail().getProfitRatio()) / 100;
			insurancePolicy.setSumAssured(sumAssured);


			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, postPolicyDto.getDuration());

			Date maturityDate = calendar.getTime();
			

			insurancePolicy.setMaturityDate(maturityDate);

			insurancePolicy.setInsuranceScheme(insuranceScheme);

			insurancePolicy.setAgent(agent);

			List<Nominee> nominees = new ArrayList<>();

			for (int i = 0; i < postPolicyDto.getNominees().size(); i++) {

				Nominee nominee = new Nominee();
				nominee.setNomineeName(postPolicyDto.getNominees().get(i).getNomineeName());
				nominee.setRelationship(postPolicyDto.getNominees().get(i).getNomineeRelation());
				nominees.add(nominee);

			}

			insurancePolicy.setNominees(nominees);

			List<Payment> paylist = new ArrayList<>();
			double payments = Math.ceil(postPolicyDto.getInvestMent() / premiumAmount);
			Calendar cal = Calendar.getInstance();
			for (int i = 0; i < payments; i++) {
				Payment payment = new Payment();
				
				double roundedamount = Math.round(premiumAmount * 100.0) / 100.0;
				payment.setAmount(roundedamount);
				if (i != 0) {
					cal.add(Calendar.MONTH, premiumTime);
					Date d = cal.getTime();
					payment.setPaymentDate(d);
				}
				else {
					cal.add(Calendar.MONTH, 0);
					Date d = cal.getTime();
					payment.setPaymentDate(d);
				}
				paylist.add(payment);
			}
			
			insurancePolicy.setPayments(paylist);
			insurancePolicy.setStatus(PolicyStatus.PENDING);
			
			Set<SubmittedDocument>documents=new HashSet();
			insurancePolicy.setStatus(PolicyStatus.PENDING);
			for(DocumentDto documentDto: postPolicyDto.getDocs()) {
				SubmittedDocument document= new SubmittedDocument();
				document.setDocumentName(documentDto.getDocumentName());
				document.setDocumentImage(documentDto.getDocumentImage());
				document.setDocumentStatus(DocumentStatus.PENDING);
				documents.add(document);
				
			}
			
			insurancePolicy.setSubmittedDocuments(documents);
			
			customerDb.getPolicies().add(insurancePolicy);
			
			customerRepository.save(customerDb);

			return new Message(HttpStatus.OK,"Policy successfully added to "+customerDb.getUserDetails().getFirstName());
		}

		@Override
		public List<GetPolicyDto> getPolices(String username) {
			
			CustomerGetDto customerGetDto = customerService.getcustomerByUsername(username);

			Optional<Customer> customerDb = customerRepository.findById(customerGetDto.getId());

			Customer customer = new Customer();

			if (customerDb.isPresent()) {
				customer = customerDb.get();
			} else {
				throw new InsuranceException("Customer Not Found");
			}
			
			List<InsurancePolicy>policies=customer.getPolicies();
			
			List<GetPolicyDto>policyList=new ArrayList<>();
			
			for(InsurancePolicy insurancePolicy:policies) {
				
				GetPolicyDto getPolicyDto=new GetPolicyDto();
				
				getPolicyDto.setDocuments(insurancePolicy.getSubmittedDocuments());
				getPolicyDto.setPayments(insurancePolicy.getPayments());
				getPolicyDto.setNominees(insurancePolicy.getNominees());
				getPolicyDto.setInvestmentAmount(insurancePolicy.getPremiumAmount()*insurancePolicy.getPayments().size());
				getPolicyDto.setIssueDate(insurancePolicy.getIssueDate());
				getPolicyDto.setMaturityDate(insurancePolicy.getMaturityDate());
				getPolicyDto.setPolicyId(insurancePolicy.getPolicyNo());
				getPolicyDto.setPolicyStatus(insurancePolicy.getStatus());
				getPolicyDto.setPremiumAmount(insurancePolicy.getPremiumAmount());
				getPolicyDto.setProfitAmount(insurancePolicy.getSumAssured()-getPolicyDto.getInvestmentAmount());
				getPolicyDto.setPremiumType(insurancePolicy.getPremiumType());
				getPolicyDto.setScheme(insurancePolicy.getInsuranceScheme());
				getPolicyDto.setSumAssured(insurancePolicy.getSumAssured());
				
				policyList.add(getPolicyDto);
			}
			
			return policyList;
		}

		@Override
		public Message payment(PaymentDto paymentDto) {
			CustomerGetDto customerPostDto = customerService.getcustomerByUsername(paymentDto.getUsername());

			Optional<Customer> customerDb = customerRepository.findById(customerPostDto.getId());

			Customer customer = new Customer();

			if (customerDb.isPresent()) {
				customer = customerDb.get();
			} else {
				throw new InsuranceException("Customer Not Found");
			}

			List<InsurancePolicy> policies = customer.getPolicies();

			InsurancePolicy insurancePolicy = null;

			for (InsurancePolicy insurance : policies) {
				if (insurance.getPolicyNo() == paymentDto.getPolicyId()) {
					insurancePolicy = insurance;
				}
			}

			if (insurancePolicy == null) {
				throw new InsuranceException("Policy not found");
			}

			if (insurancePolicy.getStatus() != PolicyStatus.ACTIVE) {
				throw new InsuranceException("Policy is not active");
			}

			List<Payment> payments = insurancePolicy.getPayments();

			Payment paymentDb = null;

			for (Payment payment : payments) {
				
				System.out.println(payment);

				if (payment.getPaymentId() == paymentDto.getPaymentId()) {
					paymentDb = payment;
				}

			}
			if(paymentDb.getAmount()!=paymentDto.getAmount()) {
				System.out.println(paymentDb.getAmount());
				System.out.println(paymentDto.getAmount());
				throw new InsuranceException("Payment Not Matched");
			}
			if (paymentDb == null) {
				throw new InsuranceException("Payment Not Found");
			}
			if (paymentDb.getPaymentStatus().equals(PaymentStatus.PAID)) {
				throw new InsuranceException("Premium already paid!");
			}
            
			paymentDb.setAmount(paymentDto.getAmount());
			paymentDb.setCardNumber(paymentDto.getCardNumber());
			paymentDb.setCvv(paymentDto.getCvv());
			paymentDb.setExpiry(paymentDto.getExpiry());
			paymentDb.setPaymentStatus(PaymentStatus.PAID);
			paymentDb.setPaymentType(PaymentType.valueOf(paymentDto.getPaymentType()));
			double total = Math.round(paymentDb.getTotalPayment()* 100.0) / 100.0;
			double premium = Math.round(paymentDto.getAmount()* 100.0) / 100.0;
			paymentDb.setTotalPayment(total+premium);
	

			Agent agent = insurancePolicy.getAgent();

			double commition = insurancePolicy.getInsuranceScheme().getSchemeDetail().getInstallmentCommRatio();

			if (agent != null) {
				Commission commission=new Commission();
				commission.setAmount((paymentDto.getAmount()*commition)/100);
				commission.setCommisionType(CommissionType.INSTALMENT.toString());
				double a = Math.round(agent.getTotalCommission()* 100.0) / 100.0;
				double b = Math.round(commission.getAmount()* 100.0) / 100.0;
				agent.setTotalCommission(a+b);
				agent.getCommissions().add(commission);
			}
               insurancePolicy.setAgent(agent);
		       policyRepository.save(insurancePolicy);

			return new Message(HttpStatus.OK, "payment success");
		}

		
		

		@Override
		public List<GetPolicyDto> getPendingPolices() {
			List<InsurancePolicy> policies = policyRepository.findAll();

			List<GetPolicyDto> policyList = new ArrayList<>();

			for (InsurancePolicy insurancePolicy : policies) {

				if (insurancePolicy.getStatus() != PolicyStatus.PENDING) {
					continue;
				}

				GetPolicyDto getPolicyDto = new GetPolicyDto();

				getPolicyDto.setDocuments(insurancePolicy.getSubmittedDocuments());
				getPolicyDto.setPayments(insurancePolicy.getPayments());
				getPolicyDto.setNominees(insurancePolicy.getNominees());
				getPolicyDto.setInvestmentAmount(insurancePolicy.getPremiumAmount() * insurancePolicy.getPayments().size());
				getPolicyDto.setIssueDate(insurancePolicy.getIssueDate());
				getPolicyDto.setMaturityDate(insurancePolicy.getMaturityDate());
				getPolicyDto.setPolicyId(insurancePolicy.getPolicyNo());
				getPolicyDto.setPolicyStatus(insurancePolicy.getStatus());
				getPolicyDto.setPremiumAmount(insurancePolicy.getPremiumAmount());
				getPolicyDto.setProfitAmount(insurancePolicy.getSumAssured() - getPolicyDto.getInvestmentAmount());
				getPolicyDto.setPremiumType(insurancePolicy.getPremiumType());
				getPolicyDto.setScheme(insurancePolicy.getInsuranceScheme());
				getPolicyDto.setSumAssured(insurancePolicy.getSumAssured());

				policyList.add(getPolicyDto);
				
			}
			if(policyList.isEmpty())
				throw new InsuranceException("No policy pending!");
			return policyList;
		}

		@Override
		public List<Payment> getpayments(Long policyId) {
			Optional<InsurancePolicy> policy = policyRepository.findById(policyId);
			if(!policy.isPresent())
			{
				throw new InsuranceException("policy not exists!");
			}
			InsurancePolicy p = policy.get();
			
			return p.getPayments();
		}

		@Override
		public Message aproovPolicy(Long policyId) {
			Optional<InsurancePolicy>policy = policyRepository.findById(policyId);
			
			if(!policy.isPresent())
			{
				throw new InsuranceException("Policy not founded!");
			}
			InsurancePolicy p =policy.get();
			
			if(p.getStatus().equals(PolicyStatus.ACTIVE))
			{
				throw new InsuranceException("policy Already aprooved!");
			}
			Set<SubmittedDocument>docs = p.getSubmittedDocuments();
			for(SubmittedDocument d:docs)
			{
				d.setDocumentStatus(DocumentStatus.APPROVED);
			}
			p.setSubmittedDocuments(docs);
			p.setStatus(PolicyStatus.ACTIVE);
			policyRepository.save(p);
			
			
			Message msg = new Message();
			msg.setStatus(HttpStatus.OK);
			msg.setMessage("Policy Aprooved!");
			
			return msg;
		}

		@Override
		public Message rejectPolicy(Long policyId) {
        Optional<InsurancePolicy>policy = policyRepository.findById(policyId);
			
			if(!policy.isPresent())
			{
				throw new InsuranceException("Policy not founded!");
			}
			InsurancePolicy p =policy.get();
			
			if(p.getStatus().equals(PolicyStatus.REJECT))
			{
				throw new InsuranceException("Policy Already Rejected!");
			}
			Set<SubmittedDocument>docs = p.getSubmittedDocuments();
			for(SubmittedDocument d:docs)
			{
				d.setDocumentStatus(DocumentStatus.APPROVED);
			}
			p.setSubmittedDocuments(docs);
			p.setStatus(PolicyStatus.REJECT);
			policyRepository.save(p);
			
			
			Message msg = new Message();
			msg.setStatus(HttpStatus.OK);
			msg.setMessage("Policy Rejected!");
			
			return msg;
		}

		@Override
		public Message policyClaim(PolicyClaimDto policyClaimDto) {
			Optional<InsurancePolicy> insurancePolicyDb=policyRepository.findById(policyClaimDto.getPolicyId());
			
			InsurancePolicy insurancePolicy=null;
			
			if(insurancePolicyDb.isPresent()) {
				insurancePolicy=insurancePolicyDb.get();
			}
			
			if(insurancePolicy==null) {
				throw new InsuranceException("policy not found");
			}
			
			if(insurancePolicy.getStatus()==PolicyStatus.PENDING) {
				throw new InsuranceException("policy not Active");
			}
			
			
			List<Payment>payments=insurancePolicy.getPayments();
			
			double amount=0;
			boolean flag=false;
			
			for(Payment payment:payments) {
				if(payment.getPaymentStatus()==PaymentStatus.PAID) {
					amount+=payment.getAmount();
				}
				else {
					flag=true;
				}
			}
			
			if(insurancePolicy.getSumAssured()<policyClaimDto.getClaimAmount()) {
				throw new InsuranceException("Claim amount must be less than paid amounts");
			}
			
			if(flag) {
				insurancePolicy.setStatus(PolicyStatus.DROP);
				}
			
			if(!flag) {
				insurancePolicy.setStatus(PolicyStatus.COMPLETE);
			}
			
			Claim claim=new Claim();
			
			if(flag)
			claim.setClaimAmount(amount);
			else {
				claim.setClaimAmount(insurancePolicy.getSumAssured());
			}
			claim.setStatus(ClaimStatus.PENDING.toString());
			claim.setBankAccountNumber(policyClaimDto.getBankAccountNumber());
			claim.setBankName(policyClaimDto.getBankName());
			claim.setBranchName(policyClaimDto.getBranchName());
			claim.setIfscCode(policyClaimDto.getIfscCode());
			
			insurancePolicy.setClaims(claim);
			
			policyRepository.save(insurancePolicy);
		
			
			return new Message(HttpStatus.OK,"Policy Claimed");
    	
	}

		@Override
		public Page<AccountDto> getAllAccounts(Pageable pageable) {
			List<Customer>customers = customerRepository.findAll();
			
			List<AccountDto>ac=new ArrayList<>();
			
			for(Customer ct:customers)
			{
				if(ct.getPolicies().size()!=0)
				{
					for(InsurancePolicy p:ct.getPolicies())
					{
						ac.add(PolicyMapper.policyToAccountDto(p,ct));
					}
				}
			}

		        int start = (int) pageable.getOffset();
		        int end = Math.min((start + pageable.getPageSize()), ac.size());
		        Page<AccountDto> allPolicies = new PageImpl<>(ac.subList(start, end), pageable, ac.size());

		        return allPolicies;
		}

		

		
}	


