package com.pl.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.pl.dto.Employee;

public interface EmployeeDao {
	
	public void addEmployee(Employee employee);
	public void updateEmployee(Employee employee);
	public List<Employee> listEmployee();
	public Employee getEmployeeById(long id);
	public void removeEmployee(long id);

}
