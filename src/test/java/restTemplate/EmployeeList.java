package restTemplate;

import java.io.Serializable;
import java.util.List;

public class EmployeeList implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Employee> employees;

	public List<Employee> getEmployees() {
		return employees;
	}
	
}
