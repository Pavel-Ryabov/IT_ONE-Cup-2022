package xyz.pary.it_one.cup2022.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = ColumnSerializer.class)
public class Column implements Serializable {

    private String title;
    private String type;
    @JsonIgnore
    private String key;

}
