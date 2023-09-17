package org.example.mocks.repository.impl;

import org.example.mocks.files.HttpRequest;
import org.example.mocks.files.HttpResponse;
import org.example.mocks.files.MockFile;
import org.example.mocks.repository.MocksRepository;

import java.util.*;

public class MocksTestRepository implements MocksRepository {
    @Override
    public Optional<MockFile> findByPath(String path) {
        MockFile mockFile = new MockFile();
        mockFile.setKey(createRequest());
        mockFile.setValue(createResponse());
        if (path.equals(mockFile.getKey().getPath())) {
            System.out.println("PATHS MATCH");
            return Optional.of(mockFile);
        } else {
            System.out.println("PATHS DO NOT MATCH");
            return Optional.empty();
        }
    }

    @Override
    public Optional<MockFile> findByRequest(HttpRequest httpRequest) {
        return Optional.empty();
    }

    public HttpRequest createRequest() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setPath("test-route");
        httpRequest.setHttpMethod("GET");
        httpRequest.setRequiredHeaders(testHeaders());
        httpRequest.setRequiredBody(testBody());
        return httpRequest;
    }

    private HttpResponse createResponse() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHeaders(testHeaders());
        httpResponse.setBody(testBody());
        httpResponse.setHttpStatusCode("200");
    }

    private String testBody() {
        return "{\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"Doe\",\n" +
                "  \"age\": 30,\n" +
                "  \"email\": \"john.doe@example.com\",\n" +
                "  \"address\": {\n" +
                "    \"street\": \"123 Main Street\",\n" +
                "    \"city\": \"Anytown\",\n" +
                "    \"state\": \"CA\",\n" +
                "    \"zipCode\": \"12345\"\n" +
                "  }\n" +
                "}\n" +
                "Este objeto JSON representa a una persona llamada John Doe, con 30 años de edad, una dirección de correo electrónico y una dirección física en Anytown, California. Puedes personalizar los valores según tus necesidades para representar a cualquier persona.\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n";
    }

    private Map<String, List<String>> testHeaders() {
        HashMap<String, List<String>> headers = new HashMap<>();
        ArrayList<String> headersValuesList = new ArrayList<>();
        headersValuesList.add("value1");
        headersValuesList.add("value2");
        headers.put("header1", headersValuesList);
        headers.put("header2", headersValuesList);
        return headers;
    }

}
