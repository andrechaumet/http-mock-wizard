package mockwizard.model;

import mockwizard.model.base.HttpRequest;
import mockwizard.model.base.HttpResponse;

public class Mock implements ReadOnlyMock {
    private String path;
    private String method;
    private Long delay;
    private HttpRequest key;
    private HttpResponse value;

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    @Override
    public HttpRequest getKey() {
        return key;
    }

    public void setKey(HttpRequest key) {
        this.key = key;
    }

    @Override
    public HttpResponse getValue() {
        return value;
    }

    public void setValue(HttpResponse value) {
        this.value = value;
    }
}
