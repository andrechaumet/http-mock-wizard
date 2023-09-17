package org.example.model;

public class MockFile {
    private String path;
    private HttpRequest key;
    private HttpResponse value;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
