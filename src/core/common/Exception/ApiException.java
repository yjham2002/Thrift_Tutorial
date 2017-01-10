package core.common.Exception;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import core.common.util.VarConst;

/**
 * 
 * Api 사용에 있어 발생하는 Exception 처리를 담당.
 */
public class ApiException extends Exception 
{
	private final static Log logger = LogFactory.getLog(ApiException.class);
	
	static final long serialVersionUID = 6259853381255397323L;
	
	private int errorCode;
	private String message;
	private AfterExceptionProcess afterExceptionProcess;

	public ApiException(int errorCode) 
	{
		this.errorCode = errorCode;
		this.message =  VarConst.getErrorMessage(errorCode);
		logger.error("APIException : " ,this);
	}

	public ApiException(String lang,int errorCode) 
	{
		int nLang = 1;
		if(lang.equals("id"))
			nLang = 2;
		
		
		this.errorCode = errorCode;
		this.message =  VarConst.getErrorMessage(nLang,errorCode);
		logger.error("APIException : " ,this);
	}
	
	public ApiException(String lang, int errorCode, AfterExceptionProcess afterExceptionProcess) 
	{
		int nLang = 1;
		if(lang.equals("id"))
			nLang = 2;
		
		this.errorCode = errorCode;
		this.message =  VarConst.getErrorMessage(nLang,errorCode);
		this.setAfterExceptionProcess(afterExceptionProcess);
		logger.error("APIException : " ,this);
	}
	
	
	public ApiException(String message) 
	{
		this.errorCode = VarConst.ErrorCode.NOT_DEFINE_ERROR;
		this.message = message;
		logger.error("APIException : " ,this);
	}
	public ApiException(int errorCode,String message) 
	{
		this.errorCode = errorCode;
		this.message =  message;
		logger.error("APIException : " ,this);
	}
	
	public int getErrorCode()
	{
		return this.errorCode;
	}
	public String getMessage() 
	{
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public AfterExceptionProcess getAfterExceptionProcess() {
		return afterExceptionProcess;
	}

	public void setAfterExceptionProcess(AfterExceptionProcess afterExceptionProcess) {
		this.afterExceptionProcess = afterExceptionProcess;
	}

}

