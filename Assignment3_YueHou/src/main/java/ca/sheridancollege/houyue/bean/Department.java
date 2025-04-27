package ca.sheridancollege.houyue.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Department {
	
	Long department_id;
	@NonNull
	String department_name;
	Long manager_id ; 
	Long location_id; 
}
