package org.example.repository.impl;

import org.example.model.HttpRequest;
import org.example.model.HttpResponse;
import org.example.model.MockFile;
import org.example.repository.MocksRepository;

import java.util.*;

public class MocksTestRepository implements MocksRepository {
    @Override
    public Optional<MockFile> findByPath(String path) {
        MockFile mockFile = new MockFile();
        mockFile.setPath("test-route");
        mockFile.setKey(createKey());
        mockFile.setValue(createValue());
        if (path.equals(mockFile.getPath())) {
            System.out.println("PATHS MATCH");
            return Optional.of(mockFile);
        } else {
            System.out.println("PATHS DO NOT MATCH");
            return Optional.empty();
        }
    }

    public HttpRequest createKey() {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setHttpMethod("GET");
        httpRequest.setRequiredHeaders(testHeaders());
        httpRequest.setRequiredBody(testBody());
        return httpRequest;
    }

    private HttpResponse createValue() {
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setHeaders(testHeaders());
        httpResponse.setBody(testBody());
        httpResponse.setHttpStatusCode("200");
        return httpResponse;
    }

    private String testBody() {
        return "{\n" +
                "  \"firstNameeeeeeee\": \"John\",\n" +
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
