package mockwizard.model;

public class MockFile {
    private String path;
    private String method;
    private HttpRequest key;
    private HttpResponse value;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public HttpRequest getKey() {
        return key;
    }

    public void setKey(HttpRequest key) {
        this.key = key;
    }

    public HttpResponse getValue() {
        return value;
    }

    public void setValue(HttpResponse value) {
        this.value = value;
    }
}
