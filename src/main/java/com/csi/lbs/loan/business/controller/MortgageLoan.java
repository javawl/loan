package com.csi.lbs.loan.business.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.lbs.loan.business.service.MortgageLoanService;
import com.csi.sbs.common.business.exception.CallOtherException;
import com.csi.sbs.common.business.model.HeaderModel;
import com.csi.sbs.common.business.util.HeaderModelUtil;
import com.csi.lbs.loan.business.clientmodel.AccountContractNumberModel;
import com.csi.lbs.loan.business.clientmodel.ContractOutstandingModel;
import com.csi.lbs.loan.business.clientmodel.ContranctDetailModel;
import com.csi.lbs.loan.business.clientmodel.MortgageAccountInfoModel;
import com.csi.lbs.loan.business.clientmodel.MortgageAccountNumberModel;
import com.csi.lbs.loan.business.clientmodel.MortgageCalculatorModel;
import com.csi.lbs.loan.business.clientmodel.MortgageLoanAmountModel;
import com.csi.lbs.loan.business.clientmodel.MortgageLoanApplicationModel;
import com.csi.lbs.loan.business.clientmodel.NextRepaymentModel;
import com.csi.lbs.loan.business.constant.ExceptionConstant;
import com.csi.lbs.loan.business.exception.AcceptException;
import com.csi.lbs.loan.business.exception.AuthorityException;
import com.csi.lbs.loan.business.exception.DateException;
import com.csi.lbs.loan.business.exception.InsertException;
import com.csi.lbs.loan.business.exception.NotFoundException;
import com.csi.lbs.loan.business.exception.OtherException;
import com.csi.lbs.loan.business.util.ResultUtil;
import com.csi.lbs.loan.business.clientmodel.OpeningLoanAccountModel;
import com.csi.lbs.loan.business.clientmodel.ReMortgageContractInfo;
import com.csi.lbs.loan.business.clientmodel.RepaymentModel;
import com.csi.lbs.loan.business.clientmodel.TransactionInfoModel;
import com.csi.lbs.loan.business.clientmodel.TransactionRequestModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/mortgage")

@Api(value = "Then controller is loan account")
public class MortgageLoan{
	
	@Resource
	private MortgageLoanService mortgageLoanService;
	
	@Resource
	private RestTemplate restTemplate;
	
	/**
	 * 开按揭贷款账户
	 * 
	 * @param OpeningLoanAccountModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/mortgageLoanAccountOpening", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to create a new mortgage loan account.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil mortgageLoanAccountOpening(
			@RequestBody @Validated OpeningLoanAccountModel openingLoanAccountModel,
			HttpServletRequest request) throws Exception {
		Map<String, Object> normalmap = null;
		ResultUtil result = new ResultUtil();
		try {
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			if(header.getCountryCode() != null && header.getCountryCode().length() > 0){
				openingLoanAccountModel.setCountrycode(header.getCountryCode());
			}
			if(header.getClearingCode() != null && header.getClearingCode().length() > 0){
				openingLoanAccountModel.setClearingcode(header.getClearingCode());
			}
			if(header.getBranchCode() != null && header.getBranchCode().length() > 0){
				openingLoanAccountModel.setBranchcode(header.getBranchCode());
			}
			if(header.getSandBoxId() != null && header.getSandBoxId().length() > 0){
				openingLoanAccountModel.setSandboxid(header.getSandBoxId());
			}
			if(header.getDockerId() != null && header.getDockerId().length() > 0){
				openingLoanAccountModel.setDockerid(header.getDockerId());
			}
		
			normalmap = mortgageLoanService.openingLoanAccount(header, openingLoanAccountModel, restTemplate);
			result.setCode(normalmap.get("code").toString());
			result.setMsg(normalmap.get("msg").toString());
			result.setData(normalmap.get("accountNumber") != null ? normalmap.get("accountNumber").toString() : "");
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} catch (Exception e) {
			throw new InsertException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500012),
					ExceptionConstant.ERROR_CODE500012);
		}
	}
	
	/**
	 * 计算最大贷款额度
	 * 
	 * @param MortgageCalculatorModel ase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/loanCalculater", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to calculate max loan amount.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil<MortgageLoanAmountModel> loanCalculater(@RequestBody @Validated MortgageCalculatorModel ase) throws Exception {
		try{
			ResultUtil<MortgageLoanAmountModel> result = mortgageLoanService.mortgageLoanCalculator(ase);
			return result;
		} catch (Exception e) {
			throw new InsertException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500012),
					ExceptionConstant.ERROR_CODE500012);
		}
	}
	
	/**
	 * 查询按揭贷款账户信息
	 * 
	 * @param MortgageAccountNumberModel ase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/accountDetailEnquiry", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to get a loan account detail.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil<MortgageAccountInfoModel> accountDetailEnquiry(
			@RequestBody @Validated MortgageAccountNumberModel ase,
			HttpServletRequest request) throws Exception {
		ResultUtil<MortgageAccountInfoModel> result = new ResultUtil<MortgageAccountInfoModel>();
		try {
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			result = mortgageLoanService.accountDetailEnquiry(header, ase, restTemplate);
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} 
	}
	
	/**
	 * 申请按揭贷款
	 * 
	 * @param MortgageLoanApplicationModel ase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mortgageLoanApplication", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to apply mortgage loan.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil<ReMortgageContractInfo> mortgageLoanApplication(
			@RequestBody @Validated MortgageLoanApplicationModel ase,
			HttpServletRequest request) throws Exception {
		ResultUtil<ReMortgageContractInfo> result = new ResultUtil<ReMortgageContractInfo>();
		try {
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			result = mortgageLoanService.mortgageLoanApplication(header, ase, restTemplate);
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} 
	}
	
	/**
	 * 注销按揭贷款账户
	 * 
	 * @param MortgageAccountNumberModel ase
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(value = "/cancellation", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to cancel ont mortgage loan account.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil cancellation(@RequestBody @Validated MortgageAccountNumberModel ase,
			HttpServletRequest request) throws Exception {
		ResultUtil result = new ResultUtil();
		try {
			
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			result = mortgageLoanService.mortgageLoanCancellation(header, ase, restTemplate);
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} 
	}
	
	/**
	 * 按揭贷款还款计划
	 * 
	 * @param  AccountContractNumberModel ase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/repaymentPlan", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to get mortgage loan repayment plan.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil<ContranctDetailModel> repaymentPlan(@RequestBody @Validated AccountContractNumberModel ase,
			HttpServletRequest request) throws Exception {
		ResultUtil<ContranctDetailModel> result = new ResultUtil<ContranctDetailModel>();
		try {
			
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			result = mortgageLoanService.repaymentPlan(header, ase);
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} 
	}
	
	/**
	 * 按揭贷款逾期账单查询
	 * 
	 * @param AccountContractNumberModel ase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/overDueRepaymentEnquiry", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to get outstanding repayment bill.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil<ContractOutstandingModel> overDueRepaymentEnquiry(@RequestBody @Validated AccountContractNumberModel ase,
			HttpServletRequest request) throws Exception {
		ResultUtil<ContractOutstandingModel> result = new ResultUtil<ContractOutstandingModel>();
		try {
			
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			result = mortgageLoanService.overDueRepaymentEnquiry(header, ase);
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} 
	}
	
	/**
	 * 按揭贷款待还款一期账单查询
	 * 
	 * @param AccountContractNumberModel ase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/nextRepaymentEnquiry", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to get outstanding repayment bill.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil<NextRepaymentModel> nextRepaymentEnquiry(@RequestBody @Validated AccountContractNumberModel ase,
			HttpServletRequest request) throws Exception {
		ResultUtil<NextRepaymentModel> result = new ResultUtil<NextRepaymentModel>();
		try {
			
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			result = mortgageLoanService.nextRepaymentEnquiry(header, ase);
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} 
	}
	
	/**
	 * 按揭贷款待交易记录查询
	 * 
	 * @param TransactionRequestModel ase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/transactionEnquiry", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to get the mortgage loan account contract borrowing or repayment details.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil<TransactionInfoModel> transactionEnquiry(@RequestBody @Validated TransactionRequestModel ase,
			HttpServletRequest request) throws Exception {
		ResultUtil<TransactionInfoModel> result = new ResultUtil<TransactionInfoModel>();
		try {
			
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			result = mortgageLoanService.transactionEnquiry(header, ase);
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} 
	}
	
	/**
	 * 按揭贷款待还款
	 * 
	 * @param RepaymentModel ase
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/repayment", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to repayment the mortgage loan.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil repayment(@RequestBody @Validated RepaymentModel ase,
			HttpServletRequest request) throws Exception {
		ResultUtil<TransactionInfoModel> result = new ResultUtil<TransactionInfoModel>();
		try {
			
			// 获取请求头参数
			HeaderModel header = HeaderModelUtil.getHeader(request);
			
			result = mortgageLoanService.repayment(header, ase, restTemplate);
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} 
	}
	
	/**
	 * 按揭贷款利息计算
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetContractDueInfo", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to repayment the mortgage loan.", notes = "version 0.0.1")
	@ApiIgnore
	public Map<String, Object> resetContractDueInfo() throws Exception {
			Map<String, Object> map = new HashMap<String, Object>();
				
			map = mortgageLoanService.resetContractDueInfo(restTemplate);
			return map;
	}
}