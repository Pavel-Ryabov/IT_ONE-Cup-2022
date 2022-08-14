package xyz.pary.it_one.cup2022.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.pary.it_one.cup2022.entity.SingleQuery;
import xyz.pary.it_one.cup2022.repository.SingleQueryRepository;

@Service
@RequiredArgsConstructor
public class SingleQueryService {

    private final SingleQueryRepository singleQueryRepository;
    private final QueryExecutor queryExecutor;

    @Transactional
    public void addQuery(SingleQuery query) {
        singleQueryRepository.save(query);
    }

    @Transactional
    public void modifyQuery(SingleQuery query) {
        if (!singleQueryRepository.existsById(query.getQueryId())) {
            throw new RuntimeException();
        }
        singleQueryRepository.save(query);
    }

    @Transactional
    public void deleteQuery(int id) {
        singleQueryRepository.deleteById(id);
    }

    @Transactional
    public void executeQuery(int id) {
        SingleQuery q = singleQueryRepository.findById(id).get();
        queryExecutor.execute(q);
    }

    @Transactional(readOnly = true)
    public SingleQuery getById(int id) {
        return singleQueryRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<SingleQuery> getAllQueries() {
        return singleQueryRepository.findAll();
    }
}
