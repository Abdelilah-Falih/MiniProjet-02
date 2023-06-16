package com.example.appminiprojet02.Models;

public class Quote {
    private int id;
    private String quote;
    private String author;

    //region getters & setters
    public void setId(int id) {
        this.id = id;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public String getAuthor() {
        return author;
    }
    //endregion


    public Quote(int id, String quote, String author) {
        setId(id);
        setQuote(quote);
        setAuthor(author);
    }
}
