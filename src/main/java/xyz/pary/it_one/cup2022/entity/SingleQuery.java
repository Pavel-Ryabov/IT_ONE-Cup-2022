package xyz.pary.it_one.cup2022.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import xyz.pary.it_one.cup2022.exception.InvalidSingleQueryId;

@Entity
@javax.persistence.Table(name = "single_queries")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SingleQuery extends Query implements Serializable {

    public void setQueryId(String queryId) {
        try {
            int id = Integer.parseInt(queryId);
            this.setQueryId(id);
        } catch (NumberFormatException e) {
            throw new InvalidSingleQueryId();
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.getQueryId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SingleQuery other = (SingleQuery) obj;
        return Objects.equals(this.getQueryId(), other.getQueryId());
    }

}
