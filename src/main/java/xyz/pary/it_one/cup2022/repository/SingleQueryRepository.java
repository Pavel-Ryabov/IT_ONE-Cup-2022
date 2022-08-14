package xyz.pary.it_one.cup2022.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import xyz.pary.it_one.cup2022.entity.SingleQuery;

public interface SingleQueryRepository extends CrudRepository<SingleQuery, Integer> {

    @Override
    public List<SingleQuery> findAll();
}
