package ca.sheridancollege.houyue.bean;

import java.sql.Date;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Employee {
	
	 Long employee_id;
	 String first_name;
	 @NonNull
	 String last_name;
	 @NonNull
	 String email;
	 String phone_number;
	 @NonNull
	 Date hire_date;
	 @NonNull
	 String job_id;
	 Float salary;
	 Float commission_pct;
	 Long manager_id;
	 Long department_id;
	 
	 
	 
}
