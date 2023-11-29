package mockwizard.model;

import mockwizard.model.base.HttpRequest;
import mockwizard.model.base.HttpResponse;

public interface ReadOnlyMock {
    String getPath();

    String getMethod();

    Long getDelay();

    HttpRequest getKey();

    HttpResponse getValue();
}
