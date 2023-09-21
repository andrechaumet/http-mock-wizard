package mockwizard.repository;

import mockwizard.model.Mock;

import java.io.IOException;

public interface MocksRepository {
    Mock findByPathAndMethod(final String path, final String method) throws IOException;
    Mock save(final Mock mock) throws IOException;
}
