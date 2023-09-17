package org.example.mocks.files;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpRequest {
    private String path;
    private String httpMethod;
    private Map<String, List<String>> requiredHeaders;
    private String requiredBody;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, List<String>> getRequiredHeaders() {
        return requiredHeaders;
    }

    public void setRequiredHeaders(Map<String, List<String>> requiredHeaders) {
        this.requiredHeaders = requiredHeaders;
    }

    public String getRequiredBody() {
        return requiredBody;
    }

    public void setRequiredBody(String requiredBody) {
        this.requiredBody = requiredBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpRequest)) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(getPath(), that.getPath()) && Objects.equals(getHttpMethod(), that.getHttpMethod()) && Objects.equals(getRequiredHeaders(), that.getRequiredHeaders()) && Objects.equals(getRequiredBody(), that.getRequiredBody());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath(), getHttpMethod(), getRequiredHeaders(), getRequiredBody());
    }
}
