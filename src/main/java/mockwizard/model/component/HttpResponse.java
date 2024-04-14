package mockwizard.model.component;

import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;
import java.util.Set;

public record HttpResponse(
        @NotNull HttpStatus httpStatus,
        @NotNull Set<Attribute<?>> headers,
        @NotNull Set<Attribute<?>> body
) {
    @Override
    public Set<Attribute<?>> headers() {
        return Set.copyOf(headers);
    }

    @Override
    public Set<Attribute<?>> body() {
        return Set.copyOf(body);
    }
}
