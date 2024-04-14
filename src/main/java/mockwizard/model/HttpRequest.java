package mockwizard.model;

import javax.validation.constraints.NotNull;
import java.util.Set;

public record HttpRequest(
        @NotNull Set<Attribute<?>> headers,
        @NotNull Set<Attribute<?>> params,
        @NotNull Set<Attribute<?>> body
) {
    @Override
    public Set<Attribute<?>> params() {
        return Set.copyOf(params);
    }

    @Override
    public Set<Attribute<?>> headers() {
        return Set.copyOf(headers);
    }

    @Override
    public Set<Attribute<?>> body() {
        return Set.copyOf(body);
    }
}
