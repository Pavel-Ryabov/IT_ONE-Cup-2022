package xyz.pary.it_one.cup2022.repository;

import xyz.pary.it_one.cup2022.model.Table;

public interface TableRepository {

    public void create(Table t);

    public Table get(String name);

    public void delete(String name);
    
    public int getNotNullCount(String tableName, String columnName);
}
