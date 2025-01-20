package org.example.exercice4_flux.entity;

public class Article {
    private String title;
    private String author;

    public Article(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Article() {}

    public String getTitle() {return title;}
    public String getAuthor() {return author;}

    public void setAuthor(String author) {this.author = author;}
    public void setTitle(String title) {this.title = title;}
}
