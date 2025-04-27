package ca.sheridancollege.houyue.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.houyue.bean.Department;
import ca.sheridancollege.houyue.bean.Employee;
import ca.sheridancollege.houyue.database.DatabaseAccess;

@Controller
public class HomeController {

	@Autowired
	@Lazy
	private DatabaseAccess da; 
	
	@GetMapping("/")
	public String index() {
	    return "index"; 
	}
	 	
	@GetMapping("/secure/insert")
	public String goInsert(Model model) {
		
//		read the department list 
	    List<Department> departmentList = da.getDepartmentList();
	    model.addAttribute("departmentList", departmentList);
// Add an empty employee object for the form
	    model.addAttribute("employee", new Employee());
		return "/secure/insert"; 
	}
	
	@GetMapping("/secure/update")
	public String goUpdate(Model model) {
		
		// get employ id dropdown list 
		List<Employee> employeeList = da.getEmployeeList();
	    model.addAttribute("employeeList", employeeList);
		return "/secure/update"; 
	}
	
	@GetMapping("/secure/delete")
	public String goDelete(Model model) {

		// get employ id dropdown list 
		List<Employee> employeeList = da.getEmployeeList();
		
	    model.addAttribute("employeeList", employeeList);
	    
		return"/secure/delete"; 
	}
	
	@GetMapping("/noPermission")
	public String permissionDenied() {
		return "/error/noPermission";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "/login";
	}
	
	@GetMapping("/secure")
	public String secureIndex(Model model, Authentication authentication) {
		
		if (authentication != null) {
	        String email = authentication.getName();

	        List<String> roleList = new ArrayList<>();
	        for (GrantedAuthority ga : authentication.getAuthorities()) {
	            roleList.add(ga.getAuthority());
	        }

	        model.addAttribute("email", email);
	        model.addAttribute("roleList", roleList);
	    }
	    return "/secure/index";
	}
	
	
	@PostMapping("/insertEmp") 
	public String InsertEmp(Model model, @ModelAttribute Employee employee){
		

		da.insertEmployee(employee); 
		
		System.out.println(employee.getEmail()); 
		System.out.println(employee.getFirst_name()); 
		
		model.addAttribute("employee", new Employee()); 
		model.addAttribute("msg", "Employee inserted"); 
		
		return "/secure/insert"; 
	}
	
	@PostMapping ("/deleteEmp")
	public String deleteEmp(Model model, @RequestParam Long empid) {
		
		da.deleteEmployeeById(empid); 
		
		System.out.println("Employee : " + empid + " deleted");
		List<Employee> employeeList = da.getEmployeeList();
		
	    model.addAttribute("employeeList", employeeList);
		
		model.addAttribute("msg", "Employee deleted"); 
		
		return "/secure/delete"; 
	}
	
	
	@PostMapping("/updateEmpSal")
	public String updateEmpSal(@RequestParam Long empid, @RequestParam Float newSal, Model model) {
		
		da.updateEmployeeSal(empid, newSal); 
		model.addAttribute("empid", empid);
		model.addAttribute("newSal", newSal);
		
		model.addAttribute("msg", "Employee salary updated"); 
		
		return  "/secure/update"; 
	}
}
