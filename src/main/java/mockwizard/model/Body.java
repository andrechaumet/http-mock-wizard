package mockwizard.model;

public class Body {
    private Boolean required;
    private String value;

    public Body(String value) {
        this.value = value;
        this.required = false;
    }

    public Boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
