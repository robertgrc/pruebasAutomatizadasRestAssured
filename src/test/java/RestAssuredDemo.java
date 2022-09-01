
import Constants.UserEndPoints;
import Constants.URLS;
import Entities.User;
import Utils.Requests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class RestAssuredDemo {

    @Test
    public void getPostTest() {
        RestAssured.baseURI=URLS.BASE_URL;
        Response response = Requests.get(UserEndPoints.GET_POSTS);
        response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(0));
    }

    @Test
    public void getPostWithIdTest() {
        RestAssured.baseURI=URLS.BASE_URL;
        Response response = Requests.getWithId(UserEndPoints.GET_POST,"1");
        //response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.equalTo(4));
        response.then().assertThat().body("userId", Matchers.equalTo(1));
        response.then().assertThat().body("id", Matchers.equalTo(1));
        response.then().assertThat().body("title", Matchers.equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
        response.then().assertThat().body("body", Matchers.equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

    @Test
    public void postPostTest() throws JsonProcessingException {
        RestAssured.baseURI=URLS.BASE_URL;
        User user = new User();
        user.setUserId("7");
        user.setId("7");
        user.setTitle("magnam facilis autem");
        user.setBody("dolore placeat quibusdam ea quo vitae\\nmagni quis enim qui quis quo nemo aut saepe\\nquidem repellat excepturi ut quia\\nsunt ut sequi eos ea sed quas");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(user);
        Response response = Requests.post(UserEndPoints.POST_POST,payload);
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("userId",Matchers.equalTo(user.getUserId()));
        response.then().assertThat().body("id",Matchers.equalTo(101));
        response.then().assertThat().body("title", Matchers.equalTo(user.getTitle()));
        response.then().assertThat().body("body",Matchers.equalTo(user.getBody()));
    }

    @Test
    public void putPostsWithIdTest() throws JsonProcessingException{
        RestAssured.baseURI=URLS.BASE_URL;

        User user = new User();
        user.setUserId("1");
        user.setTitle("update");
        user.setBody("body");

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(user);

        Response response = Requests.put(UserEndPoints.PUT_POST,"1",payload);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("id", Matchers.equalTo(1));
        response.then().assertThat().body("userId",Matchers.equalTo(user.getUserId()));
        response.then().assertThat().body("title", Matchers.equalTo(user.getTitle()));
        response.then().assertThat().body("body",Matchers.equalTo(user.getBody()));
    }

    @Test
    public void deletePostsWithIdTest(){
        RestAssured.baseURI=URLS.BASE_URL;

        Response response = Requests.delete(UserEndPoints.DELETE_POST,"1");
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("id",Matchers.equalTo(null));
        response.then().assertThat().body("userId",Matchers.equalTo(null));
        response.then().assertThat().body("title", Matchers.equalTo(null));
        response.then().assertThat().body("body",Matchers.equalTo(null));
    }
}
