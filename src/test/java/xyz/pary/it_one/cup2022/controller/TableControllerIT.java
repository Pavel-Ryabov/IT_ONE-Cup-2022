package xyz.pary.it_one.cup2022.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TableControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    {
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    public void testCreate1() throws Exception {
        System.out.println("TableControllerIT testCreate1");
        String url = "http://localhost:" + this.port + "/api/table/create-table";
        String t1 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("table1.json").toURI()));
        HttpStatus st1 = this.restTemplate.postForEntity(url, new HttpEntity(t1, headers), Void.class).getStatusCode();
        System.out.println("t1: " + st1);
        assertEquals(HttpStatus.CREATED, st1);
    }

    @Test
    public void testCreate2() throws Exception {
        System.out.println("TableControllerIT testCreate2");
        String url = "http://localhost:" + this.port + "/api/table/create-table";
        String t2 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("table2.json").toURI()));
        HttpStatus st2 = this.restTemplate.postForEntity(url, new HttpEntity(t2, headers), Void.class).getStatusCode();
        System.out.println("t2: " + st2);
        assertEquals(HttpStatus.CREATED, st2);
    }

    @Test
    public void testCreate3() throws Exception {
        System.out.println("TableControllerIT testCreate3");
        String url = "http://localhost:" + this.port + "/api/table/create-table";
        String t3 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("table3.json").toURI()));
        HttpStatus st3 = this.restTemplate.postForEntity(url, new HttpEntity(t3, headers), Void.class).getStatusCode();
        System.out.println("t3: " + st3);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st3);
    }

    @Test
    public void testCreate4() throws Exception {
        System.out.println("TableControllerIT testCreate4");
        String url = "http://localhost:" + this.port + "/api/table/create-table";
        String t4 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("table4.json").toURI()));
        HttpStatus st4 = this.restTemplate.postForEntity(url, new HttpEntity(t4, headers), Void.class).getStatusCode();
        System.out.println("t4: " + st4);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st4);
    }

    @Test
    public void testGet() throws Exception {
        System.out.println("TableControllerIT testGet1");
        testCreate1();
        String url1 = "http://localhost:" + this.port + "/api/table/get-table-by-name/Customer";
        String r1 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("table1-result.json").toURI()));
        String t1 = this.restTemplate.getForObject(url1, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r1), om.readTree(t1));
        System.out.println("TableControllerIT testGet2");
        testCreate2();
        String url2 = "http://localhost:" + this.port + "/api/table/get-table-by-name/Artists";
        String r2 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("table2-result.json").toURI()));
        String t2 = this.restTemplate.getForObject(url2, String.class);
        assertEquals(om.readTree(r2), om.readTree(t2));
        System.out.println("TableControllerIT testGet3");
        String url3 = "http://localhost:" + this.port + "/api/table/get-table-by-name/qweqwe";
        String r3 = null;
        ResponseEntity<String> e3 = this.restTemplate.getForEntity(url3, String.class);
        assertEquals(HttpStatus.OK, e3.getStatusCode());
        assertEquals(r3, e3.getBody());
        String url4 = "http://localhost:" + this.port + "/api/table/get-table-by-name/qweqweqweqweqweqweqweqweqweqweqw"
                + "eqweqweqweqweqweqweqweqweqweqweqw";
        ResponseEntity<String> e4 = this.restTemplate.getForEntity(url4, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, e4.getStatusCode());
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("TableControllerIT testDelete");
        testCreate1();
        String url = "http://localhost:" + this.port + "/api/table/drop-table/Customer";
        String addQueryUrl = "http://localhost:" + this.port + "/api/table-query/add-new-query-to-table";
        String q1 = "{\"queryId\":1,\"tableName\":\"Customer\",\"query\":\"раз dva\"}";
        this.restTemplate.postForEntity(addQueryUrl, new HttpEntity(q1, headers), Void.class);
        HttpStatus st1 = this.restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class).getStatusCode();
        assertEquals(HttpStatus.CREATED, st1);
        String getQueryUrl = "http://localhost:" + this.port + "/api/table-query/get-table-query-by-id/1";
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, this.restTemplate.getForEntity(getQueryUrl, Void.class).getStatusCode());
        HttpStatus st2 = this.restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class).getStatusCode();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st2);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setRestTemplate(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
