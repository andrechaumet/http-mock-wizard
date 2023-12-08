package mockwizard.repository.entity;
//TODO: WIP

import mockwizard.model.component.Body;
import mockwizard.model.component.Header;
import mockwizard.model.component.Param;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "HTTP_REQUEST")
public class HttpRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "HTTP_REQUEST_ID")
    private List<Header> headers;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "HTTP_REQUEST_ID")
    private List<Param> params;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "HTTP_REQUEST_ID")
    private Body body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
