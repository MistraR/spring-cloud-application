package com.mistra.base.exception;



/**
 * @author lijun
 * @date 2018-04-20.
 * @descritpion
 */
public class DataBaseRollBackException extends BaseServiceException {
    public DataBaseRollBackException() {
        super(ResultCode.DATABASE_ERROR);
    }
}
