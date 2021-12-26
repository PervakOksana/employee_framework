package com.pl.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.springframework.stereotype.Repository;
import com.pl.dto.Employee;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	private static Logger logger = LogManager.getLogger(EmployeeDaoImpl.class);

	private static final SessionFactory sessionFactory = new AnnotationConfiguration().configure()
			.buildSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private Transaction transaction = null;

	@Override
	public void addEmployee(Employee employee) {
		logger.info("IN addEmployee, employee details={}", employee);
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			transaction = currentSession.beginTransaction();
			employee.setEmployeeId(0);
			currentSession.merge(employee);
			currentSession.getTransaction().commit();
			logger.info("Employee saved successfully, employee details={}", employee);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Employee didn't save, employee details={}, error - {} ", employee, e);
		}
	}

	@Override
	public void updateEmployee(Employee employee) {
		logger.info("IN updateEmployee, employee details={}", employee);
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			transaction =currentSession.beginTransaction();
			currentSession.merge(employee);
			currentSession.getTransaction().commit();
			logger.info("Employee updated successfully, employee details={}", employee);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Employee didn't update, employee details={}, error - {} ", employee, e);
		}
	}

	@Override
	public List<Employee> listEmployee() {
		logger.info("IN TO listEmployee");
		List<Employee> employeeList = new ArrayList<>();
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			transaction = currentSession.beginTransaction();
			employeeList = currentSession.createQuery("FROM Employee").list();
			logger.info("Employee list loaded successfully" + employeeList);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Employee list didn't load, error - {}", e);
		}
		return employeeList;
	}

	@Override
	public Employee getEmployeeById(long id) {
		logger.info("IN getEmployeeById, id={}", id);
		Employee employee = new Employee();
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			transaction = currentSession.beginTransaction();
			employee = (Employee) currentSession.get(Employee.class, new Long(id));
			logger.info("Employee loaded successfully, employee details={} with id {}", employee, id);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Employee didn't load. id = {}", id);
		}
		return employee;
	}

	@Override
	public void removeEmployee(long id) {

		try {
			Session currentSession = sessionFactory.openSession();
			Employee employee = (Employee) currentSession.get(Employee.class, new Long(id));
			if (null != employee) {
				currentSession.beginTransaction();
				currentSession.delete(employee);
				currentSession.getTransaction().commit();
				logger.info("Employee deleted successfully, employee details={}, id ={}", employee, id);
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("Employee didn't delete, employee id={}, error - {} ", id, e);
		}
	}
}
