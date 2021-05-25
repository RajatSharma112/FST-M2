import org.hamcrest.Matcher;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import java.net.MalformedURLException;
import java.net.URL;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestPetAPI {
	@Test
    public void AddNewPet_first() {
        // Write the request body
        String reqBody = "{\"id\": 77252, \"name\": \"Jay\",  \"status\": \"sold\"}";

        Response response = 
            given().contentType(ContentType.JSON) 
            .body(reqBody).when().post(ROOT_URI); 

        String body = response.getBody().asPrettyString();
        System.out.println(body);
    }
    @Test
    public void GetPetDetails() {
        // Specify the base URL to the RESTful web service
        baseURI = "https://petstore.swagger.io/v2/pet";

       
        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .when().get("/findByStatus?status=sold"); // Run GET request

        
        String responseBody = response.getBody().asString();
        System.out.println("Response Body is =>  " + responseBody);

        // Assertions
        response.then().statusCode(200);
        response.then().body("[0].status", equalTo("sold"));
    }


    final static String ROOT_URI = "https://petstore.swagger.io/v2/pet";
    @Test
    public void AddNewPet() {
       
        String reqBody = "{\"id\": 77352, \"name\": \"Jay\",  \"status\": \"alive\"}";

        Response response = 
            given().contentType(ContentType.JSON) // Set headers
            .body(reqBody).when().post(ROOT_URI); // Send POST request

       
        String body = response.getBody().asPrettyString();
        System.out.println(body);
    }
    // Set Base URL
    @Test
public void GetAddedPet() {  
// Send GET Request
given().contentType(ContentType.JSON) 
.when().get(ROOT_URI + "/77352");

}
        @Test
        public void getPetInfo() throws MalformedURLException {

            // Generate response
            Response response = 
                given().contentType(ContentType.JSON) 
                .when().get(ROOT_URI + "/77352"); 
         
            // Print response
            System.out.println(response.asPrettyString());
         
         
            URL swaggerSchema = new URL("https://petstore.swagger.io/v2/swagger.json");
            response.then().body(matchesJsonSchema(swaggerSchema));
        }

		@Test
        public void DeletePet() {
            Response response = 
                given().contentType(ContentType.JSON) // Set headers
                .when().delete(ROOT_URI + "/77352"); // Send DELETE request
            
            response.then().body("code", equalTo(200));
            response.then().body("message", equalTo("77352"));
        }
}