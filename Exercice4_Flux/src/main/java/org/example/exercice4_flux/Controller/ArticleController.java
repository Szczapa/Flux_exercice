package org.example.exercice4_flux.Controller;

import org.example.exercice4_flux.entity.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {

        @GetMapping("articles")
        public Flux<String> getArticles() {

            List<Article> articles = new ArrayList<>();
            Collections.addAll(articles,
                    new Article("Introduction to Spring WebFlux", "Author"),
                    new Article("Reactive Programming with Project Reactor", "Author2"),
                    new Article("Building APIs with Spring Boot", "Author3")
            );

            return Flux.fromIterable(articles)
                    .map(Article::getTitle);
        }
}
