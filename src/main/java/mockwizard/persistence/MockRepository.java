package mockwizard.persistence;

import com.querydsl.core.types.Predicate;
import mockwizard.model.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MockRepository {
    Optional<Mock> findByUriAndMethodOrThrow(final String uri, final String method);

    Page<Mock> findAll(final Pageable pageable, final Predicate predicate);

    Mock save(final Mock mock);

    void delete(final String uri, final String method);

    void deleteAllByUri(final String uri);
}
