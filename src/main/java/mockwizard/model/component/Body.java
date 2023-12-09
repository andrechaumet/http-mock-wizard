package mockwizard.model.component;

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

    public String getValue() {
        return value;
    }
}