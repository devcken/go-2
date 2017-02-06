package io.devcken;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @RequestMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Hello world!!!");
    }
}
