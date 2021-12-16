package rw.ac.rca.termOneExam.controller;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import rw.ac.rca.termOneExam.domain.City;
import rw.ac.rca.termOneExam.utils.APICustomResponse;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class CityControllerIntegrationTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void getAll_success() throws JSONException {
        String response = this.restTemplate.getForObject("/api/cities/all", String.class);

        JSONAssert.assertEquals("[{id:101},{id:102},{id:103},{id:104}]", response, false);
    }
    @Test
    public void getById_successObject() {
        City city = this.restTemplate.getForObject("/api/cities/id/102", City.class);

        assertEquals("Musanze", city.getName());
        assertEquals("18",city.getWeather());
    }

    @Test
    public void getById_404() {
        ResponseEntity<APICustomResponse> city =
                this.restTemplate.getForEntity("/api/cities/id/1", APICustomResponse.class);

        assertTrue(city.getStatusCodeValue()==404);
        assertFalse(city.getBody().isStatus());
        assertEquals("City not found with id 1", city.getBody().getMessage());
    }

    @Test
    public void addCity_success() throws JSONException{
        ResponseEntity<City> city = this.restTemplate.postForEntity("/api/cities/id","{id:105}", City.class);
        assertNotEquals(105, city.getBody().getId());
    }

    @Test
    public  void  update(){
        City city=new City(101,"Huye",10,32);
        HttpEntity<City> itemUpdate =new HttpEntity<>(city);
        ResponseEntity <City> response=this.restTemplate.exchange("/api/cities/id/101", HttpMethod.PUT,itemUpdate,City.class);
    }
// Post Bad Request
}
