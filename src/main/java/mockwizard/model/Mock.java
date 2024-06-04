package mockwizard.model;

import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record Mock(
        @NotBlank String path,
        @NotNull HttpMethod method,
        @NotNull HttpRequest key,
        @NotNull HttpResponse value,
        @NotNull Long delayMillis
) {}
