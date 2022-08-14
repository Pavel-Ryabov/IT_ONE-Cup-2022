package xyz.pary.it_one.cup2022.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pary.it_one.cup2022.entity.TableQuery;
import xyz.pary.it_one.cup2022.repository.TableQueryRepository;

@Service
@RequiredArgsConstructor
public class TableQueryService {

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
}
