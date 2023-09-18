package mockwizard.repository;

import mockwizard.model.MockFile;

import java.io.IOException;
import java.util.Optional;

public interface MocksRepository {
    Optional<MockFile> findByPathAndMethod(final String path, final String method) throws IOException;
    void save(final MockFile mockFile) throws IOException;
}
