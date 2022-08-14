package xyz.pary.it_one.cup2022.repository;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import xyz.pary.it_one.cup2022.model.Column;
import xyz.pary.it_one.cup2022.model.Table;

@Repository
@RequiredArgsConstructor
public class TableRepositoryImpl implements TableRepository {

    private final EntityManager em;

    @Override
    public void create(Table t) {
        em.createNativeQuery(t.getCreateQuery()).executeUpdate();
    }

    @Override
    public Table get(String name) {
        List<Column> cols = em.createNativeQuery(String.format("show columns from %s;", name), "ColumnMapping").getResultList();
        if (cols.isEmpty()) {
            return null;
        }
        String pk = cols.stream().filter(c -> "PRI".equals(c.getKey())).findFirst().map(c -> c.getTitle().toLowerCase()).orElse("");
        return new Table(name, pk, cols.size(), cols);
    }

    @Override
    public void delete(String name) {
        em.createNativeQuery(String.format("DROP TABLE %s;", name)).executeUpdate();
    }

    @Override
    public int getNotNullCount(String tableName, String columnName) {
        return ((BigInteger) em.createNativeQuery(String.format("SELECT count(%2$s) FROM %1$s WHERE %2$s IS NOT NULL;", tableName, columnName))
                .getSingleResult()).intValue();
    }
}
