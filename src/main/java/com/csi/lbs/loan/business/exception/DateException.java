package com.csi.lbs.loan.business.exception;

import com.csi.sbs.common.business.exception.GlobalException;

@SuppressWarnings("serial")
public class DateException extends GlobalException{

	public DateException(String message, int code)
    {
        super(message, code);
    }

}
