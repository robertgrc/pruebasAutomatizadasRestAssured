package Utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Requests {

    public static Response get(String endpoint){
        Response response = RestAssured
                .when().get(endpoint);
        response.then().log().body();
        return response;
    }

    public static Response getWithId(String endpoint, String id){

        Response response = RestAssured
                .given().pathParam("id", id)
                .when().get(endpoint);
        response.then().log().body();
        return response;
    }

    public static Response post(String endpoint, String payload){
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .when().post(endpoint);
        response.then().log().body();
        return response;
    }

    public static Response put(String endpoint,String id, String payload){
        Response response = RestAssured.
                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .and().pathParam("id",id)
                .when().put(endpoint);
        response.then().log().body();
        return response;
    }

    public static Response delete(String endpoint,String id){
        Response response = RestAssured
                .given().pathParam("id", id)
                .when().delete(endpoint);
        response.then().log().body();
        return response;
    }
}