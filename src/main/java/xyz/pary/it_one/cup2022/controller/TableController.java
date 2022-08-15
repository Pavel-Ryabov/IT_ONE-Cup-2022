package xyz.pary.it_one.cup2022.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pary.it_one.cup2022.model.Table;
import xyz.pary.it_one.cup2022.service.TableService;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping("/create-table")
    public ResponseEntity<Void> create(@RequestBody @Valid Table t, BindingResult br) {
        if (br.hasErrors() || t.getColumnsAmount() != t.getColumnInfos().size()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            tableService.createTable(t);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get-table-by-name/{name}")
    public ResponseEntity<Table> get(@PathVariable String name) {
        if (name.length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tableService.getTable(name), HttpStatus.OK);
    }

    @DeleteMapping("/drop-table/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        if (name.length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            tableService.deleteTable(name);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
