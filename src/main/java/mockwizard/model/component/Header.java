package mockwizard.model.component;

import java.util.List;

public class Header {
    private final String key;
    private final List<String> values;
    private Boolean required;

    public Header(String key, List<String> values) {
        this.key = key;
        this.values = values;
        this.required = false;
    }

    public String getKey() {
        return key;
    }

    public Boolean isRequired() {
        return required;
    }

    public void require() {
        this.required = true;
    }

    public List<String> getValues() {
        return values;
    }

}
