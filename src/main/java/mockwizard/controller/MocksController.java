package mockwizard.controller;

import mockwizard.model.MockFile;
import mockwizard.repository.MocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController()
@RequestMapping("/mocks")
public class MocksController {

    private final MocksRepository mocksRepository;

    @Autowired
    public MocksController(MocksRepository mocksRepository) {
        this.mocksRepository = mocksRepository;
    }

    @PostMapping()
    public MockFile createMock(@RequestBody MockFile mockFile) throws IOException {
        return mocksRepository.save(mockFile);
    }
    /*//TODO:
    @GetMapping
    public List<MockFile> findAll() throws IOException {
        return null;
    }

    //TODO: DELETE LATER, ONLY FOR POSTMAN TESTS
    @GetMapping("/example")
    public MockFile exampleDeleteLater() {
        MockFile mockFile = new MockFile();
        mockFile.setPath("test");
        mockFile.setMethod("post");

        final HttpRequest httpRequest = new HttpRequest();
        final Body body = new Body();
        body.setRequired(true);
        body.setValue("completar:completar");
        httpRequest.setBody(body);
        //--
        mockFile.setKey(httpRequest);

        final HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHttpStatusCode("201");
        httpResponse.setBody(body.getValue());
        httpResponse.setHeaders(test());
        //--
        mockFile.setValue(httpResponse);
        return mockFile;
    }
    //TODO: DELETE LATER, ONLY FOR POSTMAN TESTS
    private Map<String, List<String>> test() {
        Map<String, List<String>> headers = new HashMap<>();
        addValue(headers, "Clave1", "Valor1A");
        addValue(headers, "Clave1", "Valor1B");
        addValue(headers, "Clave2", "Valor2A");
        addValue(headers, "Clave3", "Valor3A");
        addValue(headers, "Clave3", "Valor3B");
        return headers;
    }
    //TODO: DELETE LATER, ONLY FOR POSTMAN TESTS
    private void addValue(Map<String, List<String>> mapa, String clave, String valor) {
        if (mapa.containsKey(clave)) {
            mapa.get(clave).add(valor);
        } else {
            List<String> nuevaLista = new ArrayList<>();
            nuevaLista.add(valor);
            mapa.put(clave, nuevaLista);
        }
    }*/
}



