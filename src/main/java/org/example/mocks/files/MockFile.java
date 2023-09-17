package org.example.mocks.files;

public class MockFile {
    private HttpRequest key;
    private HttpResponse value;

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
