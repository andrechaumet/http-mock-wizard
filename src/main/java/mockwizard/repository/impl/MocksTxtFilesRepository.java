package mockwizard.repository.impl;

import com.google.gson.Gson;
import mockwizard.model.MockFile;
import mockwizard.repository.MocksRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MocksTxtFilesRepository implements MocksRepository {
    //TODO: Create an inactive mocks folder
    private static final String BASE_PATH = "C:/Users/Andy/Desktop/mocks/";
    private static final Gson GSON = new Gson();

    private final Map<Character, Character> toStore = new HashMap<Character, Character>() {{
        put('/', '~');
        put('?', '!');
    }};
    private final Map<Character, Character> toRetrieve = new HashMap<Character, Character>() {{
        put('~', '/');
        put('!', '?');
    }};

    @Override
    public Optional<MockFile> findByPathAndMethod(final String path, final String method) throws IOException {
        StringBuilder content = new StringBuilder();
        File file = new File(BASE_PATH + path.replaceAll("~", "/") + "_" + method + ".txt");

        if (!file.exists() || !file.isFile()) {
            throw new IOException("File does not exist");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                content.append(linea).append("\n");
            }
        }
        return Optional.of(GSON.fromJson(content.toString(), MockFile.class));
    }

    //TODO: Exception handling
    @Override
    public void save(final MockFile mockFile) throws IOException {
        FileWriter myWriter = new FileWriter(
                BASE_PATH +
                        normalizeName(mockFile.getPath()) +
                        "_" +
                        mockFile.getMethod() +
                        ".txt");
        myWriter.write(GSON.toJson(mockFile));
        myWriter.close();
        System.out.println("object saved at:" + BASE_PATH + mockFile.getPath() + "_" + mockFile.getMethod());
    }

    //TODO:
    private String normalizeName(String path) {
        StringBuilder normalizedPath = new StringBuilder();
        for (char c : path.toCharArray()) {
            if (toStore.containsKey(c)) {
                normalizedPath.append(toStore.get(c));
            } else if(toRetrieve.containsKey(c)) {
                normalizedPath.append(toRetrieve.get(c));
            } else {
                normalizedPath.append(c);
            }
        }
        return normalizedPath.toString();
    }
}
