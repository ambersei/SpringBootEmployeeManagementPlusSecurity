package ca.sheridancollege.houyue.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.houyue.bean.Department;
import ca.sheridancollege.houyue.bean.Employee;
import ca.sheridancollege.houyue.bean.User;

@Repository 
public class DatabaseAccess {
	
	@Autowired 
	private NamedParameterJdbcTemplate jdbc;
	
	public User findUserAccount(String email) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource(); 
		
		String query = "SELECT * FROM sec_user WHERE email = :email"; 
		namedParameters.addValue("email", email);
		
		try {
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException erdae) {
			return null; 
		}

	}
	
	public List<String> getRolesById(Long userId) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource(); 
		
		String query = "SELECT sec_role.roleName "
				+  "FROM user_role, sec_role "
				+ "WHERE user_role.roleId = sec_role.roleId "
				+ "AND userId = :userId"; 
		
		namedParameters.addValue("userId", userId);
		return jdbc.queryForList(query, namedParameters, String.class);
	}
	
	
//	get the department list 
	public List<Department> getDepartmentList () {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource(); 
		
		String query = "SELECT department_id, department_name FROM DEPARTMENTS"; 
	
		return  jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Department>(Department.class)); 
	}
	
//	get the employee list 
	public List<Employee> getEmployeeList () {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource(); 
		
		String query = "SELECT EMPLOYEE_ID FROM EMPLOYEES"; 
	
		return  jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Employee>(Employee.class)); 
	}
	
	
//	get the next employee id from the table 
	public Long getNextEmployeeId() {
	    String query = "SELECT COALESCE(MAX(EMPLOYEE_ID), 0) + 1 FROM EMPLOYEES";
	    return jdbc.queryForObject(query, new MapSqlParameterSource(), Long.class);
	}
	
	public void insertEmployee (Employee employee) {

		MapSqlParameterSource namedParameters = new MapSqlParameterSource(); 
		
		Long nextId = getNextEmployeeId();
	    employee.setEmployee_id(nextId);
		
		String query = "INSERT INTO EMPLOYEES (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, "
	            + "PHONE_NUMBER, HIRE_DATE, JOB_ID, SALARY, COMMISSION_PCT, MANAGER_ID, DEPARTMENT_ID) "
	            + "VALUES (:employee_id, :first_name, :last_name, :email, :phone_number, :hire_date, "
	            + ":job_id, :salary, :commission_pct, :manager_id, :department_id)";

		namedParameters.addValue("employee_id", employee.getEmployee_id());
		namedParameters.addValue("first_name", employee.getFirst_name());
	    namedParameters.addValue("last_name", employee.getLast_name());
	    namedParameters.addValue("email", employee.getEmail());
	    namedParameters.addValue("phone_number", employee.getPhone_number());
	    namedParameters.addValue("hire_date", new java.sql.Date(employee.getHire_date().getTime())); 
	    namedParameters.addValue("salary", employee.getSalary());
	    namedParameters.addValue("commission_pct", employee.getCommission_pct());
	    namedParameters.addValue("manager_id", employee.getManager_id());
	    namedParameters.addValue("department_id", employee.getDepartment_id());
	    namedParameters.addValue("job_id", employee.getJob_id());
		
		jdbc.update(query,namedParameters); 
		
	}
	
	public void deleteEmployeeById (Long empid) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource(); 
		
		String query = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = :empid"; 
		
		namedParameters.addValue("empid", empid);
		
		jdbc.update(query,namedParameters);
	}
	
	public void updateEmployeeSal(Long empid, Float newSal) {
		
		MapSqlParameterSource namedParameters = new MapSqlParameterSource(); 
		
		String query = "UPDATE EMPLOYEES SET SALARY = :newSal WHERE EMPLOYEE_ID = :empid"; 
		
		namedParameters.addValue("empid", empid);
		namedParameters.addValue("newSal", newSal);
		
		jdbc.update(query,namedParameters);
	}
}
