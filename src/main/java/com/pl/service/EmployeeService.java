package com.pl.service;

import java.util.List;

import com.pl.dto.Employee;

public interface EmployeeService {	
	public void addEmployee(Employee employee);
	public void updateEmployee(Employee employee);
	public List<Employee> listEmployee();
	public Employee getEmployeeById(long id);
	public void removeEmployee(long id);
	public boolean isExist(long id);
	public Employee parserJsonEmployee(String strEmployee);
}
