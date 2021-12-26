package com.pl.dto;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "employee_id")
	private long employeeId;
	
	@NotEmpty(message = "Please provide a first name")
	@NotNull
	@Column (name = "first_name")
	private String firstName;
	
	@NotEmpty(message = "Please provide a last name")
	@Column (name = "last_name")
	private String lastName;
	
	@NotNull(message = "Please provide a number of department")
	@Column (name = "department_id")
	private Long departmentId;
	
	@NotEmpty(message = "Please provide a job title")
	@Column (name = "job_title")
	private String jobTitle;
	
	@NotNull(message = "Please provide a gender")
	@Column (name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@NotNull(message = "Please provide a date of birth")
	@Column (name = "date_birth")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Moscow")  
	private Date dateBirth;
	public Employee() {
		
	}
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Date getDateBirth() {
		return dateBirth;
	}
	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", departmentId=" + departmentId + ", jobTitle=" + jobTitle + ", gender=" + gender + ", dateBirth="
				+ dateBirth + "]";
	}
	
	

}
