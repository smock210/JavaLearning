package ru.kda.web.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Message {
    private String text;
    private String author;
    private LocalDateTime date;

    public Message(String text, String author) {
        this.text = text;
        this.author = author;
        this.date = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "<p><b>" + this.author + "</b></p>" +
                "<br/>" +
                "<p><strong>" + this.text + "</strong></p>" +
                "<br/>" +
                "<p>" + this.date + "</p>" +
                "<hr/>";
    }
}
