package mockwizard.repository.entity;

import javax.persistence.*;

//TODO: WIP
@Entity
@Table(name = "MOCK")
public class MockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String path;
    private String method;
    private Long delay;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MOCK_ID")
    private HttpRequestEntity key;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MOCK_ID")
    private HttpResponseEntity value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public HttpRequestEntity getKey() {
        return key;
    }

    public void setKey(HttpRequestEntity key) {
        this.key = key;
    }

    public HttpResponseEntity getValue() {
        return value;
    }

    public void setValue(HttpResponseEntity value) {
        this.value = value;
    }
}