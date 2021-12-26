package com.pl.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.Logger;
import com.pl.dto.Employee;
import com.pl.service.EmployeeService;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

	private static Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployees() {
		logger.info("Trying to get employee list");
		return employeeService.listEmployee();
	}

	@GetMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getEmployeeById(@PathVariable long employeeId) {
		logger.info("Trying to get employee by id {}", employeeId);
		return employeeService.getEmployeeById(employeeId);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.CREATED)
	public void createEmployee(@Valid @RequestBody Employee employee) {
		logger.info("Trying to post {}", employee);
		employeeService.addEmployee(employee);
	}

	@PutMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateEmployee(@PathVariable long employeeId,@Valid  @RequestBody Employee employee, HttpServletResponse response) {
		logger.info("Trying to put employee = {} by id {}", employee, employeeId);
		if (employeeService.isExist(employeeId)) {
			employee.setEmployeeId(employeeId);
			employeeService.updateEmployee(employee);
		}else { 
			response.setStatus(404);
		}
		
	}

	@DeleteMapping(value = "/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteEmployee(@PathVariable long employeeId, HttpServletResponse response) {
		logger.info("Trying to delete employee by id {}", employeeId);
		if (employeeService.isExist(employeeId)) {
		employeeService.removeEmployee(employeeId);
		}else { 
			response.setStatus(404);
		}
	}

}
