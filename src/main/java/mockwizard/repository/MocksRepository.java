package mockwizard.repository;

import mockwizard.model.MockFile;

import java.io.IOException;

public interface MocksRepository {
    MockFile findByPathAndMethod(final String path, final String method) throws IOException;
    MockFile save(final MockFile mockFile) throws IOException;
}
