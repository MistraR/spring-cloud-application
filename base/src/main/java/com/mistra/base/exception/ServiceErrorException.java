package com.mistra.base.exception;

import net.cdsunrise.wm.base.web.ResultCode;

/**
 * @author lijun
 * @date 2018-04-23.
 * @descritpion
 */
public class ServiceErrorException extends BaseServiceException {

    public ServiceErrorException(String message) {
        super(ResultCode.SERVICE_ERROR, message);
    }
}
