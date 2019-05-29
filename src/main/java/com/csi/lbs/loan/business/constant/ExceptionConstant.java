package com.csi.lbs.loan.business.constant;

import java.util.HashMap;
import java.util.Map;

public class ExceptionConstant {
	
	public static final int ERROR_CODE201001 = 201001;//不是一个Sav 或者 Current 账号
	public static final int ERROR_CODE201002 = 201002;//debit账号关闭
	
	public static final int ERROR_CODE202001 = 202001;//账号状态不是Active的
	public static final int ERROR_CODE202002 = 202002;//账号余额不足
	public static final int ERROR_CODE202003 = 202003;//借款金额大于最大贷款金额
	
	public static final int ERROR_CODE400001 = 400001;//必填字段不全
	public static final int ERROR_CODE400002 = 400002;//请求body格式有误
	
	public static final int ERROR_CODE404001 = 404001;//账号accountnumber不存在
    public static final int ERROR_CODE404002 = 404002;//客户不存在 
    public static final int ERROR_CODE404003 = 404003;//没有交易记录
    public static final int ERROR_CODE404004 = 404004;//账号不存在
    public static final int ERROR_CODE404005 = 404005;//合同不存在
    public static final int ERROR_CODE404006 = 404006;//没有未还款账单
    public static final int ERROR_CODE404007 = 404007;//没有逾期账单
    
    public static final int ERROR_CODE500001 = 500001;//不支持此currency
    public static final int ERROR_CODE500002 = 500002;//调用服务接口地址失败
    public static final int ERROR_CODE500003 = 500003;//调用系统参数失败
    public static final int ERROR_CODE500004 = 500004;//有逾期拒绝交易
    public static final int ERROR_CODE500005 = 500005;//更新debit号失败
    public static final int ERROR_CODE500006 = 500006;//插入交易记录失败
    public static final int ERROR_CODE500007 = 500007;//有未还账单不能取消账户
    public static final int ERROR_CODE500008 = 500008;//小于最小贷款金额
    public static final int ERROR_CODE500009 = 500009;//输入的还款金额与待还款金额不同
    public static final int ERROR_CODE500012 = 500012;//操作失败
	
    public static Map<Integer,String> getExceptionMap(){
    	Map<Integer,String> map = new HashMap<Integer,String>();
    	
    	map.put(ERROR_CODE201001, "Not a SAV or a Current Account type");//不是一个Sav 或者 Current 账号
    	map.put(ERROR_CODE201002, "Debit Account has closed");
    	
    	map.put(ERROR_CODE202001, "Inactive Locan Account");//账号状态不是Active的
    	map.put(ERROR_CODE202002, "Insufficient Fund");//账号余额不足
    		
    	map.put(ERROR_CODE400001, "Required field incomplete");//必填字段不全
    	map.put(ERROR_CODE400002, "Incorrect requesting format");//请求body格式有误
    	
    	map.put(ERROR_CODE404001, "Account Number Not Found");//账号accountnumber不存在
    	map.put(ERROR_CODE404002, "Customer Not Found");//客户不存在
    	map.put(ERROR_CODE404003, "Transaction Detail Not Found");//没有交易记录
    	map.put(ERROR_CODE404004, "Record Not Found"); //账号不存在
    	map.put(ERROR_CODE404005, "Contract Not Found"); //合同不存在
    	map.put(ERROR_CODE404006, "There is no unpaid bill"); //没有未还款账单
    	map.put(ERROR_CODE404007, "There is no overdue bill"); //没有逾期账单
    	
    	map.put(ERROR_CODE500001, "Currency Not Supported");//不支持此currency
    	map.put(ERROR_CODE500002, "Failed to call service interface address");//调用服务接口地址失败
    	map.put(ERROR_CODE500003, "Failed to call system parameters");//调用系统参数失败
    	map.put(ERROR_CODE500004, "Application rejected due to overdue payments");//有逾期拒绝交易
    	map.put(ERROR_CODE500005, "Update Debit Account Failed");
    	map.put(ERROR_CODE500006, "Insert Transaction Failed");//插入交易记录失败
    	map.put(ERROR_CODE500007, "Failed to cancel due to unpaid bill");//有未还账单不能取消账户
    	map.put(ERROR_CODE500008, "The minimum borrowing needs is 500 HKD");//小于最小贷款金额
    	map.put(ERROR_CODE500009, "Incorrect payment amount. Please check your outstanding payment and try again");
    	map.put(ERROR_CODE500012, "Operation failed");//操作失败-开卡失败
    	return map;
    }
}
