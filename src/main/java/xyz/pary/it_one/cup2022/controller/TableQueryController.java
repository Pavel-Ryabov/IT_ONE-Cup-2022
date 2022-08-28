package xyz.pary.it_one.cup2022.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pary.it_one.cup2022.entity.TableQuery;
import xyz.pary.it_one.cup2022.model.Table;
import xyz.pary.it_one.cup2022.service.TableQueryService;
import xyz.pary.it_one.cup2022.service.TableService;

@RestController
@RequestMapping("/api/table-query")
@RequiredArgsConstructor
public class TableQueryController {

    private final TableQueryService tableQueryService;
    private final TableService tableService;

    @PostMapping("/add-new-query-to-table")
    public ResponseEntity<Void> add(@RequestBody @Valid TableQuery tq, BindingResult br) {
        if (br.hasErrors() || tableService.getTable(tq.getTableName()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            tableQueryService.addQuery(tq);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/modify-query-in-table")
    public ResponseEntity<Void> modify(@RequestBody @Valid TableQuery tq, BindingResult br) {
        if (br.hasErrors() || tableService.getTable(tq.getTableName()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            tableQueryService.modifyQuery(tq);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete-table-query-by-id/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            tableQueryService.deleteQuery(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/execute-table-query-by-id/{id}")
    public ResponseEntity<Void> execute(@PathVariable int id) {
        try {
            tableQueryService.executeQuery(id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get-all-queries-by-table-name/{name}")
    public ResponseEntity<List<TableQuery>> getAllByTableName(@PathVariable String name) {
        try {
            Table t = tableService.getTable(name);
//            if (t == null) {
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
            return new ResponseEntity<>(tableQueryService.getAllQueries(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get-table-query-by-id/{id}")
    public ResponseEntity<TableQuery> getById(@PathVariable int id) {
        TableQuery q = tableQueryService.getById(id);
        if (q == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(q, HttpStatus.OK);

    }

    @GetMapping("/get-all-table-queries")
    public ResponseEntity<List<TableQuery>> getAll() {
        return new ResponseEntity<>(tableQueryService.getAllQueries(), HttpStatus.OK);
    }
}
