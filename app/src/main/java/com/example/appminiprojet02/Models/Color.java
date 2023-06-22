package com.example.appminiprojet02.Models;

public class Color {
    private int id;
    private String name;
    private String value;

    //region getters & setters
    public void setId(int id) {
        this.id = id;
    }

    public void setQuote(String name) {
        this.name = name;
    }

    public void setAuthor(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
    //endregion


    public Color(int id, String name, String value) {
        setId(id);
        setQuote(name);
        setAuthor(value);
    }
}
