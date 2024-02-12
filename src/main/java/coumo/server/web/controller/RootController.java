package coumo.server.web.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8080")
@RestController
public class RootController {
    @GetMapping("/health")
    public String healthCheck() {
        return "I'm healthy";
    }
}
