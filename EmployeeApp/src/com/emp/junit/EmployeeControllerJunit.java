package com.emp.junit;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.employee.EmployeeController;
import com.employee.dto.EmployeeDTO;
import com.employee.model.Employee;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
	"file:C:/Users/GS00545139/Documents/workspace-eclipse/EmployeeApp/WebContent/WEB-INF/spring-servlet.xml" 
})
@WebAppConfiguration
public class EmployeeControllerJunit {

	MockMvc mockMvc;
	
	@Mock
	private EmployeeService empService;
	
	@InjectMocks
	private EmployeeController empController;
	
	@Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(empController).build();
    }

	@Test
	public void testgetEmpGetService() throws Exception{
		Employee emp = new Employee("gautam", "sonar", "gnsonar@gmail.com");
		
		when(empService.getEmployee(10)).thenReturn(emp);
		
		MvcResult mvcResult = mockMvc.perform(get("/employees/getEmployee/{id}",10))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.firstName", is("gautam")))
		.andReturn();
		
		System.out.println(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testgetAllEmpGetService() throws Exception{
		Employee emp = new Employee("gautam", "sonar", "gnsonar@gmail.com");
		Employee emp1 = new Employee("gautam", "sonar", "gnsonar@gmail.com");
		Employee emp2 = new Employee("gautam", "sonar", "gnsonar@gmail.com");
		
		when(empService.getAllEmployees("admin")).thenReturn(Arrays.asList(emp,emp1,emp2));
		
		mockMvc.perform(get("/employees/getAllEmployees")
				.param("user", "admin")
				.accept(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$[0].firstName", is("gautam")));
	}
	
	@Test
	public void testSaveEmpPostService() throws Exception{
		
		EmployeeDTO dto = new EmployeeDTO();
		dto.setFirstName("gautam");
		dto.setLastName("sonar");
		dto.setEmail("gautam@gmail.com");
		dto.setUser("admin");
		dto.setId("12345674");
		when(empService.addEmployee(any(Employee.class))).thenReturn(12345674);
		when(empService.updateEmployee(any(Integer.class), any(Employee.class))).thenReturn(true);
		
		mockMvc.perform(post("/employees/saveEmployee")
				.content(this.converObjectToJson(dto))
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(content().string("12345674"));
	}
	
	@Test
	public void testDeleteEmpService() throws Exception{
		
		
		when(empService.deleteEmployee(123)).thenReturn(true);
		
		mockMvc.perform(delete("/employees/deleteEmployee")
				.param("empid", "123")
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(content().string("success"));
	}
	
	public byte[] converObjectToJson(Object obj) throws JsonProcessingException{
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsBytes(obj);
	}
}
