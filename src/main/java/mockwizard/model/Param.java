package mockwizard.model;

public class Param {
    private String key;
    private String value;
    private Boolean required;

    public Param(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Param(String key, String value, Boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
