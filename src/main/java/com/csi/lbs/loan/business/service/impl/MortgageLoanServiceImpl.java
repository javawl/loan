package com.csi.lbs.loan.business.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.model.HeaderModel;
import com.csi.lbs.loan.business.clientmodel.AccountContractNumberModel;
import com.csi.lbs.loan.business.clientmodel.ContractNumberModel;
import com.csi.lbs.loan.business.clientmodel.ContractOutstandingModel;
import com.csi.lbs.loan.business.clientmodel.ContranctDetailModel;
import com.csi.lbs.loan.business.clientmodel.LoanStagingModel;
import com.csi.lbs.loan.business.clientmodel.InsertTransactionLogModel;
import com.csi.lbs.loan.business.clientmodel.MortgageAccountInfoModel;
import com.csi.lbs.loan.business.clientmodel.MortgageAccountNumberModel;
import com.csi.lbs.loan.business.clientmodel.MortgageCalculatorModel;
import com.csi.lbs.loan.business.clientmodel.MortgageLoanAmountModel;
import com.csi.lbs.loan.business.clientmodel.MortgageLoanApplicationModel;
import com.csi.lbs.loan.business.clientmodel.DebitAccountSearchModel;
import com.csi.lbs.loan.business.clientmodel.NextRepaymentModel;
import com.csi.lbs.loan.business.clientmodel.OpeningLoanAccountModel;
import com.csi.lbs.loan.business.clientmodel.ReConstractDetailModel;
import com.csi.lbs.loan.business.clientmodel.ReConstractDetailsModel;
import com.csi.lbs.loan.business.clientmodel.DebitAccountModel;
import com.csi.lbs.loan.business.clientmodel.ReCustomerModel;
import com.csi.lbs.loan.business.clientmodel.ReMortgageContractInfo;
import com.csi.lbs.loan.business.clientmodel.RepaymentModel;
import com.csi.lbs.loan.business.clientmodel.TransactionDetailModel;
import com.csi.lbs.loan.business.clientmodel.TransactionInfoModel;
import com.csi.lbs.loan.business.clientmodel.TransactionRequestModel;
import com.csi.lbs.loan.business.clientmodel.FindCustomerModel;
import com.csi.lbs.loan.business.service.MortgageLoanService;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.lbs.loan.business.clientmodel.CheckAccountTypeModel;
import com.csi.lbs.loan.business.exception.DateException;
import com.csi.sbs.common.business.util.DataIsolationUtil;
import com.csi.lbs.loan.business.exception.InsertException;
import com.csi.lbs.loan.business.exception.UpdateException;
import com.csi.lbs.loan.business.exception.CallOtherException;
import com.csi.lbs.loan.business.exception.SearchException;
import com.csi.lbs.loan.business.exception.AcceptException;
import com.csi.lbs.loan.business.exception.NotFoundException;
import com.csi.lbs.loan.business.constant.ExceptionConstant;
import com.csi.lbs.loan.business.exception.OtherException;
import com.csi.lbs.loan.business.constant.PathConstant;
import com.csi.lbs.loan.business.constant.SysConstant;
import com.csi.lbs.loan.business.entity.MortgageLoanAccountEntity;
import com.csi.lbs.loan.business.entity.MortgagePeriodRateEntity;
import com.csi.lbs.loan.business.entity.MortgageLoanContractEntity;
import com.csi.lbs.loan.business.entity.MortgageLoanContractDetailEntity;
import com.csi.lbs.loan.business.entity.MortgageLoanTransactionLogEntity;
import com.csi.lbs.loan.business.util.AvailableNumberUtil;
import com.csi.lbs.loan.business.util.GenerateAccountNumberUtil;
import com.csi.lbs.loan.business.util.LogUtil;
import com.csi.lbs.loan.business.util.PostUtil;
import com.csi.lbs.loan.business.util.ResultUtil;
import com.csi.lbs.loan.business.util.ValidateAccountTypeUtil;
import com.csi.lbs.loan.business.dao.MortgageLoanAccountDao;
import com.csi.lbs.loan.business.dao.MortgagePeriodRateDao;
import com.csi.lbs.loan.business.dao.MortgageLoanContractDao;
import com.csi.lbs.loan.business.dao.MortgageLoanContractDetailDao;
import com.csi.lbs.loan.business.dao.MortgageLoanTransactionLogDao;

@Service("LoanAccountService")
public class MortgageLoanServiceImpl implements MortgageLoanService{

	@Resource
	private RestTemplate restTemplate;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat format3 = new SimpleDateFormat("yyyyMMdd");
	
	@SuppressWarnings("rawtypes")
	@Resource
	private MortgageLoanAccountDao mortgageLoanAccountDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private MortgagePeriodRateDao mortgagePeriodRateDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private MortgageLoanContractDao mortgageLoanContractDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private MortgageLoanContractDetailDao mortgageLoanContractDetailDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private MortgageLoanTransactionLogDao mortgageLoanTransactionLogDao;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> openingLoanAccount(HeaderModel header, OpeningLoanAccountModel loan,
			RestTemplate restTemplate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String relaccountType = loan.getAccountnumber().substring(loan.getAccountnumber().length() - 3,
				loan.getAccountnumber().length());
		// 校验关联账号
		Map<String, Object> map2 = ValidateAccountTypeUtil.checkRelAccountNumber(restTemplate, header, relaccountType,
				loan.getAccountnumber());
		if (map2.get("code").equals(ExceptionConstant.ERROR_CODE201001)) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201001),
					ExceptionConstant.ERROR_CODE201001);
		}
		// 获取账号
		String accountNumber = null;
		String localccy = null;
		Map<String, Object> map3 = GenerateAccountNumberUtil.getAccountNumber(SysConstant.ACCOUNT_TYPE9, header,
				restTemplate);
		if (map3.get("code").equals("1")) {
			accountNumber = map3.get("accountNumber").toString();
			localccy = map3.get("localCCy").toString();
		}
		// model change
		MortgageLoanAccountEntity account = new MortgageLoanAccountEntity();
		account.setAccountnumber(accountNumber);
		account.setRelaccountnumber(loan.getAccountnumber());
		account.setAccountstatus(SysConstant.ACCOUNT_STATE2);
		account.setId(UUIDUtil.generateUUID());
		account.setLastupdateddate(format.parse(format.format(new Date())));
		account.setCustomernumber(header.getCustomerNumber());
		account.setCountrycode(loan.getCountrycode());
		account.setClearingcode(loan.getClearingcode());
		account.setBranchcode(loan.getBranchcode());
		account.setSandboxid(loan.getSandboxid());
		account.setDockerid(loan.getDockerid());
		account.setCurrencycode(localccy);

		mortgageLoanAccountDao.insert(account);

		// 写入日志
		String logstr = "create accountNumber:" + account.getAccountnumber() + " success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		AvailableNumberUtil.availableNumberIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_ACCOUNTNUMBER);
		map.put("msg", SysConstant.CREATE_SUCCESS_TIP);
		map.put("accountNumber", account.getAccountnumber());
		map.put("code", "1");
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResultUtil<MortgageLoanAmountModel> mortgageLoanCalculator(MortgageCalculatorModel ase) throws Exception{
		ResultUtil result = new ResultUtil();
		MortgageLoanAmountModel loanInfo = new MortgageLoanAmountModel();
		LoanStagingModel monthly =  new LoanStagingModel();
		LoanStagingModel fortnightly = new LoanStagingModel();
		//工资
		BigDecimal salary = ase.getMonthlysalary();
		//最大贷款额度
		BigDecimal loanAmount = null;
		//月供还款额度
		double repaymentOfMonth = 0;
		//月利率
		BigDecimal monthRate = null;
		//月供总利息
		int interestByMonth = 0;
		//月供还款期年数
		double periodOfMonth = Double.parseDouble(ase.getLoanPeriod());
		//用供还款期数
		int phaseOfMonth = 0;
		//月供还款总金额
		int totalAmountOfMonth = 0;
		//每两周还一次的每次的还款金额
		double repaymentOfBiweek = 0;
		//两周供总利息
		int interestByBiweek = 0;
		//两周供利率
		BigDecimal fortnightRate = null;
		//两周供还款期
		double periodOfBiweek = 0;
		//两周供还款总金额
		int totalAmountOfBiweek = 0;
		//两周供还款期数
		int phaseOfFortnight = 0;
		
		
		MortgagePeriodRateEntity loanperiod = new MortgagePeriodRateEntity();
		loanperiod.setRepaymentperiod(Integer.parseInt(ase.getLoanPeriod()));
		MortgagePeriodRateEntity rateInfo = (MortgagePeriodRateEntity) mortgagePeriodRateDao.findOne(loanperiod);
		if(rateInfo != null && rateInfo.getRate() != null){
			//loanAmount = salary * rate
			loanAmount = salary.multiply(rateInfo.getRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			
			/* 
			 * 获取贷款月利率
			 */
			String param1 = "{\"apiname\":\"getSystemParameter\"}";
			ResponseEntity<String> result1 = restTemplate.postForEntity(PathConstant.SERVICE_INTERNAL_URL,
					PostUtil.getRequestEntity(param1), String.class);
			if (result1.getStatusCodeValue() != 200) {
				throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),
						ExceptionConstant.ERROR_CODE500002);
			}
			String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
			// 调用系统参数服务接口
			String param2 = "{\"item\":\"MortgageMR\"}";
			ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
					String.class);
			if (result2.getStatusCodeValue() != 200) {
				throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),
						ExceptionConstant.ERROR_CODE500003);
			}
			
			JSONArray restr = JsonProcess.changeToJSONArray(result2.getBody());
			String mortgageMR = JsonProcess.returnValue(JsonProcess.changeToJSONObject(restr.get(0).toString()), "value");
			
			
			/*
			 * 计算月供的相关信息
			 */
			monthRate = new BigDecimal(mortgageMR);
			phaseOfMonth = (int) periodOfMonth * 12;
			repaymentOfMonth = Math.round(loanAmount.intValue()*monthRate.doubleValue()*Math.pow((1+ monthRate.doubleValue()), phaseOfMonth)/(Math.pow((1+ monthRate.doubleValue()), phaseOfMonth)-1)*100)/100;
			interestByMonth = (int) (phaseOfMonth * repaymentOfMonth - loanAmount.intValue());
			totalAmountOfMonth = loanAmount.intValue() + interestByMonth;
			
			monthly.setLoanPeriod(periodOfMonth);
			monthly.setRepaymentCycle("M");
			monthly.setRepaymentPerPhase(repaymentOfMonth);
			monthly.setTotalInterestPayable(interestByMonth);
			monthly.setTotalRepaymentAmount(totalAmountOfMonth);
			monthly.setTotalPhase(phaseOfMonth);
			
			/*
			 * 计算两周供相关信息
			 */
			repaymentOfBiweek = Math.round(repaymentOfMonth*100/2)/100;
			fortnightRate = monthRate.multiply(new BigDecimal("12")).divide(new BigDecimal("26"), 8, BigDecimal.ROUND_HALF_UP);
			phaseOfFortnight = (int) Math.round(Math.log(repaymentOfBiweek/(repaymentOfBiweek-loanAmount.doubleValue()*fortnightRate.doubleValue()))/Math.log(1+fortnightRate.doubleValue()));
			interestByBiweek = (int) (phaseOfFortnight * repaymentOfBiweek - loanAmount.intValue());
			periodOfBiweek = Math.ceil((double) phaseOfFortnight*10/26)/10;
			totalAmountOfBiweek = interestByBiweek + loanAmount.intValue();
			
			fortnightly.setLoanPeriod(periodOfBiweek);
			fortnightly.setRepaymentCycle("B");
			fortnightly.setRepaymentPerPhase(repaymentOfBiweek);
			fortnightly.setTotalInterestPayable(interestByBiweek);
			fortnightly.setTotalPhase(phaseOfFortnight);
			fortnightly.setTotalRepaymentAmount(totalAmountOfBiweek);
			
			loanInfo.setMaxLoanAmount(loanAmount);
			loanInfo.setRepaymentByMonth(monthly);
			loanInfo.setRepaymentByFortnight(fortnightly);
			
			result.setCode("1");
			result.setMsg("Transaction Accepted");
			result.setData(loanInfo);
		}
		
		return result;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResultUtil<MortgageAccountInfoModel> accountDetailEnquiry(HeaderModel header, MortgageAccountNumberModel ase, RestTemplate restTemplate)
			throws Exception {
		ResultUtil<MortgageAccountInfoModel> result = new ResultUtil();
		
		MortgageAccountInfoModel accountDetail =  new MortgageAccountInfoModel();
		
		/*
		 * 获取loan account infomation
		 */
		MortgageLoanAccountEntity accountInfo =  new MortgageLoanAccountEntity();
		accountInfo.setAccountnumber(ase.getAccountnumber());;
		accountInfo.setCustomernumber(header.getCustomerNumber());
		accountInfo = (MortgageLoanAccountEntity) DataIsolationUtil.condition(header, accountInfo);
		MortgageLoanAccountEntity account = (MortgageLoanAccountEntity) mortgageLoanAccountDao.findOne(accountInfo);
		if(account == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		
		if(!account.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		/*
		 * 获取constract number
		 */
		MortgageLoanContractEntity contractsParam = new MortgageLoanContractEntity();
		contractsParam.setAccountnumber(ase.getAccountnumber());
		List<MortgageLoanContractEntity> contracts =  mortgageLoanContractDao.findMany(contractsParam);
		List<ContractNumberModel> list = new ArrayList<ContractNumberModel>();
		if(contracts != null && contracts.size() > 0){
			for(int i=0; i < contracts.size(); i++){
				ContractNumberModel contractInfo =  new ContractNumberModel();
				contractInfo.setContractNumber(contracts.get(i).getContractnumber());
				list.add(contractInfo);
			}
		}else{
			list = null;
		}
		
		/*
		 * 获取customer infomation
		 */
		FindCustomerModel findCustomer = new FindCustomerModel();
		findCustomer.setCustomerNumber(account.getCustomernumber());
		String param = JsonProcess.changeEntityTOJSON(findCustomer);
		ResponseEntity<String> customerRes = restTemplate.postForEntity(PathConstant.GET_CUSTOMER, PostUtil.getRequestEntity(param),
				String.class);
		if (customerRes.getStatusCodeValue() != 200) {
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		JSONObject str1 = XmlToJsonUtil.xmlToJson(customerRes.getBody());
		String str2 = JsonProcess.returnValue(str1, "ResultUtil");
		ResultUtil str3 = JSON.parseObject(str2, ResultUtil.class);
		if (str3.getCode().equals("0")) {
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404002),
					ExceptionConstant.ERROR_CODE404002);
		}
		List<ReCustomerModel> customerInfo = new ArrayList<ReCustomerModel>();
		if(str3.getData().toString().startsWith("[")){
			customerInfo = JSON.parseArray(str3.getData().toString(), ReCustomerModel.class);
		}
		//返回一条
		if(str3.getData().toString().startsWith("{")){
			customerInfo.add(JSON.parseObject(str3.getData().toString(), ReCustomerModel.class));
		}
		
		accountDetail.setAccountnumber(account.getAccountnumber());
		accountDetail.setAccountstatus(account.getAccountstatus());
		accountDetail.setCurrencycode(account.getCurrencycode());
		accountDetail.setRepaymentaccountnumber(account.getRelaccountnumber());
		accountDetail.setAssociatedContract(list);
		accountDetail.setCustomerInfo(customerInfo.get(0));
		
		
		// 写入日志
		String logstr = "get mortgage loan " + ase.getAccountnumber() + " detail success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_QUERY, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		result.setCode("1");
		result.setMsg("Transaction Accepted");
		result.setData(accountDetail);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil<ReMortgageContractInfo> mortgageLoanApplication(HeaderModel header,
			MortgageLoanApplicationModel ase, RestTemplate restTemplate) throws Exception {
		
		ResultUtil<ReMortgageContractInfo> result =  new ResultUtil();
		
		ReMortgageContractInfo data = new ReMortgageContractInfo();
		DebitAccountModel debitAccount = new DebitAccountModel();
		
		String duedate = null;
		
		//最大贷款额度
		BigDecimal maxLoanAmount = null;
		//贷款额度
		BigDecimal borrowingNeeds = ase.getBorringneeds();
		//月供还款额度
		double repaymentOfMonth = 0;
		//月利率
		BigDecimal monthRate = null;
		//月供总利息
		int interestByMonth = 0;
		//月供还款期年数
		double periodOfMonth = Double.parseDouble(ase.getLoanPeriod());
		//用供还款期数
		int phaseOfMonth = 0;
		//月供还款总金额
//		int totalAmountOfMonth = 0;
		//每两周还一次的每次的还款金额
		double repaymentOfBiweek = 0;
		//两周供总利息
		int interestByBiweek = 0;
		//两周供利率
		BigDecimal fortnightRate = null;
		//两周供还款期
		double periodOfBiweek = 0;
		//两周供还款总金额
//		int totalAmountOfBiweek = 0;
		//两周供还款期数
		int phaseOfFortnight = 0;
		//debit account账户余额
		BigDecimal localBalance =null;
		//新debit account账户余额
		BigDecimal newLocalBalance =null;
		
		if(ase.getBorringneeds().compareTo(new BigDecimal("500")) < 0){
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500008),
					ExceptionConstant.ERROR_CODE500008);
		}
		
		MortgageLoanAccountEntity account =  new MortgageLoanAccountEntity();
		account.setAccountnumber(ase.getAccountnumber());
		account.setCustomernumber(header.getCustomerNumber());
		
		account = (MortgageLoanAccountEntity) DataIsolationUtil.condition(header, account);
		
		MortgageLoanAccountEntity accountInfo = (MortgageLoanAccountEntity) mortgageLoanAccountDao.findOne(account);
		if(accountInfo == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		if(!accountInfo.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),
					ExceptionConstant.ERROR_CODE202001);
		}
		
		/*
		 * 获取debit account信息
		 */
		String debitAccountNum = ase.getDebitaccountnumber();
		String debitType = debitAccountNum.substring(debitAccountNum.length() - 3);
		if(debitType.equals(SysConstant.ACCOUNT_TYPE1) == false && debitType.equals(SysConstant.ACCOUNT_TYPE2) == false){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201001),ExceptionConstant.ERROR_CODE201001);
		}
		
		//获取API accountSearch的内网地址
        String path2 = getInternalUrl("accountSearch");
        if(path2.length() == 0){
        	throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
        
        DebitAccountSearchModel debitRequest = new DebitAccountSearchModel();
		debitRequest.setAccountNumber(ase.getDebitaccountnumber());
		debitRequest.setBranchCode(header.getBranchCode());
		debitRequest.setClearingCode(header.getClearingCode());
		debitRequest.setCountryCode(header.getCountryCode());
		debitRequest.setCustomerNumber(header.getCustomerNumber());
		debitRequest.setDockerid(header.getDockerId());
		debitRequest.setSandboxid(header.getSandBoxId());
		
		if(debitRequest.getDockerid() == null){
			debitRequest.setDockerid("");
		}
		if(debitRequest.getSandboxid() == null){
			debitRequest.setSandboxid("");
		}
		
		//根据 debitAccountNum
		String debitAccountParam = JsonProcess.changeEntityTOJSON(debitRequest);
        
		String debitAccountRes = getResponse(path2,debitAccountParam);
		
		if(debitAccountRes.length() == 0){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),ExceptionConstant.ERROR_CODE404004);
		}
		JSONObject transObject = JsonProcess.changeToJSONObject(debitAccountRes);
		String debitAccountInfo = JsonProcess.returnValue(transObject, "account");
		debitAccount = JSON.parseObject(debitAccountInfo, DebitAccountModel.class);
		if(debitAccount.getAccountstatus().equals(SysConstant.ACCOUNT_STATE3)){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201002),ExceptionConstant.ERROR_CODE201002);
		}
		
		localBalance = debitAccount.getBalance();
		
		//判断borrowing needs是否 <= 最大贷款额度
		MortgagePeriodRateEntity loanperiod = new MortgagePeriodRateEntity();
		loanperiod.setRepaymentperiod(Integer.parseInt(ase.getLoanPeriod()));
		MortgagePeriodRateEntity rateInfo = (MortgagePeriodRateEntity) mortgagePeriodRateDao.findOne(loanperiod);
		if(rateInfo != null && rateInfo.getRate() != null){
			//maxLoanAmount = salary * rate
			maxLoanAmount = ase.getMonthlysalary().multiply(rateInfo.getRate()).setScale(0, BigDecimal.ROUND_HALF_UP);
			if(borrowingNeeds.compareTo(maxLoanAmount) > 0){
				String errorMsg =  "The inputted borrowing needs exceeded your max loan amount: "+ maxLoanAmount +", please try again";
				throw new AcceptException(errorMsg, ExceptionConstant.ERROR_CODE202003);
			}
		}
		
		/*
		 * 查询是否有逾期的合同
		 */
		MortgageLoanContractEntity contractsParam = new MortgageLoanContractEntity();
		contractsParam.setAccountnumber(ase.getAccountnumber());
		contractsParam.setContractstatus("1");
		List<MortgageLoanContractEntity> contractsList = mortgageLoanContractDao.findMany(contractsParam);
		if(contractsList != null && contractsList.size() > 0){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500004),
					ExceptionConstant.ERROR_CODE500004);
		}
		
		/* 
		 * 获取贷款月利率及deal number
		 */
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(PathConstant.SERVICE_INTERNAL_URL,
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),
					ExceptionConstant.ERROR_CODE500002);
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
		// 调用系统参数服务接口
		String param2 = "{\"item\":\"MortgageMR,NextAvailableMorgLoanNumber,NextAvailableMorDealNumber\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),
					ExceptionConstant.ERROR_CODE500003);
		}
		
		// 返回数据处理
		String mortgageMR = "";
		String contractnumber = "";
		String dealnumber = "";
		JSONObject jsonObject1 = null;
		String revalue = null;
		String temp = null;
		for (int i = 0; i < JsonProcess.changeToJSONArray(result2.getBody()).size(); i++) {
			jsonObject1 = JsonProcess
					.changeToJSONObject(JsonProcess.changeToJSONArray(result2.getBody()).get(i).toString());
			revalue = JsonProcess.returnValue(jsonObject1, "item");
			temp = JsonProcess.returnValue(jsonObject1, "value");
			if (revalue.equals("MortgageMR")) {
				mortgageMR = temp;
			}
			if(revalue.equals("NextAvailableMorgLoanNumber")){
				contractnumber = temp;
			}
			if(revalue.equals("NextAvailableMorDealNumber")){
				dealnumber = format3.format(new Date()) + temp;
			}
		}
		
		/*
		 * 计算月供的相关信息
		 */
		monthRate = new BigDecimal(mortgageMR);
		phaseOfMonth = (int) periodOfMonth * 12;
		repaymentOfMonth = Math.round(borrowingNeeds.intValue()*monthRate.doubleValue()*Math.pow((1+ monthRate.doubleValue()), phaseOfMonth)/(Math.pow((1+ monthRate.doubleValue()), phaseOfMonth)-1)*100)/100;
		interestByMonth = (int) (phaseOfMonth * repaymentOfMonth - borrowingNeeds.intValue());
		
		/*
		 * 计算两周供相关信息
		 */
		repaymentOfBiweek = Math.round(repaymentOfMonth*100/2)/100;
		fortnightRate = monthRate.multiply(new BigDecimal("12")).divide(new BigDecimal("26"), 8, BigDecimal.ROUND_HALF_UP);
		phaseOfFortnight = (int) Math.round(Math.log(repaymentOfBiweek/(repaymentOfBiweek-borrowingNeeds.doubleValue()*fortnightRate.doubleValue()))/Math.log(1+fortnightRate.doubleValue()));
		interestByBiweek = (int) (phaseOfFortnight * repaymentOfBiweek - borrowingNeeds.intValue());
		periodOfBiweek = Math.ceil((double) phaseOfFortnight*10/26)/10;

		/*
		 * 插入合同信息
		 */
		MortgageLoanContractEntity newContract = new MortgageLoanContractEntity();
		newContract.setAccountnumber(ase.getAccountnumber());
		newContract.setContractnumber(contractnumber);
		newContract.setContractstatus("0");
		newContract.setLoanmonthlyrate(monthRate);
		newContract.setCurrencycode(accountInfo.getCurrencycode());
		newContract.setId(UUIDUtil.generateUUID());
		newContract.setLoanamount(borrowingNeeds);
		newContract.setRepaymentcycle(ase.getRepaymentCycle());
		newContract.setRepaymentaccountnumber(ase.getDebitaccountnumber());
		newContract.setContracttime(format.parse(format.format(new Date())));
		
		if(ase.getRepaymentCycle().equals("M")){
			newContract.setInterestamount(new BigDecimal(interestByMonth));
			newContract.setTotalphase(phaseOfMonth);
			newContract.setRepaymentperiod(new BigDecimal(periodOfMonth));
		}else if(ase.getRepaymentCycle().equals("B")){
			newContract.setInterestamount(new BigDecimal(interestByBiweek));
			newContract.setTotalphase(phaseOfFortnight);
			newContract.setRepaymentperiod(new BigDecimal(periodOfBiweek));
		}
		mortgageLoanContractDao.insert(newContract);
		
		/*
		 * 计算每期的利息和本金
		 */
		
		for(int i=1; i <= newContract.getTotalphase(); i++ ){
			MortgageLoanContractDetailEntity detail = new MortgageLoanContractDetailEntity();
			detail.setAccountnumber(newContract.getAccountnumber());
			detail.setContractnumber(newContract.getContractnumber());
			detail.setCurrencycode(newContract.getCurrencycode());
			detail.setId(UUIDUtil.generateUUID());
			detail.setPenaltyamount(new BigDecimal("0"));
			detail.setPhaseno(i+"");
			detail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS1);
			detail.setRepaymentcycle(newContract.getRepaymentcycle());
			
			BigDecimal loanamountperphase = null;
			BigDecimal loaninterestperphase = null;
			BigDecimal paymentamountperphase = null;
			Calendar cal = Calendar.getInstance();
			if(ase.getRepaymentCycle().equals("M")){
				paymentamountperphase = new BigDecimal(repaymentOfMonth);
				loaninterestperphase = new BigDecimal((borrowingNeeds.intValue()*monthRate.doubleValue() - repaymentOfMonth) * Math.pow(1 + monthRate.doubleValue(), i-1) + repaymentOfMonth).setScale(2, BigDecimal.ROUND_HALF_UP);
				loanamountperphase = paymentamountperphase.subtract(loaninterestperphase);
				cal.add(Calendar.MONTH, i);
				cal.set(Calendar.DAY_OF_MONTH, 10);
			}else{
				paymentamountperphase = new BigDecimal(repaymentOfBiweek);
				loaninterestperphase = new BigDecimal((borrowingNeeds.intValue()*fortnightRate.doubleValue() - repaymentOfBiweek) * Math.pow(1 + fortnightRate.doubleValue(), i-1) + repaymentOfBiweek).setScale(2, BigDecimal.ROUND_HALF_UP);
				loanamountperphase = paymentamountperphase.subtract(loaninterestperphase);
				cal.add(Calendar.DATE, 2*7*i);
			}
			detail.setLoanamountperphase(loanamountperphase);
			detail.setLoaninterestperphase(loaninterestperphase);
			detail.setRepaymentduedate(format2.parse(format2.format(cal.getTime())));
			detail.setTotalpayment(paymentamountperphase);
			
			if(i == newContract.getTotalphase()){
				duedate = format2.format(cal.getTime());
			}
			
			mortgageLoanContractDetailDao.insert(detail);
		}
		
		/*
		 * 更新debit account
		 */
		newLocalBalance = localBalance.add(ase.getBorringneeds());
		// 调用服务接口地址 获得updateAccountInfo得内网地址
		String path4 = getInternalUrl("updateAccountBalance");
		if(path4.length() == 0){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		//更新 debit account master file 和transaction log file
		String updateDebitAccountParam = "{\"accountnumber\":\""+ debitAccountNum +"\",\"balance\":\""+ newLocalBalance +"\"}";
		String updateDebitAccountRes = getResponse(path4, updateDebitAccountParam);
		if(updateDebitAccountRes.length() == 0){
			throw new UpdateException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500005),ExceptionConstant.ERROR_CODE500005);
		}
		
		/*
		 * 插入debit交易日志
		 */
		InsertTransactionLogModel debitTrans =  new InsertTransactionLogModel();
		debitTrans.setAccountnumber(ase.getDebitaccountnumber());
		debitTrans.setActualbalamt(newLocalBalance);
		debitTrans.setBranchcode(header.getBranchCode());
		debitTrans.setCcy(accountInfo.getCurrencycode());
		debitTrans.setChannel(SysConstant.CHANNEL_TYPE);
		debitTrans.setChannelid(header.getUserID());
		debitTrans.setClearingcode(header.getClearingCode());
		debitTrans.setCountrycode(header.getCountryCode());
		debitTrans.setCrdrmaintind(SysConstant.CR_DR_MAINT_IND_TYPE1);
		debitTrans.setDockerid(header.getDockerId());
		debitTrans.setPreviousbalamt(localBalance);
		debitTrans.setRefaccountnumber(ase.getAccountnumber());
		debitTrans.setSandboxid(header.getSandBoxId());
		debitTrans.setTranamt(ase.getBorringneeds());
		debitTrans.setTrandesc("mortgage loan repayment");
		debitTrans.setTrantype(SysConstant.TRANSACTION_TYPE13);
		
		// 调用服务接口地址 获得transactionLogAdding得内网地址
		String path3 = getInternalUrl("transactionLogAdding");
		if(path3.length() == 0){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		//更新 transaction detail file
		String deTransactionString = JsonProcess.changeEntityTOJSON(debitTrans);
		ResponseEntity<String> deTransactionRes = restTemplate.postForEntity(path3,PostUtil.getRequestEntity(deTransactionString),String.class);
		JSONObject str1 = XmlToJsonUtil.xml2JSON(deTransactionRes.getBody().getBytes());
		String str2 = JsonProcess.returnValue(str1, "ResultUtil");
		ResultUtil<JSONArray> str3 = JSON.parseObject(str2, ResultUtil.class);
		if(str3.getCode().substring(1, str3.getCode().length()-1).equals("0")){
			throw new InsertException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500006),ExceptionConstant.ERROR_CODE500006);
		}
		
		/*
		 * 插入mortgage loan交易记录
		 */
		MortgageLoanTransactionLogEntity loanTrans =  new MortgageLoanTransactionLogEntity();
		loanTrans.setAccountnumber(ase.getAccountnumber());
		loanTrans.setBranchcode(header.getBranchCode());
		loanTrans.setCcy(accountInfo.getCurrencycode());
		loanTrans.setClearingcode(header.getClearingCode());
		loanTrans.setContractnumber(newContract.getContractnumber());
		loanTrans.setCountrycode(header.getCountryCode());
		loanTrans.setDealnumber(dealnumber);
		loanTrans.setDockerid(header.getDockerId());
		loanTrans.setId(UUIDUtil.generateUUID());
		loanTrans.setRelaccountnumber(ase.getDebitaccountnumber());
		loanTrans.setSandboxid(header.getSandBoxId());
		loanTrans.setTranamt(ase.getBorringneeds());
		loanTrans.setTrantime(format.parse(format.format(new Date())));
		loanTrans.setTrantype(SysConstant.TRANS_TYPE1);
		mortgageLoanTransactionLogDao.insert(loanTrans);
		
		/*
		 * 返回信息
		 */
		data.setAccountnumber(ase.getAccountnumber());
		data.setContractNumber(newContract.getContractnumber());
		data.setInstallmentDueDate(duedate);
		data.setInterestamount(newContract.getInterestamount());
		data.setLoanAmount(newContract.getLoanamount());
		data.setLoanperiod(newContract.getRepaymentperiod());
		data.setPenatlyAmount(BigDecimal.ZERO);
		
		// 写入日志
		String logstr = account.getAccountnumber() + "apply mortgage loan success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		AvailableNumberUtil.availableMorgLoanNumber(restTemplate, SysConstant.NEXT_AVAILABLE_MORGLOAN_NUMBER);
		
		AvailableNumberUtil.availableMortDealIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_MORTGAGE_DEALNUMBER);
		
		result.setCode("1");
		result.setMsg("Transaction Accepted");
		result.setData(data);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil mortgageLoanCancellation(HeaderModel header, MortgageAccountNumberModel ase,
			RestTemplate restTemplate) throws Exception {
		
		ResultUtil result = new ResultUtil();
		
		/*
		 * 获取loan account infomation
		 */
		MortgageLoanAccountEntity accountInfo =  new MortgageLoanAccountEntity();
		
		accountInfo.setAccountnumber(ase.getAccountnumber());
		accountInfo.setCustomernumber(header.getCustomerNumber());
		accountInfo = (MortgageLoanAccountEntity) DataIsolationUtil.condition(header, accountInfo);
		MortgageLoanAccountEntity account = (MortgageLoanAccountEntity) mortgageLoanAccountDao.findOne(accountInfo);
		if(account == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		
		if(!account.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		/*
		 * 查询是否有未还款
		 */
		MortgageLoanContractDetailEntity getUnpaidBill = new MortgageLoanContractDetailEntity();
		getUnpaidBill.setAccountnumber(ase.getAccountnumber());
		getUnpaidBill.setRepaymentstatus(SysConstant.REPAYMENT_STATUS1);
		List<MortgageLoanContractDetailEntity> pendingBill = mortgageLoanContractDetailDao.findMany(getUnpaidBill);
		
		getUnpaidBill.setRepaymentstatus(SysConstant.REPAYMENT_STATUS3);
		List<MortgageLoanContractDetailEntity> dueBill = mortgageLoanContractDetailDao.findMany(getUnpaidBill);
		
		getUnpaidBill.setRepaymentstatus(SysConstant.REPAYMENT_STATUS4);
		List<MortgageLoanContractDetailEntity> defaultBill = mortgageLoanContractDetailDao.findMany(getUnpaidBill);
		
		if(pendingBill !=null && pendingBill.size() > 0 || dueBill !=null && dueBill.size() > 0 || defaultBill !=null && defaultBill.size() > 0){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500007),
					ExceptionConstant.ERROR_CODE500007); 
		}
		
		/*
		 * 查询系统时间
		 */
		// 调用服务接口地址
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(PathConstant.SERVICE_INTERNAL_URL,
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),
					ExceptionConstant.ERROR_CODE500003);
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
		// 调用系统参数服务接口
		String param2 = "{\"item\":\"SystemDate\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),
					ExceptionConstant.ERROR_CODE500003);
		}
		JSONArray restr = JsonProcess.changeToJSONArray(result2.getBody());
		String redate = JsonProcess.returnValue(JsonProcess.changeToJSONObject(restr.get(0).toString()), "value");
		
		/*
		 * 注销贷款账户
		 */
		account.setAccountstatus(SysConstant.ACCOUNT_STATE3);
		account.setReportcanceldate(format.parse(redate));
		mortgageLoanAccountDao.update(account);
		
		// 写入日志
		String logstr = "mortgage loan account:" + account.getAccountnumber() + " cancel success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_UPDATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		
		result.setCode("1");
		result.setMsg("Transaction Accepted");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultUtil<ContranctDetailModel> repaymentPlan(HeaderModel header, AccountContractNumberModel ase)
			throws Exception {
		ResultUtil<ContranctDetailModel> result = new ResultUtil<ContranctDetailModel>();
		
		/*
		 * 获取mrotgage loan account infomation
		 */
		MortgageLoanAccountEntity accountInfo =  new MortgageLoanAccountEntity();
		accountInfo.setAccountnumber(ase.getAccountnumber());
		accountInfo.setCustomernumber(header.getCustomerNumber());
		accountInfo = (MortgageLoanAccountEntity) DataIsolationUtil.condition(header, accountInfo);
		MortgageLoanAccountEntity account = (MortgageLoanAccountEntity) mortgageLoanAccountDao.findOne(accountInfo);
		if(account == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		
		if(!account.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		/*
		 * 查询合同是否存在
		 */
		MortgageLoanContractEntity contract = new MortgageLoanContractEntity();
		contract.setAccountnumber(ase.getAccountnumber());
		contract.setContractnumber(ase.getContractnumber());
		
		MortgageLoanContractEntity contractInfo = (MortgageLoanContractEntity) mortgageLoanContractDao.findOne(contract);
		if(contractInfo == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),
					ExceptionConstant.ERROR_CODE404005);
		}
		
		/*
		 * 查询合同详情
		 */
		MortgageLoanContractDetailEntity contractdetail = new MortgageLoanContractDetailEntity();
		contractdetail.setAccountnumber(ase.getAccountnumber());
		contractdetail.setContractnumber(ase.getContractnumber());
		List<MortgageLoanContractDetailEntity> contractdetails = mortgageLoanContractDetailDao.findMany(contractdetail);
		if(contractdetails == null || contractdetails.size() == 0){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),
					ExceptionConstant.ERROR_CODE404005);
		}
		List<ReConstractDetailModel> list = new ArrayList<ReConstractDetailModel>();
		for(int i=0; i<contractdetails.size(); i++){
			MortgageLoanContractDetailEntity detail = contractdetails.get(i); 
			ReConstractDetailModel det = new ReConstractDetailModel();
			det.setCurrencycode(detail.getCurrencycode());
			det.setLoanamountperphase(detail.getLoanamountperphase());
			det.setLoaninterestperphase(detail.getLoaninterestperphase());
			det.setPhaseno(detail.getPhaseno());
			det.setRepaymentduedate(format2.format(detail.getRepaymentduedate()));
			det.setRepaymentstatus(SysConstant.getRSMap().get(detail.getRepaymentstatus()));
			det.setTotalpayment(detail.getLoanamountperphase().add(detail.getLoaninterestperphase()));
			list.add(det);
		}
		
		ContranctDetailModel details = new ContranctDetailModel();
		details.setAccountnumber(ase.getAccountnumber());
		details.setContractnumber(ase.getContractnumber());
		details.setContractPlan(list);
		
		// 写入日志
		String logstr = "get mortgage loan contract:" + ase.getContractnumber() + " plan success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_QUERY, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		
		result.setCode("1");
		result.setMsg("Transaction Acception");
		result.setData(details);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultUtil<ContractOutstandingModel> overDueRepaymentEnquiry(HeaderModel header,
			AccountContractNumberModel ase) throws Exception {
		ResultUtil<ContractOutstandingModel> result = new ResultUtil<ContractOutstandingModel>();
		
		ContractOutstandingModel data = new ContractOutstandingModel();
		/*
		 * 获取mrotgage loan account infomation
		 */
		MortgageLoanAccountEntity accountInfo =  new MortgageLoanAccountEntity();
		accountInfo.setAccountnumber(ase.getAccountnumber());
		accountInfo.setCustomernumber(header.getCustomerNumber());
		accountInfo = (MortgageLoanAccountEntity) DataIsolationUtil.condition(header, accountInfo);
		MortgageLoanAccountEntity account = (MortgageLoanAccountEntity) mortgageLoanAccountDao.findOne(accountInfo);
		if(account == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		
		if(!account.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		/*
		 * 查询合同是否存在
		 */
		MortgageLoanContractEntity contract = new MortgageLoanContractEntity();
		contract.setAccountnumber(ase.getAccountnumber());
		contract.setContractnumber(ase.getContractnumber());
		
		MortgageLoanContractEntity contractInfo = (MortgageLoanContractEntity) mortgageLoanContractDao.findOne(contract);
		if(contractInfo == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),
					ExceptionConstant.ERROR_CODE404005);
		}
		
		/*
		 * 查询合同详情
		 */
		List<ReConstractDetailsModel> list = new ArrayList<ReConstractDetailsModel>();
		
		MortgageLoanContractDetailEntity contractdetail = new MortgageLoanContractDetailEntity();
		contractdetail.setAccountnumber(ase.getAccountnumber());
		contractdetail.setContractnumber(ase.getContractnumber());
		contractdetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS4);
		List<MortgageLoanContractDetailEntity> defaultdetails = mortgageLoanContractDetailDao.findMany(contractdetail);
		
		
		if(defaultdetails != null && defaultdetails.size() > 0){
			for(int i= 0; i < defaultdetails.size(); i++){
				MortgageLoanContractDetailEntity ddt = defaultdetails.get(i);
				ReConstractDetailsModel defaultdetail = new ReConstractDetailsModel();
				defaultdetail.setCurrencycode(ddt.getCurrencycode());
				defaultdetail.setLoanamountperphase(ddt.getLoanamountperphase());
				defaultdetail.setLoaninterestperphase(ddt.getLoaninterestperphase());
				defaultdetail.setPhaseno(ddt.getPhaseno());
				defaultdetail.setRepaymentduedate(format2.format(ddt.getRepaymentduedate()));
				defaultdetail.setRepaymentstatus(SysConstant.getRSMap().get(ddt.getRepaymentstatus()));
				defaultdetail.setPenaltyamount(ddt.getPenaltyamount());
				defaultdetail.setTotalpayment(ddt.getLoanamountperphase().add(ddt.getLoaninterestperphase()).add(ddt.getPenaltyamount()));
				list.add(defaultdetail);
			}
		}
		
		contractdetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS3);
		List<MortgageLoanContractDetailEntity> duedetails = mortgageLoanContractDetailDao.findMany(contractdetail);
		if(duedetails != null && duedetails.size() > 0){
			for(int i= 0; i < duedetails.size(); i++){
				MortgageLoanContractDetailEntity ddt = duedetails.get(i);
				ReConstractDetailsModel defaultdetail = new ReConstractDetailsModel();
				defaultdetail.setCurrencycode(ddt.getCurrencycode());
				defaultdetail.setLoanamountperphase(ddt.getLoanamountperphase());
				defaultdetail.setLoaninterestperphase(ddt.getLoaninterestperphase());
				defaultdetail.setPhaseno(ddt.getPhaseno());
				defaultdetail.setRepaymentduedate(format2.format(ddt.getRepaymentduedate()));
				defaultdetail.setRepaymentstatus(SysConstant.getRSMap().get(ddt.getRepaymentstatus()));
				defaultdetail.setPenaltyamount(ddt.getPenaltyamount());
				defaultdetail.setTotalpayment(ddt.getLoanamountperphase().add(ddt.getLoaninterestperphase()).add(ddt.getPenaltyamount()));
				list.add(defaultdetail);
			}
		}
		
		if(list == null || list.size() == 0){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404007),
					ExceptionConstant.ERROR_CODE404007);
		}
		
		data.setAccountnumber(ase.getAccountnumber());
		data.setContractnumber(ase.getContractnumber());
		data.setOutstandingrepayment(list);
		
		// 写入日志
		String logstr = "get mortgage loan contract:" + ase.getContractnumber() + " overdue repayemnt success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_QUERY, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		
		result.setCode("1");
		result.setMsg("Transaction Accepted");
		result.setData(data);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultUtil<NextRepaymentModel> nextRepaymentEnquiry(HeaderModel header, AccountContractNumberModel ase)
			throws Exception {
		ResultUtil<NextRepaymentModel> result = new ResultUtil<NextRepaymentModel>();
		
		NextRepaymentModel data = new NextRepaymentModel();
		/*
		 * 获取mrotgage loan account infomation
		 */
		MortgageLoanAccountEntity accountInfo =  new MortgageLoanAccountEntity();
		accountInfo.setAccountnumber(ase.getAccountnumber());
		accountInfo.setCustomernumber(header.getCustomerNumber());
		accountInfo = (MortgageLoanAccountEntity) DataIsolationUtil.condition(header, accountInfo);
		MortgageLoanAccountEntity account = (MortgageLoanAccountEntity) mortgageLoanAccountDao.findOne(accountInfo);
		if(account == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		
		if(!account.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		/*
		 * 查询合同是否存在
		 */
		MortgageLoanContractEntity contract = new MortgageLoanContractEntity();
		contract.setAccountnumber(ase.getAccountnumber());
		contract.setContractnumber(ase.getContractnumber());
		
		MortgageLoanContractEntity contractInfo = (MortgageLoanContractEntity) mortgageLoanContractDao.findOne(contract);
		if(contractInfo == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),
					ExceptionConstant.ERROR_CODE404005);
		}
		
		/*
		 * 查询合同详情
		 */
		
		MortgageLoanContractDetailEntity contractdetail = new MortgageLoanContractDetailEntity();
		contractdetail.setAccountnumber(ase.getAccountnumber());
		contractdetail.setContractnumber(ase.getContractnumber());
		contractdetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS1);
		List<MortgageLoanContractDetailEntity> pendingdetails = mortgageLoanContractDetailDao.findMany(contractdetail);

		//没有待付款账单
		if(pendingdetails == null || pendingdetails.size() == 0){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404006),
					ExceptionConstant.ERROR_CODE404006);
		}
		MortgageLoanContractDetailEntity pendindDetail = pendingdetails.get(0);
		data.setCurrencycode(pendindDetail.getCurrencycode());
		data.setLoanamountperphase(pendindDetail.getLoanamountperphase());
		data.setLoaninterestperphase(pendindDetail.getLoaninterestperphase());
		data.setPhaseno(pendindDetail.getPhaseno());
		data.setRepaymentduedate(format2.format(pendindDetail.getRepaymentduedate()));
		data.setRepaymentstatus(SysConstant.getRSMap().get(pendindDetail.getRepaymentstatus()));
		data.setTotalpayment(pendindDetail.getLoanamountperphase().add(pendindDetail.getLoaninterestperphase()).add(pendindDetail.getPenaltyamount()));
		data.setAccountnumber(ase.getAccountnumber());
		data.setContractnumber(ase.getContractnumber());
		
		// 写入日志
		String logstr = "get mortgage loan contract:" + ase.getContractnumber() + " next repayemnt bill success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_QUERY, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		
		result.setCode("1");
		result.setMsg("Transaction Accepted");
		result.setData(data);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultUtil<TransactionInfoModel> transactionEnquiry(HeaderModel header, TransactionRequestModel ase)
			throws Exception {
		// TODO Auto-generated method stub
		ResultUtil<TransactionInfoModel> result = new ResultUtil<TransactionInfoModel>();
		TransactionInfoModel data = new TransactionInfoModel();
		
		/*
		 * 校验日期格式
		 */
		if(!CheckDate(ase.getTransFromDate())){
			throw new DateException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500010),ExceptionConstant.ERROR_CODE500010);
		}
		if(!CheckDate(ase.getTransToDate())){
			throw new DateException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500010),ExceptionConstant.ERROR_CODE500010);
		}
		
		if(format2.parse(ase.getTransFromDate()).getTime() >= format2.parse(ase.getTransToDate()).getTime()){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202008),ExceptionConstant.ERROR_CODE202008);
		}
		
		/*
		 * 获取mrotgage loan account infomation
		 */
		MortgageLoanAccountEntity accountInfo =  new MortgageLoanAccountEntity();
		accountInfo.setAccountnumber(ase.getAccountnumber());
		accountInfo.setCustomernumber(header.getCustomerNumber());
		accountInfo = (MortgageLoanAccountEntity) DataIsolationUtil.condition(header, accountInfo);
		MortgageLoanAccountEntity account = (MortgageLoanAccountEntity) mortgageLoanAccountDao.findOne(accountInfo);
		if(account == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		
		if(!account.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		MortgageLoanTransactionLogEntity request = new MortgageLoanTransactionLogEntity();
		request.setAccountnumber(ase.getAccountnumber());
		request.setContractnumber(ase.getContractnumber());
		request.setTransFromDate(ase.getTransFromDate());
		request.setTransToDate(ase.getTransToDate());
		request = (MortgageLoanTransactionLogEntity) DataIsolationUtil.condition(header, request);
		
		List<MortgageLoanTransactionLogEntity> res = mortgageLoanTransactionLogDao.findMany(request);
		List<TransactionDetailModel> list = new ArrayList<TransactionDetailModel>();
		if(res == null || res.size() == 0){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404003),ExceptionConstant.ERROR_CODE404003);
		}
		
		for(int i=0; i < res.size(); i++){
			MortgageLoanTransactionLogEntity detail = res.get(i);
			TransactionDetailModel detailres = new TransactionDetailModel();
			detailres.setCcy(detail.getCcy());
			detailres.setDealnumber(detail.getDealnumber());
			detailres.setPhaseno(detail.getPhaseno());
			detailres.setRelaccountnumber(detail.getRelaccountnumber());
			detailres.setTranamt(detail.getTranamt());
			detailres.setTrantime(format.format(detail.getTrantime()));
			detailres.setTrantype(detail.getTrantype());
			list.add(detailres);
		}
		
		data.setAccountnumber(ase.getAccountnumber());
		data.setContractnumber(ase.getContractnumber());
		data.setTransactionDetail(list);
		result.setCode("1");
		
		// 写入日志
		String logstr = "get mortgage loan:" + ase.getAccountnumber() + " contract number: " + ase.getContractnumber() + " transaction detail success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_QUERY, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
				
		result.setMsg("Transaction Accepted");
		result.setData(data);
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil repayment(HeaderModel header, RepaymentModel ase, RestTemplate restTemplate) throws Exception {
		ResultUtil result = new ResultUtil();
		
		BigDecimal totalamount = BigDecimal.ZERO;
		BigDecimal localBalance = BigDecimal.ZERO;
		BigDecimal newLocalBalance = BigDecimal.ZERO;
		String phaseNo = "";
		Boolean isRepaymentAllDue = false;
		
		/*
		 * 获取mrotgage loan account infomation
		 */
		MortgageLoanAccountEntity accountInfo =  new MortgageLoanAccountEntity();
		accountInfo.setAccountnumber(ase.getAccountnumber());
		accountInfo.setCustomernumber(header.getCustomerNumber());
		accountInfo = (MortgageLoanAccountEntity) DataIsolationUtil.condition(header, accountInfo);
		MortgageLoanAccountEntity account = (MortgageLoanAccountEntity) mortgageLoanAccountDao.findOne(accountInfo);
		if(account == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		
		if(!account.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		/*
		 * 查询合同是否存在
		 */
		MortgageLoanContractEntity contract = new MortgageLoanContractEntity();
		contract.setAccountnumber(ase.getAccountnumber());
		contract.setContractnumber(ase.getContractnumber());
		contract.setRepaymentaccountnumber(ase.getRepaymentaccountnumber());
		
		MortgageLoanContractEntity contractInfo = (MortgageLoanContractEntity) mortgageLoanContractDao.findOne(contract);
		if(contractInfo == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),
					ExceptionConstant.ERROR_CODE404004);
		}
		
		/*
		 * 查询debit account 
		 */
		String debitAccountNum = ase.getRepaymentaccountnumber();
		String debitType = debitAccountNum.substring(debitAccountNum.length() - 3);
		if(debitType.equals(SysConstant.ACCOUNT_TYPE1) == false && debitType.equals(SysConstant.ACCOUNT_TYPE2) == false){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201001),ExceptionConstant.ERROR_CODE201001);
		}
		
		 String path = getInternalUrl("accountSearch");
	     if(path.length() == 0){
	        throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		
		DebitAccountSearchModel debitRequest = new DebitAccountSearchModel();
		debitRequest.setAccountNumber(ase.getRepaymentaccountnumber());
		debitRequest.setBranchCode(header.getBranchCode());
		debitRequest.setClearingCode(header.getClearingCode());
		debitRequest.setCountryCode(header.getCountryCode());
		debitRequest.setCustomerNumber(header.getCustomerNumber());
		debitRequest.setDockerid(header.getDockerId());
		debitRequest.setSandboxid(header.getSandBoxId());
		
		if(debitRequest.getDockerid() == null){
			debitRequest.setDockerid("");
		}
		if(debitRequest.getSandboxid() == null){
			debitRequest.setSandboxid("");
		}
		
		String debitRequestString = JsonProcess.changeEntityTOJSON(debitRequest);
		
		String debitAccountRes = getResponse(path,debitRequestString);
		
		if(debitAccountRes.length() == 0){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404004),ExceptionConstant.ERROR_CODE404004);
		}
		JSONObject transObject = JsonProcess.changeToJSONObject(debitAccountRes);
		String debitAccountInfo = JsonProcess.returnValue(transObject, "account");
		DebitAccountModel debitAccount = JSON.parseObject(debitAccountInfo, DebitAccountModel.class);
		if(!debitAccount.getAccountstatus().equals(SysConstant.ACCOUNT_STATE2)){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201002),ExceptionConstant.ERROR_CODE201002);
		}
		
		//获取debit account 余额信息
		localBalance = debitAccount.getBalance();
		if(localBalance.compareTo(ase.getRepaymentamount()) < 0 ){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202002),ExceptionConstant.ERROR_CODE202002);
		}
		
		/*
		 * 查询合同并还款
		 */
		
		//所有这期可还款的List 列表
		List<MortgageLoanContractDetailEntity> list = new ArrayList<MortgageLoanContractDetailEntity>();
		MortgageLoanContractDetailEntity contractdetail = new MortgageLoanContractDetailEntity();
		contractdetail.setAccountnumber(ase.getAccountnumber());
		contractdetail.setContractnumber(ase.getContractnumber());
		
		//合同状态为逾期时
		if(contractInfo.getContractstatus().equals("1")){
			
			//查询所有状态为default的账单
			contractdetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS4);
			List<MortgageLoanContractDetailEntity> defaultdetails = mortgageLoanContractDetailDao.findMany(contractdetail);
			if(defaultdetails != null && defaultdetails.size() > 0){
				list.addAll(defaultdetails);
			}
			
			//状态为due的账单
			contractdetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS3);
			List<MortgageLoanContractDetailEntity> duedetails = mortgageLoanContractDetailDao.findMany(contractdetail);
			if(duedetails != null && duedetails.size() > 0){
				list.addAll(duedetails);
			}
		}
		
		//状态为pending的账单
		contractdetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS1);
		List<MortgageLoanContractDetailEntity> pendingdetails = mortgageLoanContractDetailDao.findMany(contractdetail);
		if(pendingdetails != null && pendingdetails.size() > 0){
			list.add(pendingdetails.get(0));
		}
		
		//没有可还款账单时，返回提示信息
		if(list == null || list.size() == 0){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404006),
					ExceptionConstant.ERROR_CODE404006);
		}
		
		for(int i=0; i < list.size(); i++){
			totalamount = totalamount.add(list.get(i).getTotalpayment());
			if(phaseNo.length() == 0){
				phaseNo += list.get(i).getPhaseno();
			}else{
				phaseNo += "," + list.get(i).getPhaseno();
			}
			
			//可还款金额大于输入金额时，拒绝还款
			if(totalamount.compareTo(ase.getRepaymentamount()) > 0){
				throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500009),
						ExceptionConstant.ERROR_CODE500009);
			}

			//输入金额大于所有可还款金额之和时，拒绝还款
			if(i == list.size() && totalamount.compareTo(ase.getRepaymentamount()) < 0){
				throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500009),
						ExceptionConstant.ERROR_CODE500009);
			}
			
			//输入金额等于可某前几次还款金额之和,退出循环
			if(totalamount.compareTo(ase.getRepaymentamount()) == 0){
				if(contractInfo.getContractstatus().equals("1") && list.size() >= 2 && i >= list.size()-2){
					isRepaymentAllDue = true;
				}
				for(int j = 0; j <= i; j++){
					MortgageLoanContractDetailEntity needUpdate = list.get(j);
					needUpdate.setRepaymentstatus(SysConstant.REPAYMENT_STATUS2);
					mortgageLoanContractDetailDao.update(needUpdate);
				}
				break;
			}
		}
		
		/*
		 * 更新debit account
		 */
		newLocalBalance = localBalance.subtract(ase.getRepaymentamount());
		// 调用服务接口地址 获得updateAccountInfo得内网地址
		String path2 = getInternalUrl("updateAccountBalance");
		if(path2.length() == 0){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		//更新 debit account master file 和transaction log file
		String updateDebitAccountParam = "{\"accountnumber\":\""+ debitAccountNum +"\",\"balance\":\""+ newLocalBalance +"\"}";
		String updateDebitAccountRes = getResponse(path2, updateDebitAccountParam);
		if(updateDebitAccountRes.length() == 0){
			throw new UpdateException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500005),ExceptionConstant.ERROR_CODE500005);
		}
		
		//如果还完逾期，修改合同状态
		if(isRepaymentAllDue){
			contractInfo.setContractstatus("0");
			mortgageLoanContractDao.update(contractInfo);
		}
		
		/*
		 * 插入debit交易日志
		 */
		InsertTransactionLogModel debitTrans =  new InsertTransactionLogModel();
		debitTrans.setAccountnumber(ase.getRepaymentaccountnumber());
		debitTrans.setActualbalamt(newLocalBalance);
		debitTrans.setBranchcode(header.getBranchCode());
		debitTrans.setCcy(account.getCurrencycode());
		debitTrans.setChannel(SysConstant.CHANNEL_TYPE);
		debitTrans.setChannelid(header.getUserID());
		debitTrans.setClearingcode(header.getClearingCode());
		debitTrans.setCountrycode(header.getCountryCode());
		debitTrans.setCrdrmaintind(SysConstant.CR_DR_MAINT_IND_TYPE1);
		debitTrans.setDockerid(header.getDockerId());
		debitTrans.setPreviousbalamt(localBalance);
		debitTrans.setRefaccountnumber(ase.getAccountnumber());
		debitTrans.setSandboxid(header.getSandBoxId());
		debitTrans.setTranamt(ase.getRepaymentamount());
		debitTrans.setTrandesc("mortgage loan repayment");
		debitTrans.setTrantype(SysConstant.TRANSACTION_TYPE13);
		
		// 调用服务接口地址 获得transactionLogAdding得内网地址
		String path3 = getInternalUrl("transactionLogAdding");
		if(path3.length() == 0){
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		//更新 transaction detail file
		String deTransactionString = JsonProcess.changeEntityTOJSON(debitTrans);
		ResponseEntity<String> deTransactionRes = restTemplate.postForEntity(path3,PostUtil.getRequestEntity(deTransactionString),String.class);
		JSONObject str1 = XmlToJsonUtil.xml2JSON(deTransactionRes.getBody().getBytes());
		String str2 = JsonProcess.returnValue(str1, "ResultUtil");
		ResultUtil<JSONArray> str3 = JSON.parseObject(str2, ResultUtil.class);
		if(str3.getCode().substring(1, str3.getCode().length()-1).equals("0")){
			throw new InsertException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500006),ExceptionConstant.ERROR_CODE500006);
		}
		
		//获取NextAvailableMorDealNumber
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(PathConstant.SERVICE_INTERNAL_URL,
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),
					ExceptionConstant.ERROR_CODE500002);
		}
		String path4 = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
		// 调用系统参数服务接口
		String param2 = "{\"item\":\"NextAvailableMorDealNumber\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path4, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),
					ExceptionConstant.ERROR_CODE500003);
		}
		
		JSONArray restr = JsonProcess.changeToJSONArray(result2.getBody());
		String dealnumber = format3.format(new Date()) + JsonProcess.returnValue(JsonProcess.changeToJSONObject(restr.get(0).toString()), "value");
		
		/*
		 * 插入mortgage loan交易记录
		 */
		MortgageLoanTransactionLogEntity loanTrans =  new MortgageLoanTransactionLogEntity();
		loanTrans.setAccountnumber(ase.getAccountnumber());
		loanTrans.setBranchcode(header.getBranchCode());
		loanTrans.setCcy(account.getCurrencycode());
		loanTrans.setClearingcode(header.getClearingCode());
		loanTrans.setContractnumber(ase.getContractnumber());
		loanTrans.setCountrycode(header.getCountryCode());
		loanTrans.setDealnumber(dealnumber);
		loanTrans.setDockerid(header.getDockerId());
		loanTrans.setId(UUIDUtil.generateUUID());
		loanTrans.setPhaseno(phaseNo);
		loanTrans.setRelaccountnumber(debitAccountNum);
		loanTrans.setSandboxid(header.getSandBoxId());
		loanTrans.setTranamt(ase.getRepaymentamount());
		loanTrans.setTrantime(format.parse(format.format(new Date())));
		loanTrans.setTrantype(SysConstant.TRANS_TYPE2);
		mortgageLoanTransactionLogDao.insert(loanTrans);
		
		// 写入日志
		String logstr = "The contract :"+ ase.getContractnumber() + " of mortgage loan account :" + account.getAccountnumber() + "repayment success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		
		AvailableNumberUtil.availableMortDealIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_MORTGAGE_DEALNUMBER);
		
		result.setCode("1");
		result.setMsg("Transaction Accepted");
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> resetContractDueInfo(RestTemplate restTemplate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		
		MortgageLoanContractDetailEntity request = new MortgageLoanContractDetailEntity();
		request.setNowDate(format2.format(new Date()));
		request.setRepaymentstatus(SysConstant.REPAYMENT_STATUS2);
		
		List<MortgageLoanContractDetailEntity> dueDetails = mortgageLoanContractDetailDao.findDue(request);
		
		if(dueDetails == null || dueDetails.size() == 0){
			map.put("code", "1");
			map.put("msg", "Transaction Accepted");
			return map;
		}
		
		/* 
		 * 获取贷款逾期利息
		 */
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(PathConstant.SERVICE_INTERNAL_URL,
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),
					ExceptionConstant.ERROR_CODE500002);
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
		// 调用系统参数服务接口
		String param2 = "{\"item\":\"MortgagePenaltyAPR,MortgageDefaultRate\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),
					ExceptionConstant.ERROR_CODE500003);
		}
		
		// 返回数据处理
		double mortgagePentlyAPR = 0;
		double mortgageDefaultRate = 0;
		JSONObject jsonObject1 = null;
		String revalue = null;
		String temp = null;
		for (int i = 0; i < JsonProcess.changeToJSONArray(result2.getBody()).size(); i++) {
			jsonObject1 = JsonProcess
					.changeToJSONObject(JsonProcess.changeToJSONArray(result2.getBody()).get(i).toString());
			revalue = JsonProcess.returnValue(jsonObject1, "item");
			temp = JsonProcess.returnValue(jsonObject1, "value");
			if (revalue.equals("MortgagePenaltyAPR")) {
				mortgagePentlyAPR = new BigDecimal(temp).doubleValue();
			}
			if(revalue.equals("MortgageDefaultRate")){
				mortgageDefaultRate = new BigDecimal(temp).doubleValue();
			}
		}
		
		for(int i=0; i<dueDetails.size() ; i++){
			MortgageLoanContractDetailEntity contractDetail = dueDetails.get(i);
			long duedateTime = format2.parse(format2.format(contractDetail.getRepaymentduedate())).getTime();
			long nowdateTime =  format2.parse(format2.format(new Date())).getTime();
			int days = (int) ((nowdateTime - duedateTime)/1000/60/60/24);
			BigDecimal totalPayment = BigDecimal.ZERO;
			BigDecimal pentlyAmount = BigDecimal.ZERO;
			double repaymentPerphase = contractDetail.getLoanamountperphase().doubleValue() + contractDetail.getLoaninterestperphase().doubleValue();

			if(days == 1){
				MortgageLoanContractEntity contract = new MortgageLoanContractEntity();
				contract.setAccountnumber(contractDetail.getAccountnumber());
				contract.setContractnumber(contractDetail.getContractnumber());
				MortgageLoanContractEntity contractInfo = (MortgageLoanContractEntity) mortgageLoanContractDao.findOne(contract);
				if(contractInfo.getContractstatus().equals("0")){
					contractInfo.setContractstatus("1");
					mortgageLoanContractDao.update(contractInfo);
				}
				contractDetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS3);
				mortgageLoanContractDetailDao.update(contractDetail);
			}
			
			if(contractDetail.getRepaymentcycle().equals("M")){
				if(days == 16){
					pentlyAmount = new BigDecimal(repaymentPerphase * mortgagePentlyAPR).setScale(2, BigDecimal.ROUND_HALF_UP);
					totalPayment = new BigDecimal(repaymentPerphase + pentlyAmount.doubleValue());
					contractDetail.setPenaltyamount(pentlyAmount);
					contractDetail.setTotalpayment(totalPayment);
					mortgageLoanContractDetailDao.update(contractDetail);
				}
				if(days > 30){
					pentlyAmount = new BigDecimal(repaymentPerphase * mortgagePentlyAPR + repaymentPerphase * Math.pow((1+ mortgageDefaultRate), (days-30)) - repaymentPerphase).setScale(2, BigDecimal.ROUND_HALF_UP);
					totalPayment = new BigDecimal(repaymentPerphase + pentlyAmount.doubleValue());
					contractDetail.setPenaltyamount(pentlyAmount);
					contractDetail.setTotalpayment(totalPayment);
					if(days == 31){
						contractDetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS4);
					}
					mortgageLoanContractDetailDao.update(contractDetail);
				}	
			}
			if(contractDetail.getRepaymentcycle().equals("B")){
				if(days == 8){
					pentlyAmount = new BigDecimal(repaymentPerphase * mortgagePentlyAPR).setScale(2, BigDecimal.ROUND_HALF_UP);
					totalPayment = new BigDecimal(repaymentPerphase + pentlyAmount.doubleValue());
					contractDetail.setPenaltyamount(pentlyAmount);
					contractDetail.setTotalpayment(totalPayment);
					mortgageLoanContractDetailDao.update(contractDetail);
				}
				if(days > 14){
					pentlyAmount = new BigDecimal(repaymentPerphase * mortgagePentlyAPR + repaymentPerphase * Math.pow((1+ mortgageDefaultRate), (days-14)) - repaymentPerphase).setScale(2, BigDecimal.ROUND_HALF_UP);
					totalPayment = new BigDecimal(repaymentPerphase + pentlyAmount.doubleValue());
					contractDetail.setPenaltyamount(pentlyAmount);
					contractDetail.setTotalpayment(totalPayment);
					if(days == 15){
						contractDetail.setRepaymentstatus(SysConstant.REPAYMENT_STATUS4);
					}
					mortgageLoanContractDetailDao.update(contractDetail);
				}	
			}
		}
		
		map.put("code", "1");
		map.put("msg", "Transaction Accepted");
		return map;
	}
	
	//获取内网地址公共方法
	private String getInternalUrl(String apiname) {
		String param = "{\"apiname\":\"" + apiname + "\"}";
		ResponseEntity<String> result = restTemplate.postForEntity(
				"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
				PostUtil.getRequestEntity(param), String.class);
		JSONObject res = JsonProcess.changeToJSONObject(result.getBody());
		String code = JsonProcess.returnValue(res, "code");
		String responseString = JsonProcess.changeEntityTOJSON(res);
		if (responseString == null || responseString.length() == 0 || ("0").equals(code)) {
			return "";
		} else {
			String path = JsonProcess.returnValue(res, "internaURL");
			return path;
		}
	}

	// 根据传参获取response string
	private String getResponse(String path, String param) {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param),
				String.class);
		String responseString = responseEntity.getBody();
		if (responseString == null || responseString.length() == 0) {
			return "";
		} else {
			JSONObject responseObject = JsonProcess.changeToJSONObject(responseEntity.getBody());
			String resCode = JsonProcess.returnValue(responseObject, "code");
			if ("0".equals(resCode)) {
				return "";
			}
			return responseString;
		}
	}
	
	//校验日期格式
	private boolean CheckDate(String date){
		String rexp1 = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if(date.matches(rexp1)){
			return true;
		}else{
			return false;
		}
	}
}