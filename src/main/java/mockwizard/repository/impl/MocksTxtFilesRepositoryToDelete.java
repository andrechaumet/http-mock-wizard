/*
package org.example.repository.impl;

import model.mockwizard.HttpRequest;
import model.mockwizard.HttpResponse;
import repository.mockwizard.MocksRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class MocksTxtFilesRepository implements MocksRepository {

    private static final String BASE_PATH = "C:/Users/Andy/Desktop/http-mock-wizard/active-mocks";

    @Override
    public Optional<HttpResponse> findByPath(String path) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(BASE_PATH + path + "/200.txt/"));) {
            reader.lines();
        } catch (IOException ex) {
            return Optional.empty();
        }
        return null;
    }

    @Override
    public Optional<HttpResponse> findByRequest(HttpRequest httpRequest) {
        return Optional.empty();
    }

*/
/*    private HttpResponse buildResponse(final Stream<String> lines) {
        HttpResponse httpResponse = new HttpResponse();

        final List<String[]> collect = lines
                .filter(line -> !line.contains("\"") && !line.contains("{") && !line.contains("}"))
                .map(line -> line.split(":"))
                .collect(Collectors.toList());
        for (String[] headersList : collect) {
            HashMap<String, List<String>> headers = new HashMap<>();
            headers.put(headersList[0], )
        }
            final String body = lines
                .filter(line -> line.contains("\"") || line.contains("{") || line.contains("}"))
                .collect(Collectors.joining());
*//*
*/
/*
        httpResponse.setHeaders(headers);
*//*
*/
/*
        httpResponse.setBody(body);
        return null;
    }*//*

}
*/
