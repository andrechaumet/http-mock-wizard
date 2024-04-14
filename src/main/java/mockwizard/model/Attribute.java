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
    public Attribute(String key, T value) {
        this(key, value, false, (a, b) -> true);
    }

    public Attribute(String key, T value, Boolean required) {
        this(key, value, required, (a, b) -> true);
    }

    public Attribute(String key, T value, BiPredicate<T, T> expectedBehaviour) {
        this(key, value, true, expectedBehaviour);
    }

    public boolean matchesBehaviour(Attribute<?> sent) {
        return expectedBehaviour.test(this.value, (T) sent.value);
    }

    public Optional<T> valueOptional() {
        return Optional.ofNullable(value);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute<?> attribute = (Attribute<?>) o;
        return Objects.equals(key, attribute.key);
    }

    public int hashCode() {
        return Objects.hash(key);
    }
}
