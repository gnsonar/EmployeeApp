package com.employee.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.LoginLogoutDTO;
import com.employee.model.AppUser;
import com.employee.model.Employee;

public class ModelMapperUtil {

	private static ModelMapper map = new ModelMapper();
	
	public static EmployeeDTO mapEmpModelToDTO(Employee emp){
		return map.map(emp, EmployeeDTO.class);
	}
	
	public static List<EmployeeDTO> mapEmpModelToDTOList(List<Employee> empList){
		
		List<EmployeeDTO> empDTOList = new ArrayList<>();
		
		Iterator<Employee> itr = empList.iterator();
		while(itr.hasNext()){
			empDTOList.add(mapEmpModelToDTO(itr.next()));
		}
		return empDTOList;
	}
	
	public static Employee mapEmpDTOToModel(EmployeeDTO emp){
		return map.map(emp, Employee.class);
	}
	
	public static AppUser mapLoginDTOtoAppModel(LoginLogoutDTO dto){
		return map.map(dto, AppUser.class);
	}
}
