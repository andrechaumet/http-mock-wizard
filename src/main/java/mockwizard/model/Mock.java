package mockwizard.model;

import mockwizard.model.component.HttpRequest;
import mockwizard.model.component.HttpResponse;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Mock {
    private final String path;
    private final HttpMethod method;
    private final HttpRequest key;
    private final HttpResponse value;
    private final Long delayMillis;

    public Mock(@NotBlank String path, @NotNull HttpMethod method, @NotNull HttpRequest key, @NotNull HttpResponse value) {
        this.key = key;
        this.path = path;
        this.value = value;
        this.method = method;
        this.delayMillis = 0L;
    }

    public Mock(@NotBlank String path, @NotNull HttpMethod method, @NotNull HttpRequest key, @NotNull HttpResponse value, Long delayMillis) {
        this.key = key;
        this.path = path;
        this.value = value;
        this.method = method;
        this.delayMillis = delayMillis;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public Long getDelayMillis() {
        return delayMillis;
    }

    public HttpRequest getKey() {
        return key;
    }

    public HttpResponse getValue() {
        return value;
    }
}
