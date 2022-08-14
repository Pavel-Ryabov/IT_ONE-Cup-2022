package xyz.pary.it_one.cup2022.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import xyz.pary.it_one.cup2022.entity.TableQuery;

public interface TableQueryRepository extends CrudRepository<TableQuery, Integer> {

    public void deleteByTableName(String tableName);

    public List<TableQuery> findAllByTableName(String tableName);

    @Override
    public List<TableQuery> findAll();
}
