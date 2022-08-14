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
import xyz.pary.it_one.cup2022.entity.SingleQuery;
import xyz.pary.it_one.cup2022.service.SingleQueryService;

@RestController
@RequestMapping("/api/single-query")
@RequiredArgsConstructor
public class SingleQueryController {

    private final SingleQueryService singleQueryService;

    @PostMapping("/add-new-query")
    public ResponseEntity<Void> add(@RequestBody @Valid SingleQuery q, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            singleQueryService.addQuery(q);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modify-single-query")
    public ResponseEntity<Void> modify(@RequestBody @Valid SingleQuery q, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            singleQueryService.modifyQuery(q);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/delete-single-query-by-id/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            singleQueryService.deleteQuery(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/execute-single-query-by-id/{id}")
    public ResponseEntity<Void> execute(@PathVariable int id) {
        try {
            singleQueryService.executeQuery(id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get-single-query-by-id/{id}")
    public ResponseEntity<SingleQuery> getById(@PathVariable int id) {
        SingleQuery q = singleQueryService.getById(id);
        if (q == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(q, HttpStatus.OK);

    }

    @GetMapping("/get-all-single-queries")
    public ResponseEntity<List<SingleQuery>> getAll() {
        return new ResponseEntity<>(singleQueryService.getAllQueries(), HttpStatus.OK);
    }
}
