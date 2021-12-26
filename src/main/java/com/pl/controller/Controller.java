package com.pl.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pl.dto.Employee;
import com.pl.service.EmployeeService;

import org.apache.logging.log4j.LogManager;

@RestController
@RequestMapping(value = "/employees/test")
public class Controller {

	@Autowired
	private EmployeeService employeeService;

	private static Logger logger = LogManager.getLogger(Controller.class);

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> getAllEmployees() {
		logger.info("Trying to get employee list");
		return employeeService.listEmployee();
	}

	@GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getEmployeeById(HttpServletRequest request) throws IOException {
		String employeeId = request.getParameter("id");
		long id = 1;
		logger.info("Trying to get employee by id {}", employeeId);
		if (employeeId != null || employeeId.trim().length() != 0) {
			try {
				id = Long.parseLong(employeeId);
			} catch (NumberFormatException e) {
				logger.error("Entered data  id = {} is not correctly", employeeId);
			}
		}
		return employeeService.getEmployeeById(id);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public void createEmployee(HttpServletRequest request,HttpServletResponse response) {
		String strEmployee = "";
		try {
			strEmployee = new BufferedReader(new InputStreamReader(request.getInputStream())).lines()
					.collect(Collectors.joining("\n"));
			Employee employee = employeeService.parserJsonEmployee(strEmployee);
			logger.info("Trying to post {}", employee);
			if (employee != null) {
				employeeService.addEmployee(employee);
				response.setStatus(200);
			} else {
				response.setStatus(400);
				logger.error("Entered data is not correctly: {}", strEmployee);
			}
		} catch (IOException e) {
			logger.error("Entered data is not correctly: {}, with errors: {}", strEmployee, e);
		}

	}

	@PutMapping(value = "/put", produces = MediaType.APPLICATION_JSON_VALUE)
	public void updateEmployee(HttpServletRequest request,HttpServletResponse response) {
		String employeeId = request.getParameter("id");
		String strEmployee = "";
		try {
			strEmployee = new BufferedReader(new InputStreamReader(request.getInputStream())).lines()
					.collect(Collectors.joining("\n"));
			Employee employee = employeeService.parserJsonEmployee(strEmployee);
			logger.info("Trying to put employee {}", employee);
			if (employee != null && employeeService.isExist(Long.parseLong(employeeId))) {
				employee.setEmployeeId(Long.parseLong(employeeId));
				employeeService.updateEmployee(employee);
			} else {
				if (employee == null) {
					response.setStatus(400);
					logger.error("Entered data is not correctly: {}", strEmployee);
				}
				if (!employeeService.isExist(Long.parseLong(employeeId))) {
					response.setStatus(404);
					logger.error("Employee with id: {} is not found", employeeId);
				}
			}
		} catch (IOException e) {
			logger.error("Entered data is not correctly: {}, with errors: {}", strEmployee, e);
		}
	}

	@DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public void deleteEmployee(HttpServletRequest request,HttpServletResponse response) {
		String employeeId = request.getParameter("id");
		if (employeeService.isExist(Long.parseLong(employeeId))) {
			logger.info("Trying to delete employee by id {}", employeeId);
			employeeService.removeEmployee(Long.parseLong(employeeId));
		} else {
			response.setStatus(404);
			logger.error("Employee with id: {} is not found", employeeId);
		}
	}

}
