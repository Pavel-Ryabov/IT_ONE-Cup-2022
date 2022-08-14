package xyz.pary.it_one.cup2022.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Table implements Serializable {

    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9_-]+$")
    private String tableName;
    private String primaryKey;
    private int columnsAmount;
    private List<Column> columnInfos;

    @JsonIgnore
    public String getCreateQuery() {
        StringBuilder sb = new StringBuilder();
        for (Column c : columnInfos) {
            sb.append(c.getTitle()).append(" ").append(c.getType()).append(",");
        }
        String columns = sb.toString();
        return String.format("CREATE TABLE %s (%s CONSTRAINT PK_%1$s PRIMARY KEY (%s))", tableName, columns, primaryKey);
    }

}
