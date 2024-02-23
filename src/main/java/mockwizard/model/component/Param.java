package mockwizard.model.component;

public class Param {
    private final String key;
    private final String value;
    private final Boolean required;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
        this.required = false;
    }

    public Param(String key, String value, Boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public Boolean isRequired() {
        return required;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
