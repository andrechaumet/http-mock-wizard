package mockwizard.model;


import java.util.ArrayList;
import java.util.List;

public class Header {
    private String key;
    private Boolean required;
    private List<String> values;

    public Header() {
        values = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
