package com.example.demo.rover.enumeration;

public enum ErrorCode {
	INVALID_REQUEST("Request is invalid"),
	INTERNAL_SERVER_ERROR("Internal Server Error");
	
	private ErrorCode(String description) {
		this.description=description;
	}
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
