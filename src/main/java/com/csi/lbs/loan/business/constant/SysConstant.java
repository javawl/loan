package com.csi.lbs.loan.business.constant;

import java.util.HashMap;
import java.util.Map;

public class SysConstant {

	  //账号类型
	   public static final String ACCOUNT_TYPE1 = "001";
	   public static final String ACCOUNT_TYPE2 = "002";
	   public static final String ACCOUNT_TYPE3 = "003";
	   public static final String ACCOUNT_TYPE4 = "100";
	   public static final String ACCOUNT_TYPE5 = "200";
	   public static final String ACCOUNT_TYPE6 = "300";
	   public static final String ACCOUNT_TYPE7 = "400";
	   public static final String ACCOUNT_TYPE8 = "500";
	   public static final String ACCOUNT_TYPE9 = "600";
	   public static final String ACCOUNT_TYPE10 = "700";
	   
	   public static Map<String,Object> getAccountTypeMap(){
		   Map<String,Object> map = new HashMap<String,Object>();
		   
		   map.put(ACCOUNT_TYPE1, "Saving");
		   map.put(ACCOUNT_TYPE2, "Current");
		   map.put(ACCOUNT_TYPE3, "FX");
		   map.put(ACCOUNT_TYPE4, "TermDeposit");
		   map.put(ACCOUNT_TYPE5, "Loans");
		   map.put(ACCOUNT_TYPE6, "Stock Investment");
		   map.put(ACCOUNT_TYPE7, "Precious Metal Investment");
		   map.put(ACCOUNT_TYPE8, "Mutual Fund Investment");
		   map.put(ACCOUNT_TYPE9, "Mortgage Loan");
		   map.put(ACCOUNT_TYPE10, "Personal Tax Loan");
		   
		   return map;
	   }
	   
	   //账号状态
	   public static final String ACCOUNT_STATE1 = "D";
	   public static final String ACCOUNT_STATE2 = "A";
	   public static final String ACCOUNT_STATE3 = "C";
	   
	   public static Map<String,Object> getAccountStateMap(){
		   Map<String,Object> map = new HashMap<String,Object>();
		   
		   //map.put(ACCOUNT_STATE1, "账号已关闭");
		   map.put(ACCOUNT_STATE2, "账号正常");
		   map.put(ACCOUNT_STATE3, "账号已关闭");
		   
		   return map;
	   }
	   
	   //交易类型
	   public static final String TRANSACITON_TYPE1 = "0001";
	   public static final String TRANSACTION_TYPE2 = "0002";
	   public static final String TRANSACTION_TYPE3 = "0003";
	   public static final String TRANSACTION_TYPE4 = "0004";
	   public static final String TRANSACTION_TYPE5 = "0005";
	   public static final String TRANSACTION_TYPE6 = "0006";
	   public static final String TRANSACTION_TYPE7 = "0007";
	   public static final String TRANSACTION_TYPE8 = "0008";
	   public static final String TRANSACTION_TYPE9 = "0009";
	   public static final String TRANSACTION_TYPE10 = "0010";
	   public static final String TRANSACTION_TYPE11 = "0011";
	   public static final String TRANSACTION_TYPE12 = "0012";
	   public static final String TRANSACTION_TYPE13 = "0013";
	   
	   public static Map<String,Object> getTransactionTypeMap(){
		   Map<String,Object> map = new HashMap<String,Object>();
		   
		   map.put(TRANSACITON_TYPE1, "定期存款");
		   map.put(TRANSACTION_TYPE2, "定期取款");
		   map.put(TRANSACTION_TYPE3, "定期续存");
		   map.put(TRANSACTION_TYPE4, "存入");
		   map.put(TRANSACTION_TYPE5, "转账");
		   map.put(TRANSACTION_TYPE6, "取款");
		   map.put(TRANSACTION_TYPE7, "外汇买入");
		   map.put(TRANSACTION_TYPE8, "外汇卖出");
		   map.put(TRANSACTION_TYPE9, "股票买入");
		   map.put(TRANSACTION_TYPE10, "股票卖出");
		   map.put(TRANSACTION_TYPE11, "基金买入");
		   map.put(TRANSACTION_TYPE12, "基金卖出");
		   map.put(TRANSACTION_TYPE13, "mortgage loan");
		   
		   return map;
	   }
	   
	   //渠道类型
	   public static final String CHANNEL_TYPE = "API";
	   
	   //借贷标志
	   public static final String CR_DR_MAINT_IND_TYPE1 = "D";
	   public static final String CR_DR_MAINT_IND_TYPE2 = "C";
	   
	   public static Map<String,Object> getCRDRMap(){
		   Map<String,Object> map = new HashMap<String,Object>();
		   
		   map.put(CR_DR_MAINT_IND_TYPE1, "支出");
		   map.put(CR_DR_MAINT_IND_TYPE2, "存入");
		   
		   return map;
	   }
	 //借贷标志
	   public static final String TRANS_TYPE1 = "borrowing";
	   public static final String TRANS_TYPE2 = "repayment";
	   
	   public static Map<String,Object> getLTMap(){
		   Map<String,Object> map = new HashMap<String,Object>();
		   
		   map.put(TRANS_TYPE1, "borrowing");
		   map.put(TRANS_TYPE2, "repayment");
		   
		   return map;
	   }
	   
	 //还款状态
	   public static final String REPAYMENT_STATUS1 = "0";
	   public static final String REPAYMENT_STATUS2 = "1";
	   public static final String REPAYMENT_STATUS3 = "2";
	   public static final String REPAYMENT_STATUS4 = "3";
	   
	   public static Map<String,String> getRSMap(){
		   Map<String,String> map = new HashMap<String,String>();
		   
		   map.put(REPAYMENT_STATUS1, "Pending");
		   map.put(REPAYMENT_STATUS2, "Paid Off");
		   map.put(REPAYMENT_STATUS3, "Due");
		   map.put(REPAYMENT_STATUS4, "Default");
		   
		   return map;
	   }
	    
	   
	   
	   //操作类型
	   public static final String OPERATION_CREATE = "create";
	   public static final String OPERATION_UPDATE = "update";
	   public static final String OPERATION_QUERY = "query";
	   public static final String OPERATION_DELETE = "delete";
	   public static final String OPERATION_TRANSFER = "transfer";
	   
	   //国家代码转数字
	   public static final String COUNTRY_CODE_CN = "1223";
	   public static final String COUNTRY_CODE_HK = "1720";
	   
	   //操作状态
	   public static final String OPERATION_SUCCESS = "success";
	   public static final String OPERATION_FAIL = "fail";
	   
	   //可用Deal Number Item Seq
	   public static final String NEXT_AVAILABLE_DEALNUMBER = "NextAvailableDealNumber";
	   
	 //可用Mortgage Deal Number Item Seq
	   public static final String NEXT_AVAILABLE_MORTGAGE_DEALNUMBER = "NextAvailableMorDealNumber";
	   //可用AccountNumber Item Seq
	   public static final String NEXT_AVAILABLE_ACCOUNTNUMBER = "NextAvailableAccountNumber";
	   //可用CustomerNumber Item
	   public static final String NEXT_AVAILABLE_CUSTOMERNUMBER = "NextAvailableCustomerNumber";
    //可用TD Number
	   public static final String NEXT_AVAILABLE_TDNUMBER = "NextAvailableTDNumber";
	   
	 //可用TD Number
	   public static final String NEXT_AVAILABLE_MORGLOAN_NUMBER = "NextAvailableMorgLoanNumber";
  
	   //可用CustomerNumber Item
	   public static final String NEXT_AVAILABLE_SEQ = "SEQ";
	   
	   //写日志服务地址
	   public static final String WRITE_LOG_SERVICEPATH = "http://SYSADMIN/sysadmin/log/writeTransactionLog";
	   //返回内部服务接口地址 URL
       public static final String SERVICE_INTERNAL_URL = "/sysadmin/getServiceInternalURL";
       //本服务内网地址
       public static final String LOCAL_SERVICE_URL = "http://localhost:8098/loan";
       //网关地址(服务器)
       public static final String GATEWAY_SERVICE = "47.112.146.96:8086/loan";
       //网关地址(本地)
       public static final String GATEWAY_LOCALHOST = "localhost:8086/loan";
       //本服务名称
       public static final String LOCAL_SERVICE_NAME = "loan";
       //本服务描述
       public static final String LOCAL_DESCRIBE = "This is the investment service module, which contains all associated API resources.";
        
       //maturity Status
       public static final String MATURITY_STATUS_A = "A";
       public static final String MATURITY_STATUS_D = "D";
       
       //客户默认密码
       public static final String INIT_PWD = "123456";
       //创建成功提示
       public static final String CREATE_SUCCESS_TIP = "Creation Succeed";
       //HTTP 协议
       public static final String HTTP = "http://";
       
}
