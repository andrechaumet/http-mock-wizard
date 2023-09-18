package mockwizard.model;

import java.util.List;
import java.util.Objects;

public class HttpRequest {
    private List<Header> headers;
    private String requiredBody;

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public String getRequiredBody() {
        return requiredBody;
    }

    public void setRequiredBody(String requiredBody) {
        this.requiredBody = requiredBody;
    }

    @Override
    public boolean equals(Object o) {
        /*if (this == o) return true;
        if (!(o instanceof HttpRequest)) return false;
        HttpRequest that = (HttpRequest) o;
        return Objects.equals(getPath(), that.getPath()) && Objects.equals(getHttpMethod(), that.getHttpMethod()) && Objects.equals(getRequiredHeaders(), that.getRequiredHeaders()) && Objects.equals(getRequiredBody(), that.getRequiredBody());
*/
        return o.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeaders(), getRequiredBody());
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                ", requiredHeaders=" + headers +
                ", requiredBody='" + requiredBody + '\'' +
                '}';
    }
}
