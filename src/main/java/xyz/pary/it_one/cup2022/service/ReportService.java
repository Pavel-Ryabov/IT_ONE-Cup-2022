package xyz.pary.it_one.cup2022.service;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pary.it_one.cup2022.entity.Report;
import xyz.pary.it_one.cup2022.entity.ReportColumn;
import xyz.pary.it_one.cup2022.entity.ReportTable;
import xyz.pary.it_one.cup2022.model.Column;
import xyz.pary.it_one.cup2022.model.Table;
import xyz.pary.it_one.cup2022.repository.ReportRepository;
import xyz.pary.it_one.cup2022.util.Types;

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
        r.setTables(r.getTables().stream().distinct().collect(Collectors.toList()));
        for (ReportTable rt : r.getTables()) {
            Table t;
            try {
                t = tableService.getTable(rt.getTableName());
            } catch (Exception ex) {
                t = null;
            }
            if (t == null) {
                return null;
            }
            for (ReportColumn rc : rt.getColumns()) {
                boolean fc = false;
                boolean et = false;
                for (Column c : t.getColumnInfos()) {
                    if (c.getTitle().equals(rc.getTitle().toUpperCase())) {
                        fc = true;
                        if (c.getType().equals(Types.resolveType(rc.getType()))) {
                            et = true;
                        }
                        break;
                    }
                }
                if (!fc || !et) {
                    return null;
                }
                rc.setSize(tableService.getNotNullCount(rt.getTableName(), rc.getTitle()));
            }
        }
        return r;
    }

}
