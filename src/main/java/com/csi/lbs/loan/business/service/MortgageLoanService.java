package com.csi.lbs.loan.business.service;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.model.HeaderModel;
import com.csi.lbs.loan.business.clientmodel.OpeningLoanAccountModel;
import com.csi.lbs.loan.business.clientmodel.MortgageCalculatorModel;
import com.csi.lbs.loan.business.clientmodel.MortgageLoanAmountModel;
import com.csi.lbs.loan.business.clientmodel.MortgageAccountInfoModel;
import com.csi.lbs.loan.business.clientmodel.MortgageLoanApplicationModel;
import com.csi.lbs.loan.business.clientmodel.ReMortgageContractInfo;
import com.csi.lbs.loan.business.clientmodel.RepaymentModel;
import com.csi.lbs.loan.business.clientmodel.TransactionRequestModel;
import com.csi.lbs.loan.business.clientmodel.MortgageAccountNumberModel;
import com.csi.lbs.loan.business.clientmodel.AccountContractNumberModel;
import com.csi.lbs.loan.business.clientmodel.CheckAccountTypeModel;
import com.csi.lbs.loan.business.clientmodel.ContranctDetailModel;
import com.csi.lbs.loan.business.clientmodel.ContractOutstandingModel;
import com.csi.lbs.loan.business.clientmodel.NextRepaymentModel;
import com.csi.lbs.loan.business.clientmodel.TransactionInfoModel;
import com.csi.lbs.loan.business.util.ResultUtil;

public interface MortgageLoanService {

	public Map<String,Object> openingLoanAccount(HeaderModel header, OpeningLoanAccountModel loan, RestTemplate restTemplate) throws Exception;
	
	public ResultUtil<MortgageLoanAmountModel> mortgageLoanCalculator(MortgageCalculatorModel ase) throws Exception;
	
	public ResultUtil<MortgageAccountInfoModel> accountDetailEnquiry(HeaderModel header, MortgageAccountNumberModel ase, RestTemplate restTemplate) throws Exception;
	
	public ResultUtil<ReMortgageContractInfo> mortgageLoanApplication(HeaderModel header, MortgageLoanApplicationModel ase, RestTemplate restTemplate) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ResultUtil mortgageLoanCancellation(HeaderModel header, MortgageAccountNumberModel ase, RestTemplate restTemplate) throws Exception;
	
	public ResultUtil<ContranctDetailModel> repaymentPlan(HeaderModel header, AccountContractNumberModel ase) throws Exception;
	
	public ResultUtil<ContractOutstandingModel> overDueRepaymentEnquiry(HeaderModel header, AccountContractNumberModel ase) throws Exception;
	
	public ResultUtil<NextRepaymentModel> nextRepaymentEnquiry(HeaderModel header, AccountContractNumberModel ase) throws Exception;
	
	public ResultUtil<TransactionInfoModel> transactionEnquiry(HeaderModel header, TransactionRequestModel ase) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ResultUtil repayment(HeaderModel header, RepaymentModel ase, RestTemplate restTemplate) throws Exception;
	
	public Map<String, Object> resetContractDueInfo(RestTemplate restTemplate) throws Exception;	
}