package xyz.pary.it_one.cup2022.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import xyz.pary.it_one.cup2022.entity.Report;
import xyz.pary.it_one.cup2022.entity.ReportColumn;
import xyz.pary.it_one.cup2022.entity.ReportTable;
import xyz.pary.it_one.cup2022.model.Column;
import xyz.pary.it_one.cup2022.model.Table;
import xyz.pary.it_one.cup2022.repository.ReportRepository;
import xyz.pary.it_one.cup2022.repository.TableRepository;
import xyz.pary.it_one.cup2022.util.Types;

@RequiredArgsConstructor
@Component
public class ReportValidator implements Validator {

    private final ReportRepository reportRepository;
    private final TableRepository tableRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Report.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors e) {
        Report r = (Report) o;

        if (r.getTableAmount() != r.getTables().size()) {
            e.rejectValue("tableAmount", "tableAmount");
            return;
        }
        if (reportRepository.existsById(r.getReportId())) {
            e.reject("exists");
            return;
        }
        for (ReportTable rt : r.getTables()) {
            Table t;
            try {
                t = tableRepository.get(rt.getTableName());
            } catch (Exception ex) {
                t = null;
            }
            if (t == null) {
                e.reject("table not found");
                return;
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
                    e.reject("column not found or wrong type. Table: " + rt.getTableName() + " Col: " + rc.getTitle() + " Type: " + rc.getType());
                    return;
                }
            }
        }
    }

}
