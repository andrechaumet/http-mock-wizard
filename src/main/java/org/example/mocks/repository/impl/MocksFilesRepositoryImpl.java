package org.example.mocks.repository.impl;

import org.example.mocks.files.HttpResponse;
import org.example.mocks.repository.MocksRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MocksFilesRepositoryImpl implements MocksRepository {

    private static final String BASE_PATH = "C:/Users/Andy/Desktop/http-mock-wizard/active-mocks";

    @Override
    public Optional<HttpResponse> findByUri(String uri) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(BASE_PATH + uri + "/200.txt/"));) {
            reader.lines();
        } catch (IOException ex) {
            return Optional.empty();
        }
        return null;
    }

    private HttpResponse buildResponse(Stream<String> lines) {
        HttpResponse httpResponse = new HttpResponse();
        final List<String> headers = lines
                .filter(line -> !line.contains("\"") && !line.contains("{") && !line.contains("}"))
                .collect(Collectors.toList());
        final String body = lines
                .filter(line -> line.contains("\"") || line.contains("{") || line.contains("}"))
                .collect(Collectors.joining());
        httpResponse.setHeaders(headers);
        httpResponse.setBody(body);
        return null;
    }
}
