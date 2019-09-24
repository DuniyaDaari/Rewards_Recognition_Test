package restApiAutomation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class FcApiTest {

	@Test
	public void testAllProductsApi() throws Exception {
		RequestSpecification rs = new RequestSpecBuilder().addHeader("Accept", "application/json").build();

		Response r = RestAssured.given(rs).when().get("http://localhost:9090/allProducts");

		System.out.println(r.getStatusCode());

		System.out.println(r.contentType());

		System.out.println(r.getBody().jsonPath().getList(""));

		List<HashMap<String, Object>> allProducts = r.getBody().jsonPath().getList("");

		List<Object> productNames = new ArrayList<Object>();
		
		for (HashMap<String, Object> product : allProducts) {

			System.out.println(product.get("name"));
			productNames.add( product.get("name"));
			
		}
		
		Assert.assertTrue(productNames.contains("Franklin Build India Direct Fund"));
	
		
		
	}
}
