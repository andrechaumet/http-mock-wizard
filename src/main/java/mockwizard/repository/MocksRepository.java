package mockwizard.repository;

import mockwizard.model.Mock;

import java.io.IOException;
import java.util.Optional;

public interface MocksRepository {
    Optional<Mock> findByPathAndMethod(final String path, final String method) throws IOException;
    Mock save(final Mock mock) throws IOException;
}
