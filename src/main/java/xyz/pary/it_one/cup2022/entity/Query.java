package xyz.pary.it_one.cup2022.entity;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import xyz.pary.it_one.cup2022.model.Column;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@SqlResultSetMapping(
        name = "ColumnMapping",
        classes = @ConstructorResult(
                targetClass = Column.class,
                columns = {
                    @ColumnResult(name = "field", type = String.class),
                    @ColumnResult(name = "type", type = String.class),
                    @ColumnResult(name = "key", type = String.class)
                }))
public abstract class Query {

    @Id
    private Integer queryId;
    @Size(max = 120)
    private String query;
}
