package mockwizard.repository.impl;

import com.google.gson.Gson;
import mockwizard.model.Mock;
import mockwizard.model.ReadOnlyMock;
import mockwizard.repository.MocksRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

//TODO: Create an inactive mocks folder
@Repository
public class MocksTxtFilesRepository implements MocksRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(MocksTxtFilesRepository.class);

    //TODO: review later
    private static final String BASE_PATH = "review";
    private static final String FILE_FORMAT = ".txt";
    private static final String PATH_FORMAT = BASE_PATH + "%s_%s" + FILE_FORMAT;
    private static final Gson GSON = new Gson();

    private static final Map<Character, Character> CONVERSION = new HashMap<>(4);

    public MocksTxtFilesRepository() {
        CONVERSION.put('/', '~');
        CONVERSION.put('~', '/');
        CONVERSION.put('?', '!');
        CONVERSION.put('!', '?');
    }

    @Override
    public ReadOnlyMock findByPathAndMethod(final String path, final String method) throws IOException {
        final String pathToSearch = format(PATH_FORMAT, normalizeFilePath(path), method);
        final File file = new File(pathToSearch);
        failIfPathIsNotValid(file);
        return extractFromFile(file);
    }

    @Override
    public ReadOnlyMock save(final Mock mock) throws IOException {
        final String filePath = format(PATH_FORMAT, normalizeFilePath(mock.getPath()), mock.getMethod());
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(GSON.toJson(mock));
            return mock;
        }
    }

    private ReadOnlyMock extractFromFile(final File mockFile) throws IOException {
        final StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(mockFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return GSON.fromJson(content.toString(), Mock.class);
    }

    private String normalizeFilePath(final String path) {
        final StringBuilder filePath = new StringBuilder();
        for (char c : path.toCharArray()) {
            filePath.append(convert(c));
        }
        return filePath.toString();
    }

    private char convert(final char c) {
        return CONVERSION.getOrDefault(c, c);
    }

    private void failIfPathIsNotValid(final File file) throws IOException {
        if (!file.exists() || !file.isFile()) {
            LOGGER.error("File with path [{}] does not exist.", file.getPath());
            throw new IOException("File does not exist.");
        }
    }
}