package org.example.exercice2_flux.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class OperationController {
    @GetMapping("/processed-number")
    public Flux<String> processedNumber(){

        return Flux.range(1, 10)
                .filter(i -> i % 2 == 0)
                .map(i -> i*10)
                .map(result -> " Processed: " + result);
    }
}
