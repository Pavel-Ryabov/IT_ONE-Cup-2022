package xyz.pary.it_one.cup2022.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pary.it_one.cup2022.model.Table;
import xyz.pary.it_one.cup2022.repository.TableRepository;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;
    private final TableQueryService tableQueryService;

    @Transactional
    public void createTable(Table table) {
        tableRepository.create(table);
    }

    @Transactional(readOnly = true)
    public Table getTable(String name) {
        return tableRepository.get(name);
    }

    @Transactional
    public void deleteTable(String name) {
        tableRepository.delete(name);
        tableQueryService.deleteQueriesByTableName(name);
    }

    @Transactional(readOnly = true)
    public int getNotNullCount(String tableName, String columnName) {
        return tableRepository.getNotNullCount(tableName, columnName);
    }
}
