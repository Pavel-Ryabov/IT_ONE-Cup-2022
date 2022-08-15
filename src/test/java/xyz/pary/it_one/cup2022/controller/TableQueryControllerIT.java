package xyz.pary.it_one.cup2022.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class TableQueryControllerIT {

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
        System.out.println("TableQueryControllerIT testAdd");
        String url = "http://localhost:" + this.port + "/api/table-query/add-new-query-to-table";
        TableControllerIT tableControllerIT = new TableControllerIT();
        tableControllerIT.setPort(port);
        tableControllerIT.setRestTemplate(restTemplate);
        tableControllerIT.testCreate1();
        tableControllerIT.testCreate2();
        String q1 = "{\"queryId\":1,\"tableName\":\"Artists\",\"query\":\"раз dva\"}";
        String q2 = "{\"queryId\":2,\"tableName\":\"Artists\",\"query\":\"select * from Artists\"}";
        String q3 = "{\"queryId\":3,\"tableName\":\"Customer\",\"query\":\"select * from Customer\"}";
        String q4 = "{\"queryId\":1,\"tableName\":\"Artist\",\"query\":\"раз dva\"}";
        String q5 = "{\"queryId\":\"abc\",\"tableName\":\"Artists\",\"query\":\"select * from Artists\"}";
        String q6 = "{\"queryId\":6,\"tableName\":\"Artists\",\"query\":\"select * from Artistsselect * from Artistsselect * from "
                + "Artistsselect * from Artistsselect * from Artistsselect * from Artistsselect * from Artistsselect * from "
                + "Artistsselect * from Artistsselect * from Artistsselect * from Artistsselect * from Artistsselect * from "
                + "Artistsselect * from Artistsselect * from Artistsselect * from Artistsselect * from Artistsselect * from "
                + "Artistsselect * from Artistsselect * from Artistsselect * from Artistsselect * from Artists\"}";
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
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st4);
        HttpStatus st5 = this.restTemplate.postForEntity(url, new HttpEntity(q5, headers), Void.class).getStatusCode();
        System.out.println("q5: " + st5);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st5);
        HttpStatus st6 = this.restTemplate.postForEntity(url, new HttpEntity(q6, headers), Void.class).getStatusCode();
        System.out.println("q6: " + st6);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st6);
    }

    @Test
    public void testModify() throws Exception {
        System.out.println("TableQueryControllerIT testModify");
        testAdd();
        String url = "http://localhost:" + this.port + "/api/table-query/modify-query-in-table";
        String q1 = "{\"queryId\":1,\"tableName\":\"Artists\",\"query\":\"раз dva\"}";
        String q2 = "{\"queryId\":2,\"tableName\":\"Artists\",\"query\":\"select * from Artists\"}";
        String q3 = "{\"queryId\":3,\"tableName\":\"Customer\",\"query\":\"select * from Customer\"}";
        String q4 = "{\"queryId\":1,\"tableName\":\"Artist\",\"query\":\"раз dva\"}";
        String q5 = "{\"queryId\":\"abc\",\"tableName\":\"Artists\",\"query\":\"select * from Artists\"}";
        String q6 = "{\"queryId\":4,\"tableName\":\"Artists\",\"query\":\"select * from Artists\"}";
        HttpStatus st1 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q1, headers), Void.class).getStatusCode();
        System.out.println("mq1: " + st1);
        assertEquals(HttpStatus.OK, st1);
        HttpStatus st2 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q2, headers), Void.class).getStatusCode();
        System.out.println("mq2: " + st2);
        assertEquals(HttpStatus.OK, st2);
        HttpStatus st3 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q3, headers), Void.class).getStatusCode();
        System.out.println("mq3: " + st3);
        assertEquals(HttpStatus.OK, st3);
        HttpStatus st4 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q4, headers), Void.class).getStatusCode();
        System.out.println("mq4: " + st4);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st4);
        HttpStatus st5 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q5, headers), Void.class).getStatusCode();
        System.out.println("mq5: " + st5);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st5);
        HttpStatus st6 = this.restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity(q6, headers), Void.class).getStatusCode();
        System.out.println("mq6: " + st6);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st6);
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println("TableQueryControllerIT testDelete1");
        testAdd();
        String url1 = "http://localhost:" + this.port + "/api/table-query/delete-table-query-by-id/1";
        String url2 = "http://localhost:" + this.port + "/api/table-query/delete-table-query-by-id/4";
        String url3 = "http://localhost:" + this.port + "/api/table-query/delete-table-query-by-id/qwe";
        HttpStatus st1 = this.restTemplate.exchange(url1, HttpMethod.DELETE, null, Void.class).getStatusCode();
        assertEquals(HttpStatus.ACCEPTED, st1);
        HttpStatus st2 = this.restTemplate.exchange(url2, HttpMethod.DELETE, null, Void.class).getStatusCode();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st2);
        HttpStatus st3 = this.restTemplate.exchange(url3, HttpMethod.DELETE, null, Void.class).getStatusCode();
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st3);
    }

    @Test
    public void testExecute() throws Exception {
        System.out.println("TableQueryControllerIT testExecute");
        testAdd();
        String url1 = "http://localhost:" + this.port + "/api/table-query/execute-table-query-by-id/2";
        String url2 = "http://localhost:" + this.port + "/api/table-query/execute-table-query-by-id/22";
        String url3 = "http://localhost:" + this.port + "/api/table-query/execute-table-query-by-id/qwe";
        String url4 = "http://localhost:" + this.port + "/api/table-query/execute-table-query-by-id/1";
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
    public void testGetAllByTableName() throws Exception {
        System.out.println("TableQueryControllerIT testGetAllByTableName");
        testAdd();
        String url1 = "http://localhost:" + this.port + "/api/table-query/get-all-queries-by-table-name/Customer";
        String r1 = "[{\"queryId\":3,\"tableName\":\"Customer\",\"query\":\"select * from Customer\"}]";
        String q1 = this.restTemplate.getForObject(url1, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r1), om.readTree(q1));
        String url2 = "http://localhost:" + this.port + "/api/table-query/get-all-queries-by-table-name/qweqwe";
        String r2 = null;
        ResponseEntity<String> e2 = this.restTemplate.getForEntity(url2, String.class);
        assertEquals(HttpStatus.OK, e2.getStatusCode());
        assertEquals(r2, e2.getBody());
    }

    @Test
    public void testGetById() throws Exception {
        System.out.println("TableQueryControllerIT testGetById");
        testAdd();
        String url1 = "http://localhost:" + this.port + "/api/table-query/get-table-query-by-id/3";
        String r1 = "{\"queryId\":3,\"tableName\":\"Customer\",\"query\":\"select * from Customer\"}";
        String q1 = this.restTemplate.getForObject(url1, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r1), om.readTree(q1));
        String url2 = "http://localhost:" + this.port + "/api/table-query/get-table-query-by-id/5";
        ResponseEntity<Void> e2 = this.restTemplate.getForEntity(url2, Void.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, e2.getStatusCode());
    }

    @Test
    public void testGetAll() throws Exception {
        System.out.println("TableQueryControllerIT testGetAll");
        String url = "http://localhost:" + this.port + "/api/table-query/get-all-table-queries";
        String r1 = "[]";
        String q1 = this.restTemplate.getForObject(url, String.class);
        assertEquals(r1, q1);
        testAdd();
        String r2 = "[{\"queryId\":1,\"tableName\":\"Artists\",\"query\":\"раз dva\"},"
                + "{\"queryId\":2,\"tableName\":\"Artists\",\"query\":\"select * from Artists\"},"
                + "{\"queryId\":3,\"tableName\":\"Customer\",\"query\":\"select * from Customer\"}]";
        String q2 = this.restTemplate.getForObject(url, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r2), om.readTree(q2));
    }
    
    @Test
    public void testDeleteTable() throws Exception {
        System.out.println("TableQueryControllerIT testDeleteTable");
        testAdd();
        String addUrl = "http://localhost:" + this.port + "/api/table-query/add-new-query-to-table";
        String dropQuery = "{\"queryId\":10,\"tableName\":\"Customer\",\"query\":\"drop table Customer; select * from Artists\"}";
        this.restTemplate.postForEntity(addUrl, new HttpEntity(dropQuery, headers), Void.class);
        String execUrl = "http://localhost:" + this.port + "/api/table-query/execute-table-query-by-id/10";
        this.restTemplate.getForEntity(execUrl, Void.class);
        String r = "[{\"queryId\":1,\"tableName\":\"Artists\",\"query\":\"раз dva\"},"
                + "{\"queryId\":2,\"tableName\":\"Artists\",\"query\":\"select * from Artists\"}]";
        String getAllUrl = "http://localhost:" + this.port + "/api/table-query/get-all-table-queries";
        String q = this.restTemplate.getForObject(getAllUrl, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r), om.readTree(q));
    }
    
    @Test
    public void testRenameTable() throws Exception {
        System.out.println("TableQueryControllerIT testRenameTable");
        testAdd();
        String addUrl = "http://localhost:" + this.port + "/api/table-query/add-new-query-to-table";
        String renameQuery = "{\"queryId\":10,\"tableName\":\"Customer\",\"query\":\"alter table Customer rename to Artist;"
                + "select * from Artists; alter table Artists rename to Job;\"}";
        this.restTemplate.postForEntity(addUrl, new HttpEntity(renameQuery, headers), Void.class);
        String execUrl = "http://localhost:" + this.port + "/api/table-query/execute-table-query-by-id/10";
        this.restTemplate.getForEntity(execUrl, Void.class);
        String r = "[{\"queryId\":1,\"tableName\":\"Job\",\"query\":\"раз dva\"},"
                + "{\"queryId\":2,\"tableName\":\"Job\",\"query\":\"select * from Artists\"},"
                + "{\"queryId\":3,\"tableName\":\"Artist\",\"query\":\"select * from Customer\"},"
                + "{\"queryId\":10,\"tableName\":\"Artist\",\"query\":\"alter table Customer rename to Artist;"
                + "select * from Artists; alter table Artists rename to Job;\"}]";
        String getAllUrl = "http://localhost:" + this.port + "/api/table-query/get-all-table-queries";
        String q = this.restTemplate.getForObject(getAllUrl, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r), om.readTree(q));
    }
    
    @Test
    public void testRenameTableAndDeleteFromSingleQuery() throws Exception {
        System.out.println("TableQueryControllerIT testRenameTableAndDeleteFromSingleQuery");
        testRenameTable();
        String addUrl = "http://localhost:" + this.port + "/api/single-query/add-new-query";
        String dropQuery = "{\"queryId\":20,\"query\":\"drop table Artist\"}";
        this.restTemplate.postForEntity(addUrl, new HttpEntity(dropQuery, headers), Void.class);
        String execUrl = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/20";
        this.restTemplate.getForEntity(execUrl, Void.class);
        String r = "[{\"queryId\":1,\"tableName\":\"Job\",\"query\":\"раз dva\"},"
                + "{\"queryId\":2,\"tableName\":\"Job\",\"query\":\"select * from Artists\"}]";
        String getAllUrl = "http://localhost:" + this.port + "/api/table-query/get-all-table-queries";
        String q = this.restTemplate.getForObject(getAllUrl, String.class);
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r), om.readTree(q));
    }
}
