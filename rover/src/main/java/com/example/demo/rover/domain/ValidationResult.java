package com.example.demo.rover.domain;

import com.example.demo.rover.enumeration.ErrorCode;

public class ValidationResult {
	private boolean valid;
	private String message;
	private ErrorCode errorCode;

	public ValidationResult(boolean valid, ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.valid = valid;
	}

	public ValidationResult(boolean valid, String message) {
		this.valid = valid;
		this.message=message;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
