package mockwizard.repository;

import mockwizard.model.Mock;
import mockwizard.model.ReadOnlyMock;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Optional;

public interface MocksRepository {
    Optional<ReadOnlyMock> findByUriAndMethod(final String uri, final String method);
    Page<ReadOnlyMock> findAll(final Pageable pageable, final Predicate predicate);
    ReadOnlyMock save(final Mock mock) throws IOException;
    void delete(final String uri, final String method) throws IOException;
}
