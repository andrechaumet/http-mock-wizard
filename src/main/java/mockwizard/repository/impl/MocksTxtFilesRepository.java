package mockwizard.repository.impl;

import com.google.gson.Gson;
import mockwizard.model.Mock;
import mockwizard.repository.MocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Repository
public class MocksTxtFilesRepository implements MocksRepository {
    //TODO: Create an inactive mocks folder
    private static final Logger LOGGER = LoggerFactory.getLogger(MocksTxtFilesRepository.class);

    //TODO: @Value
    private static final String BASE_PATH = "C:/Users/Andy/Desktop/mocks/";
    private static final String PATH_FORMAT = BASE_PATH + "_%s_%s.txt";
    private static final Gson GSON = new Gson();

    private final Map<Character, Character> CONVERSION = new HashMap<Character, Character>() {{
        put('/', '~');
        put('~', '/');
        put('?', '!');
        put('!', '?');
    }};

    @Override
    public Optional<Mock> findByPathAndMethod(final String path, final String method) throws IOException {
        final String pathToSearch = format(PATH_FORMAT, normalizeFilePath(path), method);
        File file = new File(pathToSearch);
        failIfFileDoesNotExist(file);
        final Mock mock = extractFromFile(file);
        return Optional.of(mock);
    }

    @Override
    public Mock save(final Mock mock) throws IOException {
        final String filePath = format(PATH_FORMAT, normalizeFilePath(mock.getPath()), mock.getMethod());
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(GSON.toJson(mock));
            return mock;
        }
    }

    private Mock extractFromFile(File mockFile) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(mockFile))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                content.append(linea).append("\n");
            }
        }
        return GSON.fromJson(content.toString(), Mock.class);
    }

    private String normalizeFilePath(String path) {
        StringBuilder filePath = new StringBuilder();
        for (char c : path.toCharArray()) {
            filePath.append(convert(c));
        }
        return filePath.toString();
    }

    private char convert(char c) {
        return CONVERSION.getOrDefault(c, c);
    }

    private void failIfFileDoesNotExist(File file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            LOGGER.error("File with path [{}] does not exist.", file.getPath());
            throw new IOException("File does not exist.");
        }
    }
}
