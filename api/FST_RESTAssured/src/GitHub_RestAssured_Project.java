import static io.restassured.RestAssured.given;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
public class GitHub_RestAssured_Project {
	// Declare request specification
	RequestSpecification requestSpec;
	// Declare response specification
	ResponseSpecification responseSpec;
	// Global properties
	String sshKey;
	int sshKeyId;
	@BeforeClass
	public void setUp() {
		// Create request specification
		requestSpec = new RequestSpecBuilder()
				// Set content type
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", "token xxx")
				// Set base URL
				.setBaseUri("https://api.github.com")
				// Build request specification
				.build();
		sshKey = "ssh-rsa xxx";
	}
	@Test(priority = 1)
	// Test case using a DataProvider
	public void addKeys() {
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + sshKey + "\" }";
		Response response = given().spec(requestSpec) // Use requestSpec
				.body(reqBody) // Send request body
				.when().post("/user/keys"); // Send POST request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		sshKeyId = response.then().extract().path("id");
		// Assertions
		response.then().statusCode(201);
	}
	@Test(priority = 2)
	// Test case using a DataProvider
	public void getKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.when().get("/user/keys"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(200);
	}
	@Test(priority = 3)
	// Test case using a DataProvider
	public void deleteKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.pathParam("keyId", sshKeyId).when().delete("/user/keys/{keyId}"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		// Assertions
		response.then().statusCode(204);
	}
}