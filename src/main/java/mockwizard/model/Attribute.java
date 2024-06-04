package mockwizard.model;

import javax.annotation.Nullable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiPredicate;

public record Attribute<T>(
        @NotBlank String key,
        @Nullable T value,
        @NotNull Boolean required,
        @NotNull BiPredicate<T, T> expectedBehaviour
) {
    public Attribute(final String key, final T value) {
        this(key, value, false, (a, b) -> true);
    }

    public Attribute(final String key, final T value, final Boolean required) {
        this(key, value, required, (a, b) -> true);
    }

    public Attribute(final String key, final T value, final BiPredicate<T, T> expectedBehaviour) {
        this(key, value, true, expectedBehaviour);
    }

    public boolean matchesBehaviour(final Attribute<?> sent) {
        return expectedBehaviour.test(this.value, (T) sent.value);
    }

    public Optional<T> valueOptional() {
        return Optional.ofNullable(value);
    }

    public String valueAsString() {
        return (value != null) ? value.toString() : "";
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Attribute<?> attribute = (Attribute<?>) o;
        return Objects.equals(key, attribute.key);
    }

    public int hashCode() {
        return Objects.hash(key);
    }
}
