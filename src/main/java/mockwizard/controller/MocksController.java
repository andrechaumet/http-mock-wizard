package mockwizard.controller;

import mockwizard.model.HttpRequest;
import mockwizard.model.HttpResponse;
import mockwizard.model.MockFile;
import mockwizard.repository.MocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class MocksController {

    private final MocksRepository mocksRepository;

    @Autowired
    public MocksController(MocksRepository mocksRepository) {
        this.mocksRepository = mocksRepository;
    }

    @PostMapping
    public void createMock(@RequestBody MockFile mockFile) throws IOException {
        mocksRepository.save(mockFile);
    }

    //TODO:
    @GetMapping
    public List<MockFile> findALL() throws IOException {
        return null;
    }

    @GetMapping
    public List<MockFile> findByPath(final String path) {
        return null;
    }


}
