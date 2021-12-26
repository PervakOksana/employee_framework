package com.pl.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pl.dao.EmployeeDao;
import com.pl.dto.Employee;
import com.pl.dto.Gender;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeDao employeeDAO;

	private static Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

	@Override
	public void addEmployee(Employee employee) {
		employeeDAO.addEmployee(employee);
	}

	@Override
	public void updateEmployee(Employee employee) {
		employeeDAO.updateEmployee(employee);
	}

	@Override
	public List<Employee> listEmployee() {
		return employeeDAO.listEmployee();
	}

	@Override
	public Employee getEmployeeById(long id) {
		return employeeDAO.getEmployeeById(id);
	}

	@Override
	public void removeEmployee(long id) {
		employeeDAO.removeEmployee(id);
	}

	@Override
	public boolean isExist(long id) {
		if (employeeDAO.getEmployeeById(id) != null) {
			return true;
		}
		return false;
	}

	@Override
	public Employee parserJsonEmployee(String strEmployee) {
		if (strEmployee == null || strEmployee.trim().length() == 0) {
			logger.error("Entered data is not correctly: {}", strEmployee);
		}
		logger.info("strEmployee : {}",strEmployee);
		Employee empl = new Employee();
		JSONObject jObj = new JSONObject(strEmployee);
		Iterator<String> it = jObj.keys();
		boolean isNull = false;
		int counter=0;
		
		while (it.hasNext()) {
			counter++;
			String key = it.next();
			Object o = jObj.get(key);
			logger.info("{} : {}", key, o);
			if (o == null || o.toString().trim().length() == 0)
				isNull = true;
			try {
				if ("employeeId".equals(key)) {
					String id = o.toString().trim();
					empl.setEmployeeId(Long.parseLong(id));}
				if ("firstName".equals(key))
					empl.setFirstName(((String) o));
				if ("lastName".equals(key))
					empl.setLastName(((String) o));
				if ("departmentId".equals(key)) {
					String d = o.toString().trim();
					empl.setDepartmentId(Long.parseLong(d));
				}
				if ("jobTitle".equals(key))
					empl.setJobTitle(((String) o));
				if ("gender".equals(key))
					empl.setGender(Gender.valueOf((String) o));
				if ("dateBirth".equals(key)) {
					DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date date = format.parse((String) o);
										empl.setDateBirth(date);
				}
			} catch (Exception e) {
				logger.error("Entered data is not correctly: {}, with errors: {}", strEmployee, e);
			}
		}
		if (isNull) {
			empl = null;
		}
		logger.info("isNull: {}", isNull);
		logger.info("counter: {}", counter);
		logger.info("empl: {}", empl);
		if ((empl == null )|| (empl.getEmployeeId() !=0 && counter !=7)) {
			logger.info("empl 1");
			empl = null;
		}
		if ((empl == null )|| (empl.getEmployeeId()==0 && counter !=6)) {
			logger.info("empl 2");
			empl = null;
		}
		return empl;
	}

}
