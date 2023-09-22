package mockwizard.controller;

import mockwizard.model.Mock;
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
    public Mock saveMock(@RequestBody Mock mock) throws IOException {
        return mocksRepository.save(mock);
    }
}



