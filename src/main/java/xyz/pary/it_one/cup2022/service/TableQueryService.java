package xyz.pary.it_one.cup2022.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pary.it_one.cup2022.entity.Query;
import xyz.pary.it_one.cup2022.entity.TableQuery;
import xyz.pary.it_one.cup2022.repository.TableQueryRepository;

@Service
@RequiredArgsConstructor
public class TableQueryService {

    private final Pattern renamePattern = Pattern.compile(
            "alter[\\s]+table[\\s]+(.+?)[\\s]+rename[\\s]+to[\\s]+(.+?)(;|$)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
    );
    private final Pattern dropPattern = Pattern.compile(
            "drop[\\s]+table[\\s]+(.+?)(;|$)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
    );

    private final TableQueryRepository tableQueryRepository;
    private final QueryExecutor queryExecutor;

    @Transactional
    public void addQuery(TableQuery query) {
        tableQueryRepository.save(query);
    }

    @Transactional
    public void modifyQuery(TableQuery query) {
        if (!tableQueryRepository.existsById(query.getQueryId())) {
            throw new RuntimeException();
        }
        tableQueryRepository.save(query);
    }

    @Transactional
    public void deleteQuery(int id) {
        tableQueryRepository.deleteById(id);
    }

    @Transactional
    public void deleteQueriesByTableName(String tableName) {
        tableQueryRepository.deleteByTableName(tableName);
    }

    @Transactional
    public void executeQuery(int id) {
        TableQuery q = tableQueryRepository.findById(id).get();
        queryExecutor.execute(q);
        checkTables(q);
    }

    @Transactional(readOnly = true)
    public List<TableQuery> getAllQueries(String tableName) {
        return tableQueryRepository.findAllByTableName(tableName);
    }

    @Transactional(readOnly = true)
    public TableQuery getById(int id) {
        return tableQueryRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<TableQuery> getAllQueries() {
        return tableQueryRepository.findAll();
    }

    @Transactional
    public void checkTables(Query q) {
        String query = q.getQuery();
        Matcher m = dropPattern.matcher(query);
        while (m.find()) {
            tableQueryRepository.deleteByTableName(m.group(1).trim());
        }
        m = renamePattern.matcher(query);
        while (m.find()) {
            tableQueryRepository.updateTableName(m.group(1).trim(), m.group(2).trim());
        }
    }
}
