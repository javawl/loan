package com.csi.lbs.loan.business.exception;

import com.csi.sbs.common.business.exception.GlobalException;

@SuppressWarnings("serial")
public class UpdateException extends GlobalException{

	public UpdateException(String message, int code)
    {
        super(message, code);
    }

}
