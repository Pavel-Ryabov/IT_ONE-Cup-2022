package xyz.pary.it_one.cup2022.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import xyz.pary.it_one.cup2022.entity.TableQuery;

public interface TableQueryRepository extends CrudRepository<TableQuery, Integer> {

    public void deleteByTableName(String tableName);

    public List<TableQuery> findAllByTableName(String tableName);

    @Override
    public List<TableQuery> findAll();

    @Modifying
    @Query("update TableQuery q set q.tableName = :t2 where q.tableName = :t1")
    public void updateTableName(String t1, String t2);
}
