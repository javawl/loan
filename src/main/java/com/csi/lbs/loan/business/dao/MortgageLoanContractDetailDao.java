package com.csi.lbs.loan.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.csi.lbs.loan.business.base.BaseDao;
import com.csi.lbs.loan.business.entity.MortgageLoanContractDetailEntity;
@Mapper
public interface MortgageLoanContractDetailDao<T> extends BaseDao <T> {
	
	List<MortgageLoanContractDetailEntity> findDue(MortgageLoanContractDetailEntity ase);
}