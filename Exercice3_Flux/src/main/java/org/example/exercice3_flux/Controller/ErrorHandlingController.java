package org.example.exercice3_flux.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ErrorHandlingController {
    @GetMapping("/error-resume")
    public Flux<String> errorResume() {
        return Flux.just("a", "b", "c")
                .concatWith(Flux.error(new RuntimeException("error"))).onErrorResume(
                        e -> Flux.just("Default1", "Default2"));
    }

    @GetMapping("/error-continue")
    public Flux<String> errorContinue() {

        return Flux.range(1, 5)
                .<Integer>handle((num, sink) -> {
                    if (num == 2) {
                        sink.error(new RuntimeException("Simulated Error for number: " + num));
                        return;
                    }
                    sink.next(num);
                }).onErrorContinue((e, num) -> {
                    System.out.println("Error ignored for number: " + num + ". Reason: " + e.getMessage());
                }).map(Object::toString);
    }
}
