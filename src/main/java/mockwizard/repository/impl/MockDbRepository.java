package mockwizard.repository.impl;

import mockwizard.model.Mock;
import mockwizard.model.ReadOnlyMock;
import mockwizard.repository.MocksRepository;

import java.io.IOException;

public class MockDbRepository implements MocksRepository {
    @Override
    public ReadOnlyMock findByPathAndMethod(String path, String method) {
        return null;
    }

    @Override
    public ReadOnlyMock save(Mock mock) {
        return null;
    }
}
