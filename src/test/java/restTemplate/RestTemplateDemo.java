package restTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RestTemplateDemo {

	public static RestTemplate restTemplate;

	@BeforeMethod
	public static void setUpRestTemplate() {
		restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		// Add the Jackson Message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		// Note: here we are making this converter to process any kind of response,
		// not only application/*json, which is the default behaviour
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
	}

	@Test()
	public void getEmployeeDetailsByIdString() throws Exception {
		int employeeId = 4648;
		String employeeResourceUrl = "http://dummy.restapiexample.com/api/v1/employee/" +employeeId ;
		ResponseEntity<String> response = restTemplate.getForEntity(employeeResourceUrl, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		System.out.println(response.getBody().toString());
	}

	@Test()
	public void getEmployeesByExchange() throws Exception {
		ResponseEntity<List<Employee>> response = restTemplate.exchange(
				"http://dummy.restapiexample.com/api/v1/employees", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Employee>>() {
				});
		List<Employee> employees = response.getBody();
		System.out.println(employees.size());
		employees.forEach(employee -> System.out.println(employee.getEmployee_age()));
		employees.forEach(employee -> System.out.println(employee.getEmployee_name()));
		employees.forEach(employee -> System.out.println(employee.getEmployee_salary()));
	}

	@Test()
	public void getEmployeesByObject() throws Exception {
		String employeeResourceUrl = "http://dummy.restapiexample.com/api/v1/employees";
		Employee[] employees = restTemplate.getForObject(employeeResourceUrl, Employee[].class);
		assertThat(employees, notNullValue());
	}

	@Test()
	public void getEmployeeDetailsByIdEmployee() throws Exception {
		int employeeId = 4648;
		String employeeResourceUrl = "http://dummy.restapiexample.com/api/v1/employee/" +employeeId ;
		ResponseEntity<Employee> response = restTemplate.getForEntity(employeeResourceUrl, Employee.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		System.out.println(response.getBody().toString());
		
		System.out.println(response.getHeaders());
		
		System.out.println(response.getBody().getEmployee_age());
		System.out.println(response.getBody().getEmployee_name());
		System.out.println(response.getBody().getEmployee_salary());
        
	}
}
