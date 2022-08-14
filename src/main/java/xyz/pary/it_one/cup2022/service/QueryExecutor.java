package xyz.pary.it_one.cup2022.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import xyz.pary.it_one.cup2022.entity.Query;

@Service
@RequiredArgsConstructor
public class QueryExecutor {

    private final JdbcTemplate jdbcTemplate;

    public void execute(Query q) {
        jdbcTemplate.execute(q.getQuery());
    }

}
