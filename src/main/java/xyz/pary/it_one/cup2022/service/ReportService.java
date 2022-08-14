package xyz.pary.it_one.cup2022.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pary.it_one.cup2022.entity.Report;
import xyz.pary.it_one.cup2022.entity.ReportColumn;
import xyz.pary.it_one.cup2022.entity.ReportTable;
import xyz.pary.it_one.cup2022.repository.ReportRepository;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final TableService tableService;

    @Transactional
    public void createReport(Report r) {
        reportRepository.save(r);
    }

    @Transactional(readOnly = true)
    public Report getReport(int id) {
        Report r = reportRepository.findById(id).orElse(null);
        if (r == null) {
            return r;
        }
        r.setTables(r.getTables().stream().distinct().toList());
        for (ReportTable rt : r.getTables()) {
            for (ReportColumn rc : rt.getColumns()) {
                rc.setSize(tableService.getNotNullCount(rt.getTableName(), rc.getTitle()));
            }
        }
        return r;
    }
//
//    @Transactional
//    public void deleteTable(String name) {
//        tableRepository.delete(name);
//        tableQueryService.deleteQueriesByTableName(name);
//    }
}
