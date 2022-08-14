package xyz.pary.it_one.cup2022.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SingleQueryControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    {
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void testAdd() throws Exception {
        System.out.println("SingleQueryControllerIT testAdd");
        String url = "http://localhost:" + this.port + "/api/single-query/add-new-query";
        String q1 = "{\"queryId\":1,\"query\":\"select * from Cars\"}";
        String q2 = "{\"queryId\":2,\"query\":\"select * from Motos\"}";
        String q3 = "{\"queryId\":3,\"query\":\"select * from Customer\"}";
        String q4 = "{\"queryId\":\"abc\",\"query\":\"раз dva\"}";
        String q5 = "{\"queryId\":6,\"query\":\"select * from Customerselect * from Customerselect * from Customerselect"
                + " * from Customerselect * from Customerselect * from Customerselect * from Customerselect * from Customerselect"
                + " * from Customerselect * from Customerselect * from Customerselect * from Customerselect * from Customerselect"
                + " * from Customerselect * from Customerselect * from Customerselect * from Customerselect * from Customerselect"
                + " * from Customerselect * from Customerselect * from Customerselect * from Customerselect * from Customer\"}";
        HttpStatus st1 = this.restTemplate.postForEntity(url, new HttpEntity(q1, headers), Void.class).getStatusCode();
        System.out.println("q1: " + st1);
        assertEquals(HttpStatus.CREATED, st1);
        HttpStatus st2 = this.restTemplate.postForEntity(url, new HttpEntity(q2, headers), Void.class).getStatusCode();
        System.out.println("q2: " + st2);
        assertEquals(HttpStatus.CREATED, st2);
        HttpStatus st3 = this.restTemplate.postForEntity(url, new HttpEntity(q3, headers), Void.class).getStatusCode();
        System.out.println("q3: " + st3);
        assertEquals(HttpStatus.CREATED, st3);
        HttpStatus st4 = this.restTemplate.postForEntity(url, new HttpEntity(q4, headers), Void.class).getStatusCode();
        System.out.println("q4: " + st4);
        assertEquals(HttpStatus.BAD_REQUEST, st4);
        HttpStatus st5 = this.restTemplate.postForEntity(url, new HttpEntity(q5, headers), Void.class).getStatusCode();
        System.out.println("q5: " + st5);
        assertEquals(HttpStatus.BAD_REQUEST, st5);
    }

    @Test
    public void testModify() throws Exception {
        System.out.println("SingleQueryControllerIT testModify");
        testAdd();
        String url = "http://localhost:" + this.port + "/api/single-query/modify-single-query";
        String q1 = "{\"queryId\":1,\"query\":\"select * from Planes\"}";
        String q2 = "{\"queryId\":6,\"query\":\"select * from Artists\"}";
        String q3 = "{\"queryId\":\"abc\",\"query\":\"select * from Planes\"}";
        HttpStatus st1 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q1, headers), Void.class).getStatusCode();
        System.out.println("mq1: " + st1);
        assertEquals(HttpStatus.OK, st1);
        HttpStatus st2 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q2, headers), Void.class).getStatusCode();
        System.out.println("mq2: " + st2);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st2);
        HttpStatus st3 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q3, headers), Void.class).getStatusCode();
        System.out.println("mq3: " + st3);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st3);
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("SingleQueryControllerIT testDelete1");
        testAdd();
        String url1 = "http://localhost:" + this.port + "/api/single-query/delete-single-query-by-id/1";
        String url2 = "http://localhost:" + this.port + "/api/single-query/delete-single-query-by-id/4";
        String url3 = "http://localhost:" + this.port + "/api/single-query/delete-single-query-by-id/qwe";
        HttpStatus st1 = this.restTemplate.exchange(url1, HttpMethod.DELETE, null, Void.class).getStatusCode();
        assertEquals(HttpStatus.ACCEPTED, st1);
        HttpStatus st2 = this.restTemplate.exchange(url2, HttpMethod.DELETE, null, Void.class).getStatusCode();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st2);
        HttpStatus st3 = this.restTemplate.exchange(url3, HttpMethod.DELETE, null, Void.class).getStatusCode();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st3);
    }

    @Test
    public void testExecute() throws Exception {
        System.out.println("SingleQueryControllerIT testExecute");
        testAdd();
        String createTableUrl = "http://localhost:" + this.port + "/api/table/create-table";
        String t1 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("table1.json").toURI()));
        this.restTemplate.postForEntity(createTableUrl, new HttpEntity(t1, headers), Void.class);
        String url1 = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/3";
        String url2 = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/4";
        String url3 = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/qwe";
        String url4 = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/1";
        HttpStatus st1 = this.restTemplate.getForEntity(url1, Void.class).getStatusCode();
        assertEquals(HttpStatus.CREATED, st1);
        HttpStatus st2 = this.restTemplate.getForEntity(url2, Void.class).getStatusCode();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st2);
        HttpStatus st3 = this.restTemplate.getForEntity(url3, Void.class).getStatusCode();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st3);
        HttpStatus st4 = this.restTemplate.getForEntity(url4, Void.class).getStatusCode();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st4);
    }

    @Test
    public void testGetById() throws Exception {
        System.out.println("SingleQueryControllerIT testGetById");
        testAdd();
        String url1 = "http://localhost:" + this.port + "/api/single-query/get-single-query-by-id/3";
        String r1 = "{\"queryId\":3,\"query\":\"select * from Customer\"}";
        String q1 = this.restTemplate.getForObject(url1, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r1), om.readTree(q1));
        String url2 = "http://localhost:" + this.port + "/api/single-query/get-single-query-by-id/5";
        ResponseEntity<Void> e2 = this.restTemplate.getForEntity(url2, Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e2.getStatusCode());
    }

    @Test
    public void testGetAll() throws Exception {
        System.out.println("SingleQueryControllerIT testGetAll");
        String url = "http://localhost:" + this.port + "/api/single-query/get-all-single-queries";
        String r1 = "[]";
        String q1 = this.restTemplate.getForObject(url, String.class);
        assertEquals(r1, q1);
        testAdd();
        String r2 = "[{\"queryId\":1,\"query\":\"select * from Cars\"},"
                + "{\"queryId\":2,\"query\":\"select * from Motos\"},"
                + "{\"queryId\":3,\"query\":\"select * from Customer\"}]";
        String q2 = this.restTemplate.getForObject(url, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r2), om.readTree(q2));
    }
}
