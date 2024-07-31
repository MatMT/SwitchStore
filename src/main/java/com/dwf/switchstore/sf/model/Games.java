package com.dwf.switchstore.sf.model;

public class Games {
    private int id;
    private String title;
    private String genre;
    private int price;
    private String created_at;
    private String updated_at;

    public Games() {
    }

    public Games(int id, String title, String genre, int price, String created_at, String updated_at) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }

    public void setGenre(String genre) { this.genre = genre; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public String getCreated_at() { return created_at; }

    public void setCreated_at(String created_at) { this.created_at = created_at; }

    public String getUpdated_at() { return updated_at; }

    public void setUpdated_at(String updated_at) { this.updated_at = updated_at; }

}
