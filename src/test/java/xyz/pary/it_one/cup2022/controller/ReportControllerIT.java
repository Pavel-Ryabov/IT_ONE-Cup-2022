package xyz.pary.it_one.cup2022.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import xyz.pary.it_one.cup2022.model.Table;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReportControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

    {
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
    }

    @BeforeEach
    public void createTables() throws Exception {
        String url = "http://localhost:" + this.port + "/api/table/create-table";
        String tablesJson = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("tables-for-reports.json").toURI()));
        List<Table> tables = new ObjectMapper().readValue(tablesJson, new TypeReference<List<Table>>() {
        });
        for (Table t : tables) {
            this.restTemplate.postForEntity(url, new HttpEntity(t, headers), Void.class);
        }
        addRecords();
    }

    private void addRecords() throws Exception {
        String addUrl = "http://localhost:" + this.port + "/api/single-query/add-new-query";
        String q1 = "{\"queryId\":1,\"query\":\"insert into artists values(1, 'art1', null)\"}";
        String q2 = "{\"queryId\":2,\"query\":\"insert into artists values(2, 'art2', 30)\"}";
        this.restTemplate.postForEntity(addUrl, new HttpEntity(q1, headers), Void.class);
        this.restTemplate.postForEntity(addUrl, new HttpEntity(q2, headers), Void.class);
        String execUrl1 = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/1";
        String execUrl2 = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/2";
        this.restTemplate.getForEntity(execUrl1, Void.class);
        this.restTemplate.getForEntity(execUrl2, Void.class);
    }

    @Test
    public void testCreate1() throws Exception {
        System.out.println("ReportControllerIT testCreate1");
        String url = "http://localhost:" + this.port + "/api/report/create-report";
        String r1 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report1.json").toURI()));
        HttpStatus st1 = this.restTemplate.postForEntity(url, new HttpEntity(r1, headers), Void.class).getStatusCode();
        System.out.println("r1: " + st1);
        assertEquals(HttpStatus.CREATED, st1);
    }

    @Test
    public void testCreate2() throws Exception {
        System.out.println("ReportControllerIT testCreate2");
        String url = "http://localhost:" + this.port + "/api/report/create-report";
        String r2 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report2.json").toURI()));
        HttpStatus st2 = this.restTemplate.postForEntity(url, new HttpEntity(r2, headers), Void.class).getStatusCode();
        System.out.println("r2: " + st2);
        assertEquals(HttpStatus.CREATED, st2);
    }

    @Test
    public void testCreate3() throws Exception {
        System.out.println("ReportControllerIT testCreate3");
        String url = "http://localhost:" + this.port + "/api/report/create-report";
        String r3 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report3.json").toURI()));
        HttpStatus st3 = this.restTemplate.postForEntity(url, new HttpEntity(r3, headers), Void.class).getStatusCode();
        System.out.println("r3: " + st3);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st3);
    }

    @Test
    public void testCreate4() throws Exception {
        System.out.println("ReportControllerIT testCreate4");
        String url = "http://localhost:" + this.port + "/api/report/create-report";
        String r4 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report4.json").toURI()));
        HttpStatus st4 = this.restTemplate.postForEntity(url, new HttpEntity(r4, headers), Void.class).getStatusCode();
        System.out.println("r4: " + st4);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st4);
    }

    @Test
    public void testCreate5() throws Exception {
        System.out.println("ReportControllerIT testCreate5");
        String url = "http://localhost:" + this.port + "/api/report/create-report";
        String r5 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report5.json").toURI()));
        HttpStatus st5 = this.restTemplate.postForEntity(url, new HttpEntity(r5, headers), Void.class).getStatusCode();
        System.out.println("r5: " + st5);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st5);
    }

    @Test
    public void testCreate6() throws Exception {
        System.out.println("ReportControllerIT testCreate6");
        String url = "http://localhost:" + this.port + "/api/report/create-report";
        String r6 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report1.json").toURI()));
        HttpStatus st6 = this.restTemplate.postForEntity(url, new HttpEntity(r6, headers), Void.class).getStatusCode();
        System.out.println("r6-1: " + st6);
        assertEquals(HttpStatus.CREATED, st6);
        st6 = this.restTemplate.postForEntity(url, new HttpEntity(r6, headers), Void.class).getStatusCode();
        System.out.println("r6-2: " + st6);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st6);
    }

    @Test
    public void testCreate7() throws Exception {
        System.out.println("ReportControllerIT testCreate6");
        String url = "http://localhost:" + this.port + "/api/report/create-report";
        String r71 = "{\"reportId\":1,\"tableAmount\":1,\"tables\":[{\"tableName\":\"Artists\",\"columns\":"
                + "[{\"title\":\"id\",\"type\":\"int4\"},{\"title\":\"name\",\"type\":\"varchar\"},{\"title\":\"age\",\"type\":\"int4\"}]}]}";
        String r72 = "{\"reportId\":2,\"tableAmount\":2,\"tables\":[{\"tableName\":\"Artists\",\"columns\":"
                + "[{\"title\":\"id\",\"type\":\"int4\"},{\"title\":\"name\",\"type\":\"varchar\"},{\"title\":\"age\",\"type\":\"int4\"}]},"
                + "{\"tableName\":\"Job\",\"columns\":[{\"title\":\"id\",\"type\":\"int4\"},{\"title\":\"name\","
                + "\"type\":\"varchar\"},{\"title\":\"salary\",\"type\":\"int4\"},{\"title\":\"address\",\"type\":\"varchar\"}]}]}";
        String r73 = "{\"reportId\":3,\"tableAmount\":2,\"tables\":[{\"tableName\":\"Artists\",\"columns\":"
                + "[{\"title\":\"id\",\"type\":\"int4\"},{\"title\":\"name\",\"type\":\"varchar\"},{\"title\":\"age\",\"type\":\"int4\"}]},"
                + "{\"tableName\":\"Artist\",\"columns\":[{\"title\":\"id\",\"type\":\"int4\"},{\"title\":\"name\",\"type\":\"varchar\"}]}]}";
        HttpStatus st71 = this.restTemplate.postForEntity(url, new HttpEntity(r71, headers), Void.class).getStatusCode();
        System.out.println("r7-1: " + st71);
        assertEquals(HttpStatus.CREATED, st71);
        HttpStatus st72 = this.restTemplate.postForEntity(url, new HttpEntity(r72, headers), Void.class).getStatusCode();
        System.out.println("r7-2: " + st72);
        assertEquals(HttpStatus.CREATED, st72);
        HttpStatus st73 = this.restTemplate.postForEntity(url, new HttpEntity(r73, headers), Void.class).getStatusCode();
        System.out.println("r7-3: " + st73);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, st73);
    }
//{"reportId":1,"tableAmount":1,"tables":[{"tableName":"Artists","columns":[{"title":"id","type":"int4"},{"title":"name","type":"varchar"},{"title":"age","type":"int4"}]}]}
//{"reportId":2,"tableAmount":2,"tables":[{"tableName":"Artists","columns":[{"title":"id","type":"int4"},{"title":"name","type":"varchar"},{"title":"age","type":"int4"}]},{"tableName":"Job","columns":[{"title":"id","type":"int4"},{"title":"name","type":"varchar"},{"title":"salary","type":"int4"},{"title":"address","type":"varchar"}]}]}
//{"reportId":3,"tableAmount":2,"tables":[{"tableName":"Artists","columns":[{"title":"id","type":"int4"},{"title":"name","type":"varchar"},{"title":"age","type":"int4"}]},{"tableName":"Artist","columns":[{"title":"id","type":"int4"},{"title":"name","type":"varchar"}]}]}

    @Test
    public void testGet() throws Exception {
        System.out.println("ReportControllerIT testGet1");
        testCreate2();
        String url1 = "http://localhost:" + this.port + "/api/report/get-report-by-id/2";
        String r1 = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report2-result.json").toURI()));
        ResponseEntity<String> e1 = this.restTemplate.getForEntity(url1, String.class);
        assertEquals(HttpStatus.CREATED, e1.getStatusCode());
        ObjectMapper om = new ObjectMapper();
        assertEquals(om.readTree(r1), om.readTree(e1.getBody()));
        System.out.println("ReportControllerIT testGet2");
        String url2 = "http://localhost:" + this.port + "/api/report/get-report-by-id/5";
        ResponseEntity<String> e2 = this.restTemplate.getForEntity(url2, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, e2.getStatusCode());
    }
    
    @Test
    public void testRenameTable() throws Exception {
        System.out.println("ReportControllerIT testRenameTable");
        testCreate2();
        String addUrl = "http://localhost:" + this.port + "/api/single-query/add-new-query";
        String renameQuery = "{\"queryId\":20,\"query\":\"alter table Artists rename to Customer\"}";
        this.restTemplate.postForEntity(addUrl, new HttpEntity(renameQuery, headers), Void.class);
        String execUrl = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/20";
        this.restTemplate.getForEntity(execUrl, Void.class);
        String url = "http://localhost:" + this.port + "/api/report/get-report-by-id/2";
        String r = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report2-result.json").toURI()));
        ResponseEntity<String> e1 = this.restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, e1.getStatusCode());
    }
    
    @Test
    public void testRenameColumn() throws Exception {
        System.out.println("ReportControllerIT testRenameColumn");
        testCreate2();
        String addUrl = "http://localhost:" + this.port + "/api/single-query/add-new-query";
        String renameQuery = "{\"queryId\":20,\"query\":\"alter table Artists alter column id rename to artistId\"}";
        this.restTemplate.postForEntity(addUrl, new HttpEntity(renameQuery, headers), Void.class);
        String execUrl = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/20";
        this.restTemplate.getForEntity(execUrl, Void.class);        
        String url = "http://localhost:" + this.port + "/api/report/get-report-by-id/2";
        String r = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report2-result.json").toURI()));
        ResponseEntity<String> e1 = this.restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, e1.getStatusCode());
    }
    
    @Test
    public void testChangeType() throws Exception {
        System.out.println("ReportControllerIT testChangeType");
        testCreate2();
        String addUrl = "http://localhost:" + this.port + "/api/single-query/add-new-query";
        String renameQuery = "{\"queryId\":20,\"query\":\"alter table Artists alter column id set data type varchar\"}";
        this.restTemplate.postForEntity(addUrl, new HttpEntity(renameQuery, headers), Void.class);
        String execUrl = "http://localhost:" + this.port + "/api/single-query/execute-single-query-by-id/20";
        this.restTemplate.getForEntity(execUrl, Void.class);        
        String url = "http://localhost:" + this.port + "/api/report/get-report-by-id/2";
        String r = Files.readString(Paths.get(this.getClass().getClassLoader().getResource("report2-result.json").toURI()));
        ResponseEntity<String> e1 = this.restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.NOT_ACCEPTABLE, e1.getStatusCode());
    }

}
