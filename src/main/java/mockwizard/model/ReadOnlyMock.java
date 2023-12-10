package mockwizard.model;

import mockwizard.model.base.HttpRequest;
import mockwizard.model.base.HttpResponse;

//TODO: GET/POST/PUT/PATCH interfaces
public interface ReadOnlyMock {
    String getPath();

    String getMethod();

    Long getDelayMillis();

    HttpRequest getKey();

    HttpResponse getValue();
}
