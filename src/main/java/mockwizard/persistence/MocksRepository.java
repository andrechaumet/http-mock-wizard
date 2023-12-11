package mockwizard.persistence;

import mockwizard.model.Mock;
import mockwizard.model.ReadOnlyMock;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MocksRepository {
    Optional<ReadOnlyMock> findByUriAndMethod(final String uri, final String method);
    Page<ReadOnlyMock> findAll(final Pageable pageable, final Predicate predicate);
    ReadOnlyMock save(final Mock mock);
    void delete(final String uri, final String method);
    void deleteAllByUri(final String uri);
}
