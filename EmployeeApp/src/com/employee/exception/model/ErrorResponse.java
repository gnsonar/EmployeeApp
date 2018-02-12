package com.employee.exception.model;

public class ErrorResponse {

	private String errorCode;
	private String errorMessage;
	private String devMessage;
	
	public ErrorResponse(String errorCode,String errorMessage,String devMessage){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.devMessage = devMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getDevMessage() {
		return devMessage;
	}
	public void setDevMessage(String devMessage) {
		this.devMessage = devMessage;
	}
	
	
}
