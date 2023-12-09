package mockwizard.repository;

import mockwizard.model.Mock;
import mockwizard.model.ReadOnlyMock;

import java.io.IOException;

public interface MocksRepository {
    ReadOnlyMock findByUriAndMethod(final String uri, final String method) throws IOException;
    ReadOnlyMock save(final Mock mock) throws IOException;
}
