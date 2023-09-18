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

    public void addValue(String value) {
        this.values.add(value);
    }

    public void addValue(List<String> values) {
        this.values.addAll(values);
    }

    public Boolean isRequired() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Header)) return false;

        Header header = (Header) o;

        if (!getKey().equals(header.getKey())) return false;
        return getValues().equals(header.getValues());
    }

    @Override
    public int hashCode() {
        int result = getKey().hashCode();
        result = 31 * result + getValues().hashCode();
        return result;
    }
}
