package xyz.pary.it_one.cup2022.controller;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.pary.it_one.cup2022.entity.Report;
import xyz.pary.it_one.cup2022.service.ReportService;
import xyz.pary.it_one.cup2022.validator.ReportValidator;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ReportValidator reportValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(reportValidator);
    }

    @PostMapping("/create-report")
    public ResponseEntity<Void> create(@RequestBody @Valid Report r, BindingResult br) {
        if (br.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        try {
            if (reportService.getReport(r.getReportId()) != null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            reportService.createReport(r);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/get-report-by-id/{id}")
    public ResponseEntity<Report> getById(@PathVariable int id) {
        try {
            Report r = reportService.getReport(id);
            if (r == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(r, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
