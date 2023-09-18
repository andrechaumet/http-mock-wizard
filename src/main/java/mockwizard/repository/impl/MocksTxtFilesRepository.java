package mockwizard.repository.impl;

import com.google.gson.Gson;
import mockwizard.model.MockFile;
import mockwizard.repository.MocksRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Optional;

@Repository
public class MocksTxtFilesRepository implements MocksRepository {
    //TODO: Create an inactive mocks folder
    private static final String BASE_PATH = "C:/Users/Andy/Desktop/mocks/";

    private Gson gson = new Gson();

    @Override
    public Optional<MockFile> findByPathAndMethod(final String path, final String method) throws IOException {
        StringBuilder contenido = new StringBuilder();
        File file = new File(BASE_PATH + path.replaceAll("/", "~") +"_" + method + ".txt");

        if (!file.exists() || !file.isFile()) {
            System.out.println("FILE NOT FOUNDADADAD");
            throw new IOException("File does not exist");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        }

        return Optional.of(gson.fromJson(contenido.toString(), MockFile.class));
    }

    //TODO: Exception handling
    @Override
    public void save(final MockFile mockFile) throws IOException {
        FileWriter myWriter = new FileWriter(
                BASE_PATH +
                        mockFile.getPath().replaceAll("/", "~") +
                        "_" +
                        mockFile.getMethod() +
                        ".txt");

        myWriter.write(gson.toJson(mockFile));
        myWriter.close();
        System.out.println("object saved at:" + BASE_PATH + mockFile.getPath() + "_" + mockFile.getMethod());
    }
}
