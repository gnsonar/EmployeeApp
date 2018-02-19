package com.employee.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrors {

	private List<ValidationErrorDTO> errorList = new ArrayList<>();

	public List<ValidationErrorDTO> getErrorList() {
		return errorList;
	}

	public void setErrorList(ValidationErrorDTO e) {
		this.errorList.add(e);
	}
}
